package com.hzl.hadoop.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.security.entity.SysUserRoleEntity;
import com.hzl.hadoop.security.vo.SysUserRoleVO;
import com.hzl.hadoop.security.dto.SysUserRoleDTO;

import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 用户角色关系表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

	PageInfo<SysUserRoleDTO> queryPage(SysUserRoleVO params, Integer current, Integer pageSize);
}

