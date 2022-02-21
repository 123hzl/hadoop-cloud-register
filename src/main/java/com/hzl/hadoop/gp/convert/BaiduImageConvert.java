package com.hzl.hadoop.gp.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hzl.hadoop.gp.vo.BaiduImageVO;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 * https://image.baidu.com/search/acjson?tn=resultjson_com&logid=8446578268906037508&ipn=rj&ct=201326592&is=&fp=result&fr=&word=%E8%A2%81%E5%86%B0%E5%A6%8D&queryWord=%E8%A2%81%E5%86%B0%E5%A6%8D
 * &cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=&hd=&latest=&copyright=&s=&se=&tab=&width=&height=
 * &face=0&istype=2&qc=&nc=1&expermode=&nojc=&isAsync=&pn=30&rn=30&gsm=1e&1642398735673=
 *
 * @author hzl 2022/01/17 2:01 PM
 */
@Slf4j
public class BaiduImageConvert {

	public static String URL = "https://image.baidu.com/search/acjson";

	public static Map<String, String> getParam(String pn, String rn, String gsm, String searchImage) {
		Map<String, String> param = new HashMap<>();
		param.put("tn", "resultjson_com");
		param.put("logid", "8446578268906037508");
		param.put("ipn", "rj");
		param.put("ct", "201326592");
		param.put("is", "");
		param.put("fp", "result");
		param.put("fr", "");
		//图片搜索条件，默认袁冰妍
		param.put("word", searchImage);
		//图片搜索条件，默认袁冰妍
		param.put("queryWord", searchImage);
		param.put("cl", "2");
		param.put("lm", "-1");
		param.put("ie", "utf-8");
		param.put("oe", "utf-8");
		param.put("adpicid", "");
		param.put("st", "-1");
		param.put("z", "");
		param.put("ic", "");
		param.put("hd", "");
		param.put("latest", "");
		param.put("copyright", "");
		param.put("s", "");
		param.put("se", "");
		param.put("tab", "");
		param.put("width", "");
		param.put("height", "");
		param.put("face", "0");
		param.put("istype", "2");
		param.put("qc", "");
		param.put("nc", "1");
		param.put("expermode", "");
		param.put("nojc", "");
		param.put("isAsync", "");

		//总数量,默认0
		param.put("pn", pn);
		//每次次查询数量，默认30
		param.put("rn", rn);
		//gsm 上一次查询返回的值,默认空
		param.put("gsm", gsm);


		return param;
	}

