package com.hzl.hadoop.gp.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzl.hadoop.gp.vo.CpVO;
import com.hzl.hadoop.util.HttpResponseException;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 * 彩票爬取，大乐透
 *
 * @author hzl 2022/01/26 3:34 PM
 */
@Slf4j
public class CpConvert {
	//https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=30&isVerify=1&pageNo=74

	public static String DLT_TUL = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry";

	public static String httpGet(String url, int pageNo) {
		log.info("url:{}", url);
		String data = null;
		try {
			Map<String, String> headerParam = new HashMap<>();

			Map<String, String> param = new HashMap();
			param.put("pageNo", String.valueOf(pageNo));
			param.put("pageSize", "30");
			param.put("gameNo", "85");
			param.put("provinceId", "0");
			param.put("isVerify", "1");


			data = HttpUtils.sendGet(url, "utf-8", param, headerParam);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
		return data;
	}


	public static void main(String args[]) {

		String data = httpGet(DLT_TUL, 75);
		String jsonArray = JSONObject.parseObject(data).getJSONObject("value").getString("list");
		List<CpVO> cpVOS = JSONArray.parseArray(jsonArray, CpVO.class);
		cpVOS.forEach(a -> {
			log.info("结果{}", a.convert());
		});

	}

}
