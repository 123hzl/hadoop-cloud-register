package com.hzl.hadoop.security.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.security.mapper.SysRoleMapper;
import com.hzl.hadoop.security.entity.SysRoleEntity;
import com.hzl.hadoop.security.vo.SysRoleVO;
import com.hzl.hadoop.security.dto.SysRoleDTO;
import com.hzl.hadoop.security.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

	@Autowired
    SysRoleMapper mapper;

    @Override
    public PageInfo<SysRoleDTO> queryPage(SysRoleVO params,Integer current, Integer pageSize) {
		PageInfo<SysRoleDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

        return pageResult;
    }

}