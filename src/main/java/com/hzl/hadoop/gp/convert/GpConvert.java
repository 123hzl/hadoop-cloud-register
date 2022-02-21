package com.hzl.hadoop.gp.convert;

import com.hzl.hadoop.util.HttpResponseException;
import com.hzl.hadoop.util.HttpUtils;
import com.hzl.hadoop.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 * 基于新浪股票
 *
 * @author hzl 2020/10/31 12:53 PM
 */
@Slf4j
public class GpConvert {

	//===============================================================================
	//参考：https://blog.csdn.net/cxu123321/article/details/104239214
	//  https://hq.sinajs.cn/rn=1604120110291&list=sh600887,sh600887_i,sh163035,bk_new_sphy
	// http://hq.sinajs.cn/list=sh600887
	//由于股票交易以一百股为基本单位，所以在使用时成交的股票数，通常需要除以一百；
	//样例：var hq_str_sh600887="伊利股份,40.990,44.140,39.730,41.500,39.730,0.000,39.730,134286954,5371200262.000,0,0.000,0,0.000,0,0.000,0,0.000,0,0.000,8766337,39.730,910532,39.740,47100,39.750,209700,39.760,10600,39.770,2020-10-30,15:00:01,00,";
	//===============================================================================
	//请求数据
	private String httpGet(String url) {
		log.info("url:" + url);
		//String data=HttpUtil.get(url);
		String data = null;
		try {
			Map<String, String> headerParam = new HashMap<>();
			headerParam.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,de;q=0.7");
			headerParam.put("Host", "hq.sinajs.cn");
			headerParam.put("Referer", "https://vip.stock.finance.sina.com.cn/");
			headerParam.put("Sec-Fetch-Site", "cross-site");
			headerParam.put("Sec-Fetch-Mode", "no-cors");
			headerParam.put("Sec-Fetch-Dest", "script");
			headerParam.put("Cache-Control", "no-cache");
			headerParam.put("Pragma", "no-cache");
			headerParam.put("sec-ch-ua", "Not;A Brand\";v=\"99\", \"Google Chrome\";v=\"97\", \"Chromium\";v=\"97");
			headerParam.put("sec-ch-ua-mobile", " ?0");
			headerParam.put("sec-ch-ua-platform", "centOS");
			data = HttpUtils.sendGet(url, "GB18030", null, headerParam);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
		return data;
	}

	//截取字符串
	private String[] subString(String data) {
		String first[] = data.split("\"", -1);

		//log.info("截取结果{}", StringUtils.join(first,"-"));
		String dataArry[] = first[1].split(",");
		//log.info("第二次截取" + Arrays.toString(dataArry));


		return dataArry;
	}


	//映射key-value
	private Map<String, String> keyValue(String[] keys, String[] dataArry) {
		String[] keysModel = {"股票名字", "今日开盘价", "昨日收盘价", "当前价格", "今日最高价", "今日最低价",
				"竞买价，即买一报价", "竞卖价，即卖一报价",
				"成交的股票数", "成交金额/元"
				, "买一申请股数", "买一报价", "买二申请股数", "买二报价", "买三申请股数", "买三报价", "买四申请股数", "买四报价", "买五申请股数", "买五报价"
				, "卖一申请股数", "卖一报价", "卖二申请股数", "卖二报价", "卖三申请股数", "卖三报价", "卖四申请股数", "卖四报价", "卖五申请股数", "卖五报价"
				, "日期", "时间"};
		if (keys != null && keys.length > 0) {
			//如果指定key模版，就用指定的，否则用默认的keysModel
			return ListUtils.listToMap(Arrays.asList(keys), Arrays.asList(dataArry));
		} else {
			return ListUtils.listToMap(Arrays.asList(keysModel), Arrays.asList(dataArry));
		}

	}

	/**
	 * @param url  请求地址
	 * @param keys key模版，可以为空，为空用默认的
	 * @return
	 * @author hzl 2020-10-31 2:47 PM
	 */
	//伊利股票信息获取
	public Map<String, String> getGpInfo(String url, String[] keys) {
		//获取数据
		String data = httpGet(url);
		log.info("响应结果{}", data);
		//解析数据
		String[] dataSubString = subString(data);
		//转化数据
		Map<String, String> result = keyValue(keys, dataSubString);
		return result;
	}


}
