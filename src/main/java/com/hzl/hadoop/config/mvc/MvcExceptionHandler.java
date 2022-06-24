package com.hzl.hadoop.config.mvc;

import com.hzl.hadoop.constant.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * description
 *	mvc全局异常处理
 * @author hzl 2020/01/02 8:43 PM
 */
@ControllerAdvice
@Slf4j
public class MvcExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value =Exception.class )
	public BaseResponse exceptionHander(Exception e, HttpServletRequest req){
		e.printStackTrace();
		log.error("mvc统一异常处理"+e);
		BaseResponse baseResponse=new BaseResponse(null,false,e.getMessage());
		return baseResponse;
	}
}
