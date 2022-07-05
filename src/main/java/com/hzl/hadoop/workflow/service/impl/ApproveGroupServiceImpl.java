package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveGroupMapper;
import com.hzl.hadoop.workflow.entity.ApproveGroupEntity;
import com.hzl.hadoop.workflow.service.ApproveGroupService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveGroupService")
public class ApproveGroupServiceImpl extends ServiceImpl<ApproveGroupMapper, ApproveGroupEntity> implements ApproveGroupService {

	@Autowired
    ApproveGroupMapper mapper;

    @Override
    public PageInfo queryPage(ApproveGroupEntity params,int start, int pageSize) {
		QueryWrapper<ApproveGroupEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveGroupEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

	@Override
	public Boolean isAllApprove(Long nodeId,Integer nodeType) {

		return null;
	}

}