	/**
	 * <p>
	 * 调用接口  feature设备类型
	 * </p>
	 *
	 * @author hzl 2021/12/16 1:07 PM
	 */
	public static String httpGet(Map<String, String> param, String url) {
		String imageResult = null;
		try {

			Map<String, String> heads = new HashMap<>();
			//heads.put("cookie","BDqhfp=%E8%A2%81%E5%86%B0%E5%A6%8D%26%26-10-1undefined%26%260%26%261; PSTM=1640320649; BAIDUID=056C6EDA8FEF80A3C0840FDBFADD47B4:FG=1; BIDUPSID=4D585F327E8335EDFDE28585A57913E0; __yjs_duid=1_14031fcd7eda6abea18737514ba910791640927553054; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BCLID=11332145502706792034; BDSFRCVID=7DDOJeC62igxjHnHTXhrJFyOemRCbdrTH6aoboP-fvOhJA7deQTgEG0P-M8g0Ku-S2-cogKK0eOTHkLF_2uxOjjg8UtVJeC6EG0Ptf8g0f5; H_BDCLCKID_SF=tRk8oD8MtKvDqTrP-trf5DCShUFsBJcJB2Q-XPoO3KJ-Hn06yhOiQT-pQlr9KRj0LI7gbfbgy4op8P3y0bb2DUA1y4vpK5jr3eTxoUJ2XbbdMh5qqtnWyf0ebPRiJPQ9Qg-qahQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0MC09j6KhDTPVKgTa54cbb4o2WbCQ5qOd8pcN2b5oQT8wy2cnBp3vLC6ZKtTv5R6vOPQKDpOUWfAkXpJvQnJjt2JxaqRC5bj6Sh5jDh3Me-AsLn6te6jzaIvy0hvctn5cShPCyUjrDRLbXU6BK5vPbNcZ0l8K3l02V-bIe-t2XjQhjGtOtjDttb3aQ5rtKRTffjrnhPF3jjc3XP6-hnjy3bRNaT6JWUbiVJj_QRrcbtuUypjpJh3RymJJ2-39LPO2hpRjyxv4bP_93-oxJpOJfIJM5McaHCoADb3vbURvD-Lg3-AqBM5dtjTO2bc_5KnlfMQ_bf--QfbQ0hOhqP-jBRIE_D0yJD_hhIvPKITD-tFO5eT22-us2Rcl2hcHMPoosIJ-yKcxyh0RWhQZ0Jc95Cjia-QCtMbUoqRHXnJi0btQDPvxBf7p5K6g-p5TtUJMbbRTLp6hqjDlhMJyKMnitIT9-pPKWhQrh459XP68bTkA5bjZKxtq3mkjbPbDfn028DKuDj-WDjJWeaR22PRt5Poa3RTeb6rjDnCrjUOUXUI8LNDHa4bO2ebXaUb92Poks4TObtcCLPDOKRO7ttoyKDOTsRQKBbnkED3gbqQxbUL1Db0LW6vMtg3tsx8-HROoepvoD-oc3MkfX-jdJJQOBKQB0KnGbUQkeq8CQft20b0EeMtjKjLEK5r2SC-hJK-K3f; BCLID_BFESS=11332145502706792034; BDSFRCVID_BFESS=7DDOJeC62igxjHnHTXhrJFyOemRCbdrTH6aoboP-fvOhJA7deQTgEG0P-M8g0Ku-S2-cogKK0eOTHkLF_2uxOjjg8UtVJeC6EG0Ptf8g0f5; H_BDCLCKID_SF_BFESS=tRk8oD8MtKvDqTrP-trf5DCShUFsBJcJB2Q-XPoO3KJ-Hn06yhOiQT-pQlr9KRj0LI7gbfbgy4op8P3y0bb2DUA1y4vpK5jr3eTxoUJ2XbbdMh5qqtnWyf0ebPRiJPQ9Qg-qahQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0MC09j6KhDTPVKgTa54cbb4o2WbCQ5qOd8pcN2b5oQT8wy2cnBp3vLC6ZKtTv5R6vOPQKDpOUWfAkXpJvQnJjt2JxaqRC5bj6Sh5jDh3Me-AsLn6te6jzaIvy0hvctn5cShPCyUjrDRLbXU6BK5vPbNcZ0l8K3l02V-bIe-t2XjQhjGtOtjDttb3aQ5rtKRTffjrnhPF3jjc3XP6-hnjy3bRNaT6JWUbiVJj_QRrcbtuUypjpJh3RymJJ2-39LPO2hpRjyxv4bP_93-oxJpOJfIJM5McaHCoADb3vbURvD-Lg3-AqBM5dtjTO2bc_5KnlfMQ_bf--QfbQ0hOhqP-jBRIE_D0yJD_hhIvPKITD-tFO5eT22-us2Rcl2hcHMPoosIJ-yKcxyh0RWhQZ0Jc95Cjia-QCtMbUoqRHXnJi0btQDPvxBf7p5K6g-p5TtUJMbbRTLp6hqjDlhMJyKMnitIT9-pPKWhQrh459XP68bTkA5bjZKxtq3mkjbPbDfn028DKuDj-WDjJWeaR22PRt5Poa3RTeb6rjDnCrjUOUXUI8LNDHa4bO2ebXaUb92Poks4TObtcCLPDOKRO7ttoyKDOTsRQKBbnkED3gbqQxbUL1Db0LW6vMtg3tsx8-HROoepvoD-oc3MkfX-jdJJQOBKQB0KnGbUQkeq8CQft20b0EeMtjKjLEK5r2SC-hJK-K3f; delPer=0; PSINO=3; BAIDUID_BFESS=39CE95697ADE6F6AAAF0BCF7A7E2558A:FG=1; BDRCVFR[dG2JNJb_ajR]=mk3SLVN4HKm; BDRCVFR[-pGxjrCMryR]=mk3SLVN4HKm; BDRCVFR[Txj84yDU4nc]=mk3SLVN4HKm; indexPageSugList=%5B%22%E8%A2%81%E5%86%B0%E5%A6%8D%22%5D; cleanHistoryStatus=0; ariaDefaultTheme=default; ariaFixed=true; ariaReadtype=1; H_PS_PSSID=35414_35740_35105_31660_35627_35488_35456_34584_35490_35699_35693_35325_26350_35746; BA_HECTOR=80848k050gag04003s1gua0b50q; ariaStatus=false; userFrom=null; ab_sr=1.0.1_ZDIxMTQwYjFlMDA5M2NiZjc1NjRjOTBkZWI5NzIwYmU1N2VkMzQ3ZDFjMGNhYmI1MjZiMTJiNmE3NjVhNzQzMGYwMTYzOTZiNzBkOGJhMDM0MDk0YjE1ZGE2MjM4YWI4MGY0ZWIxMzFmZTQzZGFiYTU1OTExZjc1MWZhY2NkNjQ3MGRlZDlhOTZjZmQwZmIzNTQzOWRiYzUzYzg0MTQzYQ==");
			//heads.put("Referer","https://image.baidu.com/search/index?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1642398717954_R&pv=&ic=&nc=1&z=&hd=&latest=&copyright=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&dyTabStr=&ie=utf-8&sid=&word=%E8%A2%81%E5%86%B0%E5%A6%8D");
			heads.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,de;q=0.7");
			heads.put("Connection", "keep-alive");
			heads.put("Upgrade-Insecure-Requests", "1");
			heads.put("Accept", "text/plain, */*; q=0.01");
			heads.put("Host", "image.baidu.com");
			heads.put("Pragma", "no-cache");
			heads.put("Sec-Fetch-Mode", "cors");

			imageResult = HttpUtils.sendGet(url, "UTF-8", param, heads);
			return imageResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageResult;
	}


	public static BaiduImageVO parseImage(String httpResult) {
		JSONObject jsonObject = JSONObject.parseObject(httpResult);
		JSONArray data = jsonObject.getJSONArray("data");
		String gsm = jsonObject.getString("gsm");

		List<String> imageUrl = data.stream().map(a -> (String) ((JSONObject) a).get("thumbURL")).collect(Collectors.toList());

		BaiduImageVO baiduImageVO = new BaiduImageVO();
		baiduImageVO.setGsm(gsm);
		baiduImageVO.setImages(imageUrl);
		return baiduImageVO;

	}


	public static void main(String args[]) {
		int pn = 30;
		int rn = 30;

		String httpResult = httpGet(getParam(String.valueOf(pn), String.valueOf(rn), "", "袁冰妍"), URL);
		BaiduImageVO baiduImageVO = parseImage(httpResult);

		//只获取3000张图片
		for (int i = 0; i <= 100; i++) {
			String gsm = baiduImageVO.getGsm();
			if (baiduImageVO != null && StringUtils.hasLength(gsm)) {
				//插入数据库todo

				//插入数据库结束
				log.info("结果{}", baiduImageVO.getImages());
				pn = pn + rn;
				httpResult = httpGet(getParam(String.valueOf(pn), String.valueOf(rn), baiduImageVO.getGsm(), "袁冰妍"), URL);
				baiduImageVO = parseImage(httpResult);

			} else {
				break;
			}
		}


	}


}
