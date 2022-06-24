package com.hzl.hadoop.security.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * description
 * 扩展springsecurity的用户信息
 * todo 待完善
 * @author hzl 2022/06/17 10:38 AM
 */
public class UserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		return null;
	}
}
