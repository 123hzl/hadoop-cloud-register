package com.hzl.hadoop.security.service.impl;

import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.security.dataobject.SysUser;
import com.hzl.hadoop.security.mapper.SysUserMapper;
import com.hzl.hadoop.security.service.MyUserDetailsService;
import com.hzl.hadoop.security.vo.SysUserVO;
import com.hzl.hadoop.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author hzl 2021/09/09 5:10 PM
 */
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

	SysUserMapper sysUserMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public MyUserDetailsServiceImpl(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Override
	public SysUser selectUser(SysUser sysUser) {
		return sysUserMapper.selectOne(sysUser);
	}

	@Override
	public SysUser selectUserByUserName(String username) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		return sysUserMapper.selectOne(sysUser);
	}

	@Override
	public Boolean register(SysUserVO sysUserVO) {
		sysUserVO.init();
		//用户名，密码为空校验，不能重复注册
		validateUser(sysUserVO);
		String password = sysUserVO.getPassword();
		sysUserVO.setPassword(passwordEncoder.encode(password));
		int i = sysUserMapper.insert((SysUser) JsonUtils.cloneObject(sysUserVO, SysUser.class));
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}


	public void validateUser(SysUserVO sysUserV){
		if(StringUtils.isBlank(sysUserV.getPassword())){
			throw new CommonException("密码不能为空");
		}
		if(StringUtils.isBlank(sysUserV.getUsername())){
			throw new CommonException("用户名不能为空");
		}

		if(StringUtils.isBlank(sysUserV.getPhone())){
			throw new CommonException("手机号码不能为空");
		}

		SysUser sysUser=this.selectUserByUserName(sysUserV.getUsername());
		if(sysUser!=null){
			throw new CommonException("该用户名已存在");
		}


	}
}
