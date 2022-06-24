package com.hzl.hadoop.security.service;

import com.hzl.hadoop.security.entity.SysUser;
import com.hzl.hadoop.security.vo.RecoveredPasswordVO;
import com.hzl.hadoop.security.vo.SysUserVO;
import com.hzl.hadoop.security.vo.UserInfoVO;

/**
 * description
 *
 * @author hzl 2021/09/09 5:06 PM
 */
public interface MyUserDetailsService {

	SysUser selectUser(SysUser sysUser);

	SysUser selectUserByUserName(String username);

	Boolean register(SysUserVO sysUserVO);

	Boolean recoveredPassword(RecoveredPasswordVO recoveredPasswordVO);

	Boolean authCodePassword(String phone);

	UserInfoVO getCurrentUserInfo();
}
