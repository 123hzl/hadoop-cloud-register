package com.hzl.hadoop.security.service.impl;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * description
 *  自定义用户信息，扩展springsecurity的user
 * @author hzl 2022/06/17 10:51 AM
 */
@Data
public class CustomUserDetails extends User {

	/**
	 * 用户id
	 * */
	private Long userId;

	/**
	* 租户id
	* */
	private Long tenantId;

	/**
	 * 角色id
	 * */
	private Long roleId;

	/**
	 * 头像url
	 */
	private String avatar;
	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 用户名-真实姓名，可以为空
	 */
	private String realName;


	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}




}
