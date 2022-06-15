package com.hzl.hadoop.security.service.impl;

import com.hzl.hadoop.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * description
 *
 * @author hzl 2021/09/09 8:32 PM
 */
@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	/**
	 * 注入我们自己定义的用户信息获取对象
	 */
	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
		log.info("用户名{}",userName);
		String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
		// 这里构建来判断用户是否存在和密码是否正确
		UserDetails userInfo = userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
		if (userInfo == null) {
			throw new BadCredentialsException("用户名不存在");
		}
		log.info("登陆密码{}", password);
		log.info("数据库密码:{}", userInfo.getPassword());
		boolean flag = passwordEncoder.matches(password, userInfo.getPassword());
		log.info("密码对比结果:{}", flag);

		if (!flag) {
			throw new BadCredentialsException("密码不正确");
		}
		Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
		// 构建返回的用户登录成功的token
		return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
		//return new UsernamePasswordAuthenticationToken(userInfo, null,authorities);
	}

	@Override
	public boolean supports(Class<?> Class) {
		// 这里直接改成retrun true;表示是支持这个执行
		return true;
	}
}
