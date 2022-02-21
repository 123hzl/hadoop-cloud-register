package com.hzl.hadoop.gp.convert;

import com.hzl.hadoop.gp.entity.GpXlPercentEntity;
import com.hzl.hadoop.gp.vo.XlGpPercentVO;
import com.hzl.hadoop.util.HttpResponseException;
import com.hzl.hadoop.util.HttpUtils;
import com.hzl.hadoop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 * 获取分价数据
 *
 * @author hzl 2022/01/21 2:35 PM
 */
@Slf4j
public class GpPercentConvert {

	private static String PERCENT_URL = "https://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_Bill.GetBillList";

	private static String httpGet(String url, String gpCode,String page) {
		log.info("url:{}",url);
		//String data=HttpUtil.get(url);
		String data = null;
		try {
			Map<String, String> headerParam = new HashMap<>();
			headerParam.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,de;q=0.7");
			headerParam.put("Host", "hq.sinajs.cn");
			headerParam.put("Referer", "https://vip.stock.finance.sina.com.cn/quotes_service/view/cn_price.php?symbol=" + gpCode);
			headerParam.put("Sec-Fetch-Site", "cross-site");
			headerParam.put("Sec-Fetch-Mode", "no-cors");
			headerParam.put("Sec-Fetch-Dest", "script");
			headerParam.put("Cache-Control", "no-cache");
			headerParam.put("Pragma", "no-cache");
			headerParam.put("sec-ch-ua", "Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"97\", \"Chromium\";v=\"97");
			headerParam.put("sec-ch-ua-mobile", " ?0");
			headerParam.put("sec-ch-ua-platform", "macOS");
			headerParam.put("authority", "vip.stock.finance.sina.com.cn");
			headerParam.put("content-type", "application/x-www-form-urlencoded");
			headerParam.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,de;q=0.7");
			Map<String, String> param = new HashMap();
			//?symbol=sh600690&num=60&page=1&sort=ticktime&asc=0&volume=40000&amount=0&type=0&day=
			param.put("symbol", gpCode);
			param.put("page", page);
			param.put("sort", "ticktime");
			//如果为0，则返回所有数据
			param.put("num", "60");


			param.put("asc", "0");
			param.put("amount", "0");
			param.put("type", "0");
			//param.put("day","2022-01-20");
			param.put("day", LocalDate.now().toString());
			data = HttpUtils.sendGet(url, "gbk", param, headerParam);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
		return data;
	}

	//https://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_Bill.GetBillListCount?symbol=sz000651&num=60&page=1&sort=ticktime&asc=0&volume=40000&amount=0&type=0&day=
	//获取总页数


	public static List<GpXlPercentEntity> parse(String gpCode,String page) {
		String data = httpGet(PERCENT_URL, gpCode,page);
		log.info("数据{}",data);
		return JsonUtils.jsonStringToList(data, GpXlPercentEntity.class);

	}


}
