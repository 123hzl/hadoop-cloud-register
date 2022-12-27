package com.hzl.hadoop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzl.hadoop.constant.HnlpConstant;
import com.hzl.hadoop.exception.CommonException;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.*;

/**
 * description
 * http请求工具类
 * 无法处理http2.0协议，切没有连接池，需要换成okhttp3
 * @author hzl 2020/01/02 7:24 PM
 */
@Slf4j
public class HttpUtils {
	/**
	 * 默认连接超时时间 10秒
	 */
	private static final int DEFAULT_CONNECTION_TIME_OUT = 10_000;

	/**
	 * 默认读取响应结果超时时间 10秒
	 */
	private static final int DEFAULT_READ_TIME_OUT = 10_000;

	/**
	 * http OAuth 2.0 固定字段
	 */
	private static final String USER_NAME_FIELD = "username";
	private static final String PASSWORD_FIELD = "password";
	private static final String GRANT_TYPE_FIELD = "grant_type";
	private static final String GRANT_TYPE_VALUE = PASSWORD_FIELD;
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_BASIC = "Basic";

	//basic认证生成请求头中的key值	header.put("Authorization", key);
	public static String getKey(String user, String password) {
		String code = user + ":" + password;
		Map<String, String> bodyParam = new HashMap<>();
		bodyParam.put("username", user);
		bodyParam.put("password", password);
		String newKey = new BASE64Encoder().encodeBuffer(code.getBytes());
		newKey = "Basic " + newKey;
		return newKey;
	}

	/**
	 * oauth 2.0
	 * <p>
	 * authorization type : basic
	 *
	 * @param url      地址
	 * @param userName 账号
	 * @param password 密码
	 * @param token    token 可选
	 * @return 响应结果 json 形式
	 * @throws HttpResponseException 非20X时，扔出http响应异常
	 */
	public static JSONObject doOauth2(String url, String userName, String password, String token)
			throws HttpResponseException {

		Objects.requireNonNull(userName, "登录账号");
		Objects.requireNonNull(password, "登录密码");
		Map<String, String> bodyParam = new HashMap<>();
		bodyParam.put(USER_NAME_FIELD, userName);
		bodyParam.put(PASSWORD_FIELD, password);
		bodyParam.put(GRANT_TYPE_FIELD, GRANT_TYPE_VALUE);

		if (StringUtils.isBlank(token)) {
			return sentPostWithForm(url, bodyParam, null);
		}
		Map<String, String> headerParam = new HashMap<>();
		headerParam.put(AUTHORIZATION_HEADER, AUTHORIZATION_BASIC + " " + token);
		return sentPostWithForm(url, bodyParam, headerParam);
	}

	/**
	 * post content-type： application/json token 形式： [auth_type token_content] eg: Basic xxxxxxxxxxx
	 * Bearer xxxxxxxxxx
	 *
	 * @param url   请求地址
	 * @param body  请求报文
	 * @param token 权限校验口令
	 * @return 响应报文
	 * @throws HttpResponseException 非20x,异常
	 */
	public static JSONObject sentPostWithJsonByAuthorization(String url, Map<String, ?> body, String token)
			throws HttpResponseException {
		Objects.requireNonNull(token, "Authorization token");
		HashMap<String, String> authHeader = new HashMap<>();
		authHeader.put(AUTHORIZATION_HEADER, token);
		return sentPostWithJson(url, body, authHeader);
	}

	/**
	 * POST content-type: application/json;charset=utf-8
	 *
	 * @param url         访问地址
	 * @param bodyParam   请求报文，body
	 * @param headerParam 请求头 header
	 * @return 响应报文，body
	 * @throws HttpResponseException 非20X，异常
	 */
	public static JSONObject sentPostWithJson(String url, Map<String, ?> bodyParam, Map<String, String> headerParam)
			throws HttpResponseException {
		return sentPost(url, bodyParam, APPLICATION_JSON_UTF8, headerParam, HnlpConstant.REQUEST_METHOD_POST);
	}

	/**
	 * POST content-type: application/x-www-form-urlencoded
	 *
	 * @param url         请求地址
	 * @param bodyParam   请求报文 body
	 * @param headerParam 请求头 header
	 * @return 响应报文 response body
	 * @throws HttpResponseException 非20x响应，异常
	 */
	public static JSONObject sentPostWithForm(String url, Map<String, ?> bodyParam, Map<String, String> headerParam)
			throws HttpResponseException {
		return sentPost(url, bodyParam, APPLICATION_FORM_URLENCODED, headerParam, HnlpConstant.REQUEST_METHOD_POST);
	}

