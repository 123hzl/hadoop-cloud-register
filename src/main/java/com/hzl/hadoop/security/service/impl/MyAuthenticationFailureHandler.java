package com.hzl.hadoop.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.hzl.hadoop.config.mvc.BaseResponse;
import com.hzl.hadoop.security.vo.LoginSuccessVO;
import com.hzl.hadoop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description
 * 自定义登陆失败
 *
 * @author hzl 2021/09/10 11:13 AM
 */
@Slf4j
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException {
		log.info("登陆失败出发");
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=utf-8");
		LoginSuccessVO loginSuccessVO=LoginSuccessVO.builder()
				.status("error")
				.currentAuthority("guest")
				.build();

		BaseResponse baseResponse=new BaseResponse(loginSuccessVO,false,exception.getMessage());
		//不通过前端通用异常显示，登陆自带的异常捕获处理
		baseResponse.setShowType(0);

		log.info("测试错误信息{}", JsonUtils.objectToString(baseResponse));

		PrintWriter printWriter=response.getWriter();
		printWriter.write(JSON.toJSONString(baseResponse));
		printWriter.flush();
		printWriter.close();
	}
}
