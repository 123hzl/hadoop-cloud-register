package com.hzl.hadoop.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.security.entity.SysRoleEntity;
import com.hzl.hadoop.security.vo.SysRoleVO;
import com.hzl.hadoop.security.dto.SysRoleDTO;

import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 用户角色表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageInfo<SysRoleDTO> queryPage(SysRoleVO params, Integer current, Integer pageSize);
}

