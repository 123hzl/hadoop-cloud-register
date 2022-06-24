package com.hzl.hadoop.security.service.impl;

import com.hzl.hadoop.interfacemanager.entity.InterfaceManageEntity;
import com.hzl.hadoop.interfacemanager.service.InterfaceManageService;
import com.hzl.hadoop.interfacemanager.vo.InterfaceManageVO;
import com.hzl.hadoop.security.service.RoleOauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * description
 * 接口拦截器，不管用户是否登录都会拦截接口
 * @author hzl 2021/09/10 11:53 AM
 */
@Slf4j
@Component("roleOauthService")
public class RoleOauthServiceImpl implements RoleOauthService {

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Autowired
	private InterfaceManageService interfaceManageService;

	@Override
	public Boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();

		boolean hasPermission = false;

		InterfaceManageVO interfaceManageVO = new InterfaceManageVO();
		interfaceManageVO.setMethod(request.getMethod().toLowerCase());

		if (principal instanceof UserDetails) {
			String userName = ((UserDetails) principal).getUsername();
			log.info("用户信息111{}",userName);
			// 数据库读取 //读取用户所拥有权限的所有URL
			List<InterfaceManageEntity> urls = interfaceManageService.selectUrls(interfaceManageVO);

			log.info("请求方式{}",request.getMethod());
			// 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
			for (InterfaceManageEntity interfaceManage : urls) {
				if (antPathMatcher.match(interfaceManage.getUrl(), request.getRequestURI())
						&&interfaceManage.getMethod().equalsIgnoreCase(request.getMethod())) {
					hasPermission = true;
					break;
				}
			}
		}else{
			// 数据库读取 //读取用户所拥有权限的所有URL
			interfaceManageVO.setIsLogin(false);
			List<InterfaceManageEntity> urls = interfaceManageService.selectUrls(interfaceManageVO);

			// 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
			for (InterfaceManageEntity interfaceManage : urls) {
				if (antPathMatcher.match(interfaceManage.getUrl(), request.getRequestURI())) {
					hasPermission = true;
					break;
				}
			}
		}

		log.info("是否有权限{}", hasPermission);
		return hasPermission;
		//以上代码暂时注释，等后期url表设计，和自动把url注册进表再放开
	}
}
