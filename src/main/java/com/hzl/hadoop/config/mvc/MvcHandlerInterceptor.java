package com.hzl.hadoop.config.mvc;

import com.hzl.hadoop.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * description
 * mvc拦截器，需要在WebMvcConfig中配置拦截器
 * 实现springmvc拦截的方式有两种一种implements HandlerInterceptor另一种extends HandlerInterceptorAdapter
 * HandlerInterceptorAdapter实现了HandlerInterceptor的子类AsyncHandlerInterceptor。如果实现HandlerInterceptor需要实现里面的所有方法
 * 但是继承HandlerInterceptorAdapter并不需要实现所有拦截器的方法
 * 参考：https://www.jianshu.com/p/59fc39ad946e
 *
 * @author hzl 2020/06/11 10:02 AM
 */
@Slf4j
public class MvcHandlerInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("{}:在请求处理之前进行调用（Controller方法调用之前）", this.getClass().getSimpleName());


		//从header中获取token
		String token = request.getHeader("access_token");
		//如果header中不存在token，则从参数中获取token
		if (StringUtils.isBlank(token)) {
			token = request.getParameter("access_token");
		}
		//token为空,非oauth2情况下不需要
//		if (StringUtils.isBlank(token)) {
//			throw new CommonException("token不能为空");
//		}

		return true;//只有返回true才会继续向下执行，返回false取消当前请求
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		log.info("{}:请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）", this.getClass().getSimpleName());

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		log.info("{}:在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）", this.getClass().getSimpleName());
	}

}
