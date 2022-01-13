package com.hzl.hadoop.gp.convert;

import com.alibaba.fastjson.JSONObject;
import com.hzl.hadoop.gp.vo.XlwebFriendsVO;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hzl.hadoop.gp.constant.GpUrlConstant.XLWB_COOKIE;

/**
 * description
 * 新浪微博获取好友列表
 * https://weibo.com/ajax/friendships/friends?page=1&uid=1590681790
 *
 * @author hzl 2021/12/24 2:24 PM
 */
@Slf4j
public class XlFriendConvert {

	private static final String friendsUrl = "https://weibo.com/ajax/friendships/friends";

	/**
	 * <p>
	 * 调用接口
	 * </p>
	 *
	 * @author hzl 2021/12/16 1:07 PM
	 */
	public static String httpGet(Map<String, String> param, String url) {
		String friendsResult = null;
		try {

			Map<String, String> heads = new HashMap<>();
			heads.put("cookie", XLWB_COOKIE);
			friendsResult = HttpUtils.sendGet(url, "UTF-8", param, heads);
			return friendsResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friendsResult;
	}

	/**
	 * <p>
	 * 解析数据
	 * </p>
	 *
	 * @author hzl 2021/12/16 1:07 PM
	 */
	public static List<XlwebFriendsVO> parseFriends(String friends) {
		//将字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.parseObject(friends);
		String data = jsonObject.getString("users");
		List<XlwebFriendsVO> xlwebFriendsVOList = JSONObject.parseArray(data, XlwebFriendsVO.class);
		//log.info("好友列表{}", xlwebFriendsVOList.toString());
		return xlwebFriendsVOList;
	}

	public static List<XlwebFriendsVO> getFriendsAll(String uid) {
		Map<String, String> param = new HashMap<>();
		param.put("uid", uid);
		List<XlwebFriendsVO> xlwebFriendsVOList = new ArrayList<>();
		for (int i = 1; ; i++) {
			param.put("page", String.valueOf(i));
			String friends = httpGet(param, friendsUrl);
			List<XlwebFriendsVO> xlwebFriendsVOS = parseFriends(friends);
			if (CollectionUtils.isEmpty(xlwebFriendsVOS)) {
				break;
			}
			xlwebFriendsVOList.addAll(xlwebFriendsVOS);
		}
		return xlwebFriendsVOList;
	}

	public static void main(String args[]) {
//		Map<String, String> param= new HashMap<>();
//		param.put("page","2");
//		param.put("uid","7530253054");
//		String friends=httpGet(param,friendsUrl);

		log.info(getFriendsAll("7530253054").toString());
	}


}
