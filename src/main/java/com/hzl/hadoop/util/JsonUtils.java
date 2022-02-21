package com.hzl.hadoop.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * description
 * json处理工具类
 * 改成泛型方法,增加可读性和安全性，避免强制转换
 * @author hzl 2020/08/06 12:53 PM
 */
public class JsonUtils {

	/**
	 * <p>
	 * java对象转json字符串
	 * </p>
	 *
	 * @author hzl 2020/08/06 12:56 PM
	 */

	public static String objectToString(Object object){
		return JSON.toJSONString(object);
	}

	/**
	 * <p>
	 * json字符串转java对象
	 * </p>
	 *
	 * @author hzl 2020/08/06 12:58 PM
	 */

	public static <T> T jsonStringToObeject(String json,Class<T> className){
		JSONObject jsonObejct = JSONObject.parseObject(json);
		return JSON.toJavaObject(jsonObejct, className);
	}

	/**
	 * <p>
	 * json字符串转java集合对象
	 * </p>
	 *
	 * @author hzl 2020/08/06 12:58 PM
	 */

	public static <T> List<T>  jsonStringToList(String json,Class<T> className){

		return JSONObject.parseArray(json, className);
	}

	/**
	 *
	 *
	 * 实现对象克隆有两种方式：
	 *
	 * 1). 实现Cloneable接口并重写Object类中的clone()方法；（分为深拷贝和浅拷贝）
	 *
	 * 2). 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆。
	 *
	 * 参考：https://www.cnblogs.com/Qian123/p/5710533.html
	 *
	 * json序列化方式克隆对象
	 * @param object 被克隆对象
	 * @author hzl 2020-08-19 2:20 PM
	 * @return
	 */
	public static <T> T cloneObject(Object object,Class<T> className){
		String cloneObject=objectToString(object);
		return jsonStringToObeject(cloneObject,className);
	}
	/**
	 *
	 * 集合对象克隆
	 * @param object 集合
	 * @param className 转化的类型
	 * @author hzl 2020-11-05 12:34 PM
	 * @return
	 */
	public static <T> List<T> cloneObjectList(Object object, Class<T> className){
		String cloneObject =objectToString(object);
		return JSONObject.parseArray(cloneObject,className);
	}

	/**
	 * map转化为json
	 *
	 * @param map 需要转化的map对象
	 * @author hzl 2020-10-31 2:35 PM
	 * @return
	 */

	public static JSONObject mapToJson(Map map){
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
		return jsonObject;
	}


}
