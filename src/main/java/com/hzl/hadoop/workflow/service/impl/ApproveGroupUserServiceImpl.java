package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveGroupUserMapper;
import com.hzl.hadoop.workflow.entity.ApproveGroupUserEntity;
import com.hzl.hadoop.workflow.service.ApproveGroupUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveGroupUserService")
public class ApproveGroupUserServiceImpl extends ServiceImpl<ApproveGroupUserMapper, ApproveGroupUserEntity> implements ApproveGroupUserService {

	@Autowired
    ApproveGroupUserMapper mapper;

    @Override
    public PageInfo queryPage(ApproveGroupUserEntity params,int start, int pageSize) {
		QueryWrapper<ApproveGroupUserEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<ApproveGroupUserEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}