package com.hzl.hadoop.userlog;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 *
 * @author hzl
 */
@Slf4j
public class IPUtil {

	private final static boolean ipLocal = false;

	/**
	 * 根据ip获取详细地址
	 */
	public static String getCityInfo(String ip) {
		if (ipLocal) {
			//待开发
			return null;
		} else {
			return getHttpCityInfo(ip);
		}
	}

	/**
	 * 获取ip
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	/**
	 * 根据ip获取详细地址
	 * 临时使用，待调整
	 */
	public static String getHttpCityInfo(String ip) {
		String api = String.format("http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true", ip);
		JSONObject object = JSON.parseObject(HttpUtil.get(api, 10000));
		log.info(object.toJSONString());
		return object.getString("addr");
	}

}
