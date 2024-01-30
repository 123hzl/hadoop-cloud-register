package com.hzl.hadoop.userlog;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hzl.hadoop.userlog.entity.RequestLogsEntity;
import com.hzl.hadoop.userlog.service.RequestLogsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * description
 * 用户操作日志
 *
 * @author hzl 2020/01/07 9:59 PM
 */
@Slf4j
@Aspect
@Component
public class UserLogAspect {

	@Autowired
	private RequestLogsService requestLogsService;

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMappingPointCut() {

	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	public void deleteMappingPointCut() {

	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	public void putMappingPointCut() {

	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMappingPointCut() {

	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMappingPointCut() {

	}


	@Around(value = "getMappingPointCut()")
	public Object aroundGet(ProceedingJoinPoint point) throws Throwable {
		return around(point);
	}

	@Around(value = "deleteMappingPointCut()")
	public Object aroundDelete(ProceedingJoinPoint point) throws Throwable {
		return around(point);
	}


	@Around(value = "putMappingPointCut()")
	public Object aroundPut(ProceedingJoinPoint point) throws Throwable {
		return around(point);
	}


	@Around(value = "postMappingPointCut()")
	public Object aroundPost(ProceedingJoinPoint point) throws Throwable {
		return around(point);
	}


	@Around(value = "requestMappingPointCut()")
	public Object aroundRequest(ProceedingJoinPoint point) throws Throwable {
		return around(point);
	}

	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object returnResult;
		//获取参数,不能获取对象继承的父类
		Object[] args = point.getArgs();

		//获取请求上下文
		HttpServletRequest request = getRequest();

		String requestParam = getArgs(args, request);

		log.info("方法参数:{}", requestParam);
		//获取方法返回值
		// 计算耗时
		long startTime = System.nanoTime();

		long consumeTime;
		try {
			returnResult = point.proceed(args);
		} finally {
			consumeTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
		}

		log.info("打印返回结果:{}", String.valueOf(returnResult));

		//插入记录到数据库 TODO 后期改成异步操作，或者消息中间件
		insertTable(request, requestParam, consumeTime);
		//用户日志记录结束
		return returnResult;
	}


	/**
	 * 获取请求参数
	 *
	 * @param args
	 * @param request
	 * @return
	 */
	private String getArgs(Object[] args, HttpServletRequest request) {
		String strArgs = StringPool.EMPTY;
		try {
			if (!request.getContentType().contains("multipart/form-data")) {
				strArgs = JSONObject.toJSONString(args);

			}
		} catch (Exception e) {
			try {
				strArgs = Arrays.toString(args);
			} catch (Exception ex) {
				log.warn("解析参数异常", ex);
			}
		}
		return strArgs;
	}


	/**
	 * <p>
	 * 获取上下午request
	 * </p>
	 *
	 * @author hzl 2021/11/22 9:49 AM
	 */
	public HttpServletRequest getRequest() {
		//获取请求上下文
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
		return servletRequestAttributes.getRequest();
	}


	/**
	 * <p>
	 * 将请求记录保存数据库
	 * </p>
	 *
	 * @author hzl 2021/11/22 9:51 AM
	 */
	public boolean insertTable(HttpServletRequest request, String requestParam, long consumeTime) {
		//参数封装，插入数据库
		String option = request.getMethod();
		String url = request.getRequestURI();
		//获取访问人的ip
		String ip = IPUtil.getIpAddress(request);
		//String city = IPUtil.getCityInfo(ip);
		String city="";

		//用户日志记录,后期换成异步不影响请求性能
		RequestLogsEntity requestLogsEntity = RequestLogsEntity.builder()
				.ip(ip)
				.requestParam(JSON.toJSONString(requestParam))
				.url(url)
				.method(option)
				.city(city)
				.consumeTime(consumeTime)
				.build();
		return requestLogsService.save(requestLogsEntity);
	}



}
