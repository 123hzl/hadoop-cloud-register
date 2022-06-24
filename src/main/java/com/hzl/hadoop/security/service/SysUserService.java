package com.hzl.hadoop.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.security.entity.SysUser;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.security.vo.SysUserVO;

/**
 * 用户信息表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-17 09:10:15
 */
public interface SysUserService extends IService<SysUser> {

	PageInfo queryPage(SysUserVO params, int start, int pageSize);

	int updateStatus(Long userId,Integer status);
}

