package com.hzl.hadoop.security.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.security.mapper.SysUserRoleMapper;
import com.hzl.hadoop.security.entity.SysUserRoleEntity;
import com.hzl.hadoop.security.vo.SysUserRoleVO;
import com.hzl.hadoop.security.dto.SysUserRoleDTO;
import com.hzl.hadoop.security.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

	@Autowired
    SysUserRoleMapper mapper;

    @Override
    public PageInfo<SysUserRoleDTO> queryPage(SysUserRoleVO params,Integer current, Integer pageSize) {
		PageInfo<SysUserRoleDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

        return pageResult;
    }

}