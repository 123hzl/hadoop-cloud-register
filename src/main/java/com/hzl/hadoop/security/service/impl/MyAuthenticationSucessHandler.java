package com.hzl.hadoop.security.service.impl;

import com.hzl.hadoop.security.service.SysUserService;
import com.hzl.hadoop.security.vo.LoginSuccessVO;
import com.hzl.hadoop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
	@Autowired
	SysUserService sysUserService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		LoginSuccessVO loginSuccessVO = LoginSuccessVO.builder().status("ok").currentAuthority(customUserDetails.getUsername()).build();
		//登陆成功后返回true
		PrintWriter printWriter = response.getWriter();
		log.info("登陆成功的用户{}", JsonUtils.objectToString(loginSuccessVO));
		sysUserService.updateStatus(customUserDetails.getUserId(), 1);
		printWriter.write(JsonUtils.objectToString(loginSuccessVO));
		printWriter.flush();
		printWriter.close();
		//设置用户登录成功

	}
}
