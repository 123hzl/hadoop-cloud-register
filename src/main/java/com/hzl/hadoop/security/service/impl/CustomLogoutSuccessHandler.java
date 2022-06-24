package com.hzl.hadoop.security.service.impl;

import com.hzl.hadoop.security.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description
 *
 * @author hzl 2022/06/08 10:43 AM
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	SysUserService sysUserService;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
		CustomUserDetails customUserDetails= (CustomUserDetails) authentication.getPrincipal();

		//设置用户状态离线
		sysUserService.updateStatus(customUserDetails.getUserId(),0);
		//登出日志
		log.info("退出成功");
	}
}
