package com.hzl.hadoop.security.utils;

import com.hzl.hadoop.security.service.impl.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * description
 * 获取当前登录的用户信息
 *
 * @author hzl 2022/06/17 12:46 PM
 */
public class DetailHepler {

	public static CustomUserDetails getUserDetails() {
		if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof CustomUserDetails) {
				return (CustomUserDetails) principal;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
