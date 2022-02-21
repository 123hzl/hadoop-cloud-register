package com.hzl.hadoop.gp.convert;

import cn.hutool.Hutool;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import com.hzl.hadoop.gp.mapper.XlwebImagesMapper;
import com.hzl.hadoop.gp.vo.XlwebImagesVO;
import com.hzl.hadoop.util.HttpResponseException;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hzl.hadoop.gp.constant.GpUrlConstant.XLWB_COOKIE;

/**
 * description
 * 图片爬虫，目前爬取新浪微博的图片
 * 微博访问地址：https://weibo.com/u/5975234660
 * 用户信息接口：https://weibo.com/ajax/profile/info?uid=5975234660
 * 个人详细信息：https://weibo.com/ajax/profile/detail?uid=5975234660
 * 获取好友列表：https://weibo.com/ajax/friendships/friends?page=1&uid=1590681790 （如果设置了需要关注才可查看必须登陆）
 * 图片接口：https://weibo.com/ajax/statuses/mymblog?uid=5975234660&page=2&feature=0
 * 获取所有图片接口：https://weibo.com/ajax/profile/getImageWall?uid=2838690360&sinceid=0&has_album=true
 * @author hzl 2021/12/16 1:06 PM
 */
@Slf4j
@Component
public class ImageConvert {

	//https://weibo.com/ajax/profile/getImageWall?sinceid=4230649125084697_-1_20180419_-1&has_album=true&uid=2838690360
//	1590681790
	private static final String imageUrl="https://weibo.com/ajax/statuses/mymblog";

	private static final String imageUrlAll="https://weibo.com/ajax/profile/getImageWall";

	private static final String originalImageUrl="https://wx1.sinaimg.cn/orj1080/";


	private static final String visit="https://login.sina.com.cn/visitor/visitor?a=restore&cb=restore_back&from=weibo&_rand=0.34054252895247306&entry=sinawap";

	@Autowired
	XlwebImagesMapper mapper;

	public static Map<String,String> getCookie(){
		Map<String,String> cookie = new HashMap<>();
		try {
			HttpUtils.sendGet(visit, "GB2312",null,null);

		} catch (HttpResponseException e) {
			e.printStackTrace();
		}

		return cookie;
	}
	
	/**
	 * <p>
	 * 调用接口  feature设备类型
	 * </p>
	 * 
	 * @author hzl 2021/12/16 1:07 PM
	 */
	public static String httpGet(Map<String,String> param,String url){
		String imageResult=null;
		try {

			Map<String,String> heads = new HashMap<>();
			heads.put("cookie",XLWB_COOKIE);
			imageResult=HttpUtils.sendGet(url, "UTF-8", param, heads);
			return imageResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageResult;
	}

	
	
	/**
	 * <p>
	 * 解析数据
	 * </p>
	 * 
	 * @author hzl 2021/12/16 1:07 PM
	 */
	public static boolean parseImage(String image){
		//将字符串转换成jsonObject
		JSONObject jsonObject=JSONObject.parseObject(image);
		JSONObject data= jsonObject.getJSONObject("data");
		JSONArray list= data.getJSONArray("list");
		if(list.size()==0){
			return true;
		}
		list.forEach(a->{
			((JSONObject)a).getJSONArray("pic_ids").forEach(b->{
				String originalImageUrl="https://wx1.sinaimg.cn/orj1080/";
				log.info(originalImageUrl+b+".jpg");
			});
		});
		return false;
	}

	public static XlwebImagesVO parseImageAll(String image){
		//将字符串转换成jsonObject
		JSONObject jsonObject=JSONObject.parseObject(image);
		JSONObject data= jsonObject.getJSONObject("data");
		List<String> list= data.getJSONArray("list").stream().map(a-> ((JSONObject)a).getString("pid")).collect(Collectors.toList());
		String since_id= data.getString("since_id");


		log.info("since_id---------{}",since_id);
		if(list.size()==0){
			return null;
		}

		XlwebImagesVO xlwebImagesVO = new XlwebImagesVO();
		xlwebImagesVO.setPids(list);
		xlwebImagesVO.setSinceid(since_id);

		return xlwebImagesVO;
	}

	
	
	
	
	/**
	 * <p>
	 * 获取转换后的信息
	 * </p>
	 * 
	 * @author hzl 2021/12/16 1:07 PM
	 */

	public static void getImageInfos(String uid){
		Map<String,String> param = new HashMap<>();
		param.put("uid",uid);
		param.put("feature","0");

		for(int i=1;;i++){
			param.put("page", String.valueOf(i));

			String result=httpGet(param,imageUrl);
			boolean isEnd=parseImage(result);
			if(isEnd){
				break;
			}
		}

	}

	public  List<XlwebImagesVO> getImageInfosAll(String uid,Boolean isAll){
		Map<String,String> param = new HashMap<>();
		param.put("uid",uid);
		param.put("sinceid","0");
		param.put("has_album","true");
		List<XlwebImagesVO> list = new ArrayList<>();
		for(int i=1;;i++){
			String result=httpGet(param, imageUrlAll);
			XlwebImagesVO xlwebImagesVO=parseImageAll(result);



			if(xlwebImagesVO==null){
				break;
			}

			//如果存在了就不进行下载
			if(!isAll){
				QueryWrapper queryWrapper = new QueryWrapper();
				queryWrapper.eq("pid", xlwebImagesVO.getPids().get(0));
				XlwebImagesEntity xlwebImagesEntity=mapper.selectOne(queryWrapper);
				if(xlwebImagesEntity!=null){
					log.info("已经存在{}",xlwebImagesEntity.toString());
					break;
				}
			}

			xlwebImagesVO.setUid(uid);
			list.add(xlwebImagesVO);
			if("0".equals(xlwebImagesVO.getSinceid())){
				break;
			}
			param.put("sinceid",xlwebImagesVO.getSinceid());

		}

		return list;
	}



	public static void main(String args[]){
//		String result=httpGet("1590681790","0");
//		parseImage(result);
		//getImageInfos("1590681790");
//		List<XlwebImagesVO>  list=getImageInfosAll("5079248899");
//		list.forEach(a->{
//			System.out.println(a.convert());
//		});
		//log.info(list.toString());
		//getCookie();

	}
}
