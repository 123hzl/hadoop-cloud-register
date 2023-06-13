package com.hzl.hadoop.area;

import com.alibaba.fastjson.JSONObject;
import com.hzl.hadoop.util.HttpResponseException;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 * 根据地名从百度地图获取经纬度
 * 参考：https://lbsyun.baidu.com/index.php?title=webapi/guide/webservice-geocoding
 * @author hzl 2023/05/23 2:30 PM
 */
@Slf4j
public class BaiduAreaLongitudeLatitude {

	static final String baiduUrl = "https://api.map.baidu.com/geocoding/v3/";

	public static Map<String, String> getLongitudeLatitude() throws HttpResponseException {
		Map<String, String> map = new HashMap<>();

		Map<String, Object> param = new HashMap<>();
		param.put("city", "上海市");
		param.put("address", "建韬商业广场");

		param.put("output", "json");
		//param.put("callback", "showLocation");

		//国测局坐标
		param.put("ret_coordtype", "gcj02ll");
		param.put("ak", "svsKSUpRTgTu73tK4zqe9RVveu7x9Thb");
		param.put("extension_analys_level", true);
		JSONObject result= (JSONObject) HttpUtils.sendGet(baiduUrl, param, null, "GET");
		return map;
	}

	public static void main(String args[]){
		try {
			getLongitudeLatitude();
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
	}
}