	/**
	 * @param url         请求地址
	 * @param bodyParam   请求参数，form形式
	 * @param headerParam 头部参数
	 * @return 20X时，响应body 其他的以异常形式扔出
	 */
	public static JSONObject sentPost(String url, Map<String, ?> bodyParam, MediaType contentType,
									  Map<String, String> headerParam, String requestMethod) throws HttpResponseException {
		String result = "";
		BufferedReader in = null;
		Integer status = null;
		try {
			URL realUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
			connection.setReadTimeout(DEFAULT_READ_TIME_OUT);
			connection.setRequestMethod(requestMethod);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 添加头部参数
			if (Objects.nonNull(headerParam) && !headerParam.isEmpty()) {
				headerParam.forEach(connection::setRequestProperty);
			}
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 处理报文并发送请求
			processPostContentType(contentType, bodyParam, connection);
			status = connection.getResponseCode();
			String line;
			try {
				for (in = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8)); StringUtils
						.isNotEmpty((line = in.readLine())); result = result + line) {
				}
			} catch (IOException e) {
				// 判断，如果发生400的异常，则调用再次获取错误流里面的数据，再次包装响应对象
				for (in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), UTF_8)); StringUtils
						.isNotEmpty((line = in.readLine())); result = result + line) {
				}
			}
		} catch (Exception e) {
			throw new HttpResponseException(
					Optional.ofNullable(status).orElse(HttpStatus.INTERNAL_SERVER_ERROR.value()), result,
					"请求失败");
		} finally {
			try {
				if (Objects.nonNull(in)) {
					in.close();
				}
			} catch (IOException e) {
				log.error("http 请求失败，流关闭异常", e);
			}
		}
		if (Objects.isNull(status)) {
			status = HttpStatus.REQUEST_TIMEOUT.value();
		}

		if (status >= HttpStatus.OK.value() && status < HttpStatus.MULTIPLE_CHOICES.value()) {
			// 目前，要素类型新增接口无返回内容，状态码为 202
			if (status == HttpStatus.ACCEPTED.value()) {
				return null;
			}
			return (JSONObject) JSONObject.parse(result);
		}
		throw new HttpResponseException(status, result, "POST请求失败");
	}

	/**
	 * 处理报文
	 *
	 * @param contentType 报文类型
	 * @param bodyParam   报文map
	 * @param connection  连接
	 * @throws IOException 请求异常
	 */
	private static void processPostContentType(MediaType contentType, Map<String, ?> bodyParam,
											   HttpURLConnection connection) throws IOException {
		Supplier<String> buildParam;
		contentType = Objects.isNull(contentType) ? APPLICATION_FORM_URLENCODED : contentType;
		if (APPLICATION_FORM_URLENCODED.getSubtype().equalsIgnoreCase(contentType.getSubtype())) {
			buildParam = () -> buildForm(bodyParam);
		} else if (APPLICATION_JSON.getSubtype().equalsIgnoreCase(contentType.getSubtype())) {
			buildParam = () -> buildJSON(bodyParam);
		} else {
			// 默认 form形式
			contentType = APPLICATION_FORM_URLENCODED;
			buildParam = () -> buildForm(bodyParam);
		}

		connection.setRequestProperty("Content-type", contentType.toString());
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), UTF_8))) {
			out.print(buildParam.get());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建 application/x-www-form-urlencoded 格式的请求报文
	 * <p>
	 * formData 只支持 [String, String]的格式，
	 *
	 * @param param form 参数
	 * @return 编码后的form参数
	 */
	private static String buildForm(Map<String, ?> param) {
		if (Objects.isNull(param) || param.isEmpty()) {
			return "";
		}
		return param.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue().toString())
				.collect(Collectors.joining("&"));
	}

	/**
	 * 构建json String
	 *
	 * @param param 参数
	 * @return json String
	 */
	private static String buildJSON(Map<String, ?> param) {
		if (Objects.isNull(param) || param.isEmpty()) {
			return "";
		}
		return JSON.toJSONString(param);
	}

	/**
	 * 发送get请求的工具方法（现主要用来接收集合类型的返回数据）
	 *
	 * @param url         请求地址
	 * @param param       body 参数
	 * @param headerParam header 参数
	 * @return 响应body
	 */
	public static Object sendGet(String url, Map<String, ?> param, Map<String, String> headerParam,
								 String requestMethod) throws HttpResponseException {
		log.info("========  开始发送GET请求 ========");
		BufferedReader in = null;
		String result = "";
		Integer status = null;
		try {
			URL realUrl = new URL(getUrl(url, param));
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod(requestMethod);
			connection.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
			connection.setReadTimeout(DEFAULT_READ_TIME_OUT);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			// 添加头部参数
			if (Objects.nonNull(headerParam) && !headerParam.isEmpty()) {
				for (String key : headerParam.keySet()) {
					connection.setRequestProperty(key, headerParam.get(key));
				}
			}
			connection.connect();
			status = connection.getResponseCode();
			Map<String, List<String>> map = connection.getHeaderFields();
			map.forEach((key, values) -> {
				log.info(key + ":" + values.toString());
			});

			String line;
			try {
				for (in = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8)); (line =
						in.readLine()) != null; result = result + line) {
				}
			} catch (IOException e) {
				for (in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), UTF_8)); (line =
						in.readLine()) != null; result = result + line) {

				}
			}

			log.info("======== 返回信息 ======== ：" + result);

		} catch (Exception var18) {
			throw new HttpResponseException(
					Optional.ofNullable(status).orElse(HttpStatus.INTERNAL_SERVER_ERROR.value()), result,
					"请求失败");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				log.error("http 请求失败，流关闭异常", e);
			}
		}
		if (Objects.nonNull(status) && status >= 200 && status < 300) {
			return JSON.parse(result);
		}
		//
		throw new HttpResponseException(Optional.ofNullable(status).orElse(HttpStatus.REQUEST_TIMEOUT.value()), result,
				"请求失败");

	}

	/**
	 * 发送get请求的工具方法（现主要用来接收集合类型的返回数据）
	 *
	 * @param url         请求地址
	 * @param param       body 参数
	 * @param headerParam header 参数
	 * @param charset     编码默认utf-8 参数
	 * @return 响应body
	 */
	public static String sendGet(String url, String charset, Map<String, ?> param, Map<String, String> headerParam) throws HttpResponseException {
		log.info("========  开始发送GET请求 ========");
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		Integer status = null;
		try {
			URL realUrl = new URL(getUrl(url, param));
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
			connection.setReadTimeout(DEFAULT_READ_TIME_OUT);
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
			// 添加头部参数
			if (Objects.nonNull(headerParam) && !headerParam.isEmpty()) {
				for (String key : headerParam.keySet()) {
					connection.setRequestProperty(key, headerParam.get(key));
				}
			}
			connection.connect();
			status = connection.getResponseCode();
			Map<String, List<String>> map = connection.getHeaderFields();
			map.forEach((key, values) -> {
				log.info(key + ":" + values.toString());
			});

			String line;
			try {
				for (in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset)); (line =
						in.readLine()) != null; result.append(line)) {
				}
			} catch (IOException e) {
				for (in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), charset)); (line =
						in.readLine()) != null; result.append(line)) {

				}
			}

			//log.info("======== 返回信息 ======== ：" + result);

		} catch (Exception var18) {
			throw new HttpResponseException(
					Optional.ofNullable(status).orElse(HttpStatus.INTERNAL_SERVER_ERROR.value()), result.toString(),
					"请求失败");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				log.error("http 请求失败，流关闭异常", e);
			}
		}
		if (Objects.nonNull(status) && status >= 200 && status < 300) {
			return result.toString();
		}
		//
		throw new HttpResponseException(Optional.ofNullable(status).orElse(HttpStatus.REQUEST_TIMEOUT.value()), result.toString(),
				"请求失败");

	}

	/**
	 * 拼接 get 请求的 url
	 *
	 * @param url   基础url
	 * @param param 参数
	 * @return 拼接完成的url
	 */
	private static String getUrl(String url, Map<String, ?> param) {
		StringBuilder sb = new StringBuilder();
		String equals = "=";
		// 拼接get请求的url参数
		sb.append(url);
		if (Objects.nonNull(param) && !param.isEmpty()) {
			sb.append("?");
			Object tempValue = null;
			int count = 0;
			for (String key : param.keySet()) {
				// 字符串类型参数
				tempValue = param.get(key);
				if (tempValue instanceof String && StringUtils.isNotBlank(tempValue.toString())) {
					sb.append(count == 0 ? "" : "&").append(key).append(equals).append(encodeUrl(tempValue.toString()));
					count++;
				}
				// 数组类型参数
				if (tempValue instanceof List) {
					List conditions = (List) tempValue;
					for (Object value : conditions) {
						if (value instanceof String && StringUtils.isNotBlank(value.toString())) {
							sb.append(count == 0 ? "" : "&").append(key).append(equals)
									.append(encodeUrl(value.toString()));
							count++;
						}
					}
				}
			}
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 编码url
	 *
	 * @param url raw url
	 * @return encoder url
	 */
	private static String encodeUrl(String url) {
		if (StringUtils.isBlank(url)) {
			return "";
		}
		try {
			return URLEncoder.encode(url, OSFCodeSetRegistry.UTF_8.getName());
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}


	/**
	 * 下载
	 *
	 * @param url 下载地址
	 * @return 下载输入流
	 */
	public static InputStream download(String url) {
		URL downUrl = null;
		try {
			downUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) downUrl.openConnection();
			// 设置超时间为3秒
			conn.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT);
			conn.setReadTimeout(DEFAULT_READ_TIME_OUT);
			return conn.getInputStream();
		} catch (MalformedURLException e) {
			log.info("url:{}", url);
			e.printStackTrace();
			throw new CommonException("");
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommonException("");
		}
	}

	/**
	 * 下载
	 *
	 * @param url 下载地址
	 * @return 二进制数据
	 */
	public static byte[] downloadData(String url) {
		URL downUrl = null;
		InputStream inputStream = download(url);
		try {
			return readInputStream(inputStream);
		} catch (IOException e) {
			log.error("文件下载异常", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}


	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		IOUtils.closeQuietly(bos);
		return bos.toByteArray();
	}


	public static JSONObject sentRequestWithJsonByAuthorization(String url, Map<String, ?> body, String token,
																String requestMethod) throws HttpResponseException {
		Objects.requireNonNull(token, "Authorization token");
		HashMap<String, String> authHeader = new HashMap<>();
		authHeader.put(AUTHORIZATION_HEADER, token);
		return sentWithJson(url, body, authHeader, requestMethod);
	}

	public static JSONObject sentWithJson(String url, Map<String, ?> bodyParam, Map<String, String> headerParam,
										  String requestMethod) throws HttpResponseException {
		return sentPost(url, bodyParam, APPLICATION_JSON_UTF8, headerParam, requestMethod);
	}

	public static Object sentRequestWithJsonReturnObject(String url, Map<String, ?> body, String token,
														 String requestMethod) throws HttpResponseException {
		Objects.requireNonNull(token, "Authorization token");
		HashMap<String, String> authHeader = new HashMap<>();
		authHeader.put(AUTHORIZATION_HEADER, token);
		return sentWithJsonReturnObject(url, body, authHeader, requestMethod);
	}

	public static Object sentWithJsonReturnObject(String url, Map<String, ?> bodyParam, Map<String, String> headerParam,
												  String requestMethod) throws HttpResponseException {
		return sendGet(url, bodyParam, headerParam, requestMethod);
	}

	public static String post(String url, Map<String, String> header, String jsonData, int timeout) {
		String result = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpDo = new HttpPost(url);
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(0).setConnectTimeout(0).build();
			httpDo.setConfig(requestConfig);
			StringEntity strEntity = new StringEntity(jsonData, "UTF-8");
			strEntity.setContentEncoding("UTF-8");
			//发送json数据需要设置contentType
			strEntity.setContentType("application/json; charset=UTF-8");
			if (null != header) {
				header.forEach((k, v) -> {
					httpDo.addHeader(k, v);
				});
			}
			httpDo.setEntity(strEntity);
			HttpResponse res = client.execute(httpDo);
			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				// 返回json格式：
				result = EntityUtils.toString(res.getEntity(), "UTF-8");
			} else {
				throw new RuntimeException("服务器异常，参数" + jsonData + "，状态码为" + statusCode + "，请联系管理员");
			}
		} catch (Exception e) {
			throw new RuntimeException("连接服务器异常，参数" + jsonData + "，错误消息为" + e.getMessage() + "，请联系管理员");
		}
		return result;
	}

}
