package com.hzl.hadoop.workflow.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;

/**
 * description
 *
 * @author hzl 2023/01/05 6:12 PM
 */
@Slf4j
public class ScriptUtil {


	/**
	 * <p>
	 *
	 *
	 * </p>
	 * @param expiress 字符串表达式
	 * @param values   表达式参数
	 * @author hzl 2023/01/05 6:18 PM
	 */
	public static boolean expire(String expiress, JSONObject values) throws ScriptException {

		ScriptEngineManager manager = new ScriptEngineManager();

		ScriptEngine engine = manager.getEngineByName("js");

		values.forEach((k,v)->{
			engine.put(k,v);
		});

		Boolean result = null;

		result = (Boolean) engine.eval(expiress);

		log.info("表达式执行结果{}",result);

		return result;
	}


}
