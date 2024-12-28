package com.hzl.hadoop.app.service.impl;

import com.hzl.hadoop.app.mapper.Contractmapper;
import com.hzl.hadoop.app.service.TestService;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.executor.ExecutorUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * description
 *
 * @author hzl 2020/05/07 8:36 PM
 */
@Component
@Slf4j
public class TestServiceImpl implements TestService {

	@Autowired
	private Contractmapper contractmapper;

	@Override
	@Transactional
	public int update(int i) {
		log.info("日期");
		contractmapper.insertTest(i);
		if(i==0){
			throw new CommonException("故意抛出异常");
		}
		return 0;
	}

	//@Scheduled(cron = "0 0 19 * * ?")
	@Scheduled(cron = "59 59 18 * * ?")
	@Override
	public void robCar() {
		//当期时间小于2024-03-30 00:00
		LocalDateTime now = LocalDateTime.now();
		if (now.isBefore(LocalDateTime.of(2024, 3, 30, 0, 0))) {
			fixExecutor(10,1000);
		}

	}
	private Map<String, String> buildHeader() {
		Map<String, String> header = new HashMap();
		//authority:pre-api.ideamake.cn
		//accept:*/*
		//accept-language:zh-CN,zh;q=0.9,en;q=0.8,de;q=0.7
		//content-type:application/json
		//im-token:ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5Sm5jbTkxY0VOdlpHVWlPaUp6ZHlJc0luQnliMnBsWTNSRGIyUmxJam9pYzNjM056QTNJaXdpZFhObGNpSTZJbnRjSW1SdmJXRnBibHdpT2x3aU1qVTJOMkUxWldNNU56QTFaV0kzWVdNeVl6azROREF6TTJVd05qRTRPV1F0ZDJWaVhDSXNYQ0puY205MWNFTnZaR1ZjSWpwY0luTjNYQ0lzWENKdmNHVnVTV1JjSWpwY0lqVTBPVEV6TkRrMk16RTNOakF5TVRVek1qYzJMWGRsWWx3aUxGd2ljbTlzWlZ3aU9sd2lWa2xUU1ZSUFVsd2lMRndpZEdWdVlXNTBTV1JjSWpwY0luZDRZelF4WkROak56Z3pPREZpT0dWak0xd2lMRndpZFhObGNrbGtYQ0k2WENJeE56Y3lPVGcxTkRBME9UZ3pPRFl4TWpRNFhDSjlJaXdpWVhCd1NXUWlPaUozZUdNME1XUXpZemM0TXpneFlqaGxZek1pTENKemIzVnlZMlVpT2lKaGNIQnNaWFFpTENKMlpYSnphVzl1SWpvaU1TNHdJaXdpY0d4aGRHWnZjbTFGYm5WdElqb2liMjVzYVc1bFQzQmxibWx1WnlJc0luVjFhV1FpT201MWJHd3NJbTl3Wlc1SlpDSTZJalUwT1RFek5EazJNekUzTmpBeU1UVXpNamMyTFhkbFlpSXNJbkJoY21GdGN5STZlMzBzSW1WNGNDSTZNVGN4TWprd056UXlNbjAuU1U1QkdyVmc3M09NcURZcFBva0htSzR6R1FkYVQzbTQzNFcyLTRtR1pSaw==
		//origin:https://pre-h5.ideamake.cn
		//referer:https://pre-h5.ideamake.cn/
		//sec-ch-ua:"Google Chrome";v="119", "Chromium";v="119", "Not?A_Brand";v="24"
		//sec-ch-ua-mobile:?0
		//sec-ch-ua-platform:"macOS"
		//sec-fetch-dest:empty
		//sec-fetch-mode:cors
		//sec-fetch-site:same-site
		//user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36


		header.put("authority", "pre-api.ideamake.cn");
		header.put("accept", "*/*");
		header.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,de;q=0.7");
		header.put("content-type", "application/json");
		header.put("im-token", "ZXlKMGVYQWlPaUpLVjFRaUxDSmhiR2NpT2lKSVV6STFOaUo5LmV5Sm5jbTkxY0VOdlpHVWlPaUp6ZHlJc0luQnliMnBsWTNSRGIyUmxJam9pYzNjM056QTNJaXdpZFhObGNpSTZJbnRjSW1SdmJXRnBibHdpT2x3aU1qVTJOMkUxWldNNU56QTFaV0kzWVdNeVl6azROREF6TTJVd05qRTRPV1F0ZDJWaVhDSXNYQ0puY205MWNFTnZaR1ZjSWpwY0luTjNYQ0lzWENKdmNHVnVTV1JjSWpwY0lqVTBPVEV6TkRrMk16RTNOakF5TVRVek1qYzJMWGRsWWx3aUxGd2ljbTlzWlZ3aU9sd2lWa2xUU1ZSUFVsd2lMRndpZEdWdVlXNTBTV1JjSWpwY0luZDRZelF4WkROak56Z3pPREZpT0dWak0xd2lMRndpZFhObGNrbGtYQ0k2WENJeE56Y3lPVGcxTkRBME9UZ3pPRFl4TWpRNFhDSjlJaXdpWVhCd1NXUWlPaUozZUdNME1XUXpZemM0TXpneFlqaGxZek1pTENKemIzVnlZMlVpT2lKaGNIQnNaWFFpTENKMlpYSnphVzl1SWpvaU1TNHdJaXdpY0d4aGRHWnZjbTFGYm5WdElqb2liMjVzYVc1bFQzQmxibWx1WnlJc0luVjFhV1FpT201MWJHd3NJbTl3Wlc1SlpDSTZJalUwT1RFek5EazJNekUzTmpBeU1UVXpNamMyTFhkbFlpSXNJbkJoY21GdGN5STZlMzBzSW1WNGNDSTZNVGN4TWprd056UXlNbjAuU1U1QkdyVmc3M09NcURZcFBva0htSzR6R1FkYVQzbTQzNFcyLTRtR1pSaw==");
		header.put("origin","https://pre-h5.ideamake.cn");
		header.put("referer","https://pre-h5.ideamake.cn/");
		header.put("sec-ch-ua","\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
		header.put("sec-ch-ua-mobile","?0");
		header.put("sec-ch-ua-platform","\"macOS\"");
		header.put("sec-fetch-dest","empty");
		header.put("sec-fetch-mode","cors");
		header.put("sec-fetch-site","same-site");
		header.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");

		return header;
	}

	public String post(String url, Map<String, String> headers, String body) {
		if (url == null || body == null) {
			throw new IllegalArgumentException("URL and body must not be null");
		}

		Request.Builder builder = new Request.Builder()
				.url(url)
				.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body));

		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}
		builder.addHeader("Accept-Charset", "UTF-8");

		Request request = builder.build();
		OkHttpClient.Builder clientBuidler = new OkHttpClient.Builder().connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS);
		OkHttpClient client = clientBuidler.build();
		Response response = null;

		try {
			response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response.code() + " with message: " + response.message());
			}
			// 使用UTF-8编码将字节转换为字符串
			String responseBody = response.body().string();
			// Consider using a more secure logging mechanism that doesn't log sensitive data.
			if (responseBody.length() > 0) {
				log.info("Response received. First 100 characters: " + responseBody.substring(0, Math.min(responseBody.length(), 100)));
			} else {
				log.info("Response received. Empty body.");
			}
			return responseBody;
		} catch (IOException e) {
			log.error("Error occurred while sending request to " + url, e);
			throw new RuntimeException(e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	public void getUrl(String roomId){
		String url="https://pre-api.ideamake.cn/super-gw/online-sale/mobile/choosing-room-activity/195/public-test-pick";
		String body="{\"phoneNumber\":\"17602153276\",\"roomId\":"+roomId+"}";
		String resp=post(url,buildHeader(),body);
		log.info(resp);
	}

	public static  void
	fixExecutor(int threadNum, int executeNum) {
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);

		for (int i = 0; i < executeNum; i++) {
			int finalI = i;
			Future<Long> future = executor.submit(() -> {
				TestServiceImpl testService=new TestServiceImpl();
				testService.getUrl("213186");
				testService.getUrl("213187");
				testService.getUrl("213188");
				testService.getUrl("213225");
				testService.getUrl("213122");

				return System.currentTimeMillis();
			});
		}

		executor.shutdown();
	}

//	public static void main(String[] args) {
//		//并发请求
//		LocalDateTime now = LocalDateTime.now();
//		if (now.isBefore(LocalDateTime.of(2024, 3, 30, 0, 0))) {
//			fixExecutor(10,10);
//		}
//
//
//	}
}
