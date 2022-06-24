package com.hzl.hadoop.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.security.entity.SysUser;
import com.hzl.hadoop.security.dto.SysUserDTO;
import com.hzl.hadoop.security.mapper.SysUserMapper;
import com.hzl.hadoop.security.service.SysUserService;
import com.hzl.hadoop.security.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	SysUserMapper mapper;

	@Override
	public PageInfo queryPage(SysUserVO params, int start, int pageSize) {

		PageInfo<SysUserDTO> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

		return pageResult;
	}

	/**
	 * 更新用户状态，true为在线
	 *
	 * @param userId 用户id
	 * @return
	 * @author hzl 2022-06-17 5:06 PM
	 */
	@Override
	public int updateStatus(Long userId, Integer status) {
		SysUser sysUser = new SysUser();
		sysUser.setStatus(status);
		sysUser.setVersionNum(0);
		UpdateWrapper updateWrapper =new UpdateWrapper();
		updateWrapper.eq("id",userId);

		return mapper.update(sysUser,updateWrapper);
	}

}