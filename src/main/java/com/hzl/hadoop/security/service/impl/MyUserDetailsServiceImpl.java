package com.hzl.hadoop.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.security.dataobject.SysUser;
import com.hzl.hadoop.security.mapper.SysUserMapper;
import com.hzl.hadoop.security.service.MyUserDetailsService;
import com.hzl.hadoop.security.vo.RecoveredPasswordVO;
import com.hzl.hadoop.security.vo.SysUserVO;
import com.hzl.hadoop.security.vo.UserInfoVO;
import com.hzl.hadoop.util.GenerateCodeUtils;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Override
	public Boolean recoveredPassword(RecoveredPasswordVO recoveredPasswordVO) {

		//校验验证码是否正确
		String code= (String) RedisUtils.get(recoveredPasswordVO.getPhone());

		if(StringUtils.isNotBlank(code)&&code.equals(recoveredPasswordVO.getIndentifyCode())){
			//验证码没有失效，且校验通过，更新用户密码

			//查询用户信息
			SysUser sysUser = new SysUser();
			sysUser.setPhone(recoveredPasswordVO.getPhone());
			Wrapper<SysUser> queryWrapper = new QueryWrapper<>(sysUser);
			sysUser=sysUserMapper.selectOne(queryWrapper);

			//判断用户信息是否存在
			if(sysUser!=null){

				UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
				updateWrapper.eq("id",sysUser.getId());

				SysUser sysUserUpdate = new SysUser();
				sysUserUpdate.setPassword(passwordEncoder.encode(recoveredPasswordVO.getPassword()));

				sysUserMapper.update(sysUserUpdate,updateWrapper);

			}else{
				throw new CommonException("用户信息不存在!");
			}

		}else{
			throw new CommonException("验证码失效");
		}

		return true;
	}

	@Override
	public Boolean authCodePassword(String phone) {

		//查询用户信息
		SysUser sysUser = new SysUser();
		sysUser.setPhone(phone);
		Wrapper<SysUser> queryWrapper = new QueryWrapper<>(sysUser);
		sysUser=sysUserMapper.selectOne(queryWrapper);

		if(sysUser==null){
			throw new CommonException("用户信息不存在!");
		}

		//验证码存在直接返回
		//生成验证码,并发情况下最多出现验证码被覆盖的情况
		String code= (String) RedisUtils.get(phone);
		if(StringUtils.isBlank(code)){
			code= GenerateCodeUtils.generateNumCode(4);
			//存入redis 默认五分钟失效
			RedisUtils.set(phone,code,300);
		}
		//发送邮件,或者短信 todo

		return true;
	}

	@Override
	public UserInfoVO getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String userName=authentication.getName();

		SysUser sysUser=selectUserByUserName(userName);

		UserInfoVO userInfoVO=JsonUtils.cloneObject(sysUser,UserInfoVO.class);
		userInfoVO.setUserid(String.valueOf(sysUser.getId()));
		return userInfoVO;
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
