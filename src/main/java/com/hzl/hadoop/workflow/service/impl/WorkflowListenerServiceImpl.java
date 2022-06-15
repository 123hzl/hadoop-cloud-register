package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.WorkflowListenerMapper;
import com.hzl.hadoop.workflow.entity.WorkflowListenerEntity;
import com.hzl.hadoop.workflow.service.WorkflowListenerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("workflowListenerService")
public class WorkflowListenerServiceImpl extends ServiceImpl<WorkflowListenerMapper, WorkflowListenerEntity> implements WorkflowListenerService {

	@Autowired
    WorkflowListenerMapper mapper;

    @Override
    public PageInfo queryPage(WorkflowListenerEntity params,int start, int pageSize) {
		QueryWrapper<WorkflowListenerEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<WorkflowListenerEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}