package com.hzl.hadoop.security.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.hzl.hadoop.security.vo.LoginSuccessVO;
import com.hzl.hadoop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description
 * 自定义登陆成功后的操作
 *
 * @author hzl 2021/09/10 11:10 AM
 */
@Slf4j
@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
		LoginSuccessVO loginSuccessVO=LoginSuccessVO.builder().status("ok").currentAuthority(userName).build();
		//登陆成功后返回true
		PrintWriter printWriter = response.getWriter();
		log.info(JsonUtils.objectToString(loginSuccessVO));
		printWriter.write(JsonUtils.objectToString(loginSuccessVO));
		printWriter.flush();
		printWriter.close();
	}
}
