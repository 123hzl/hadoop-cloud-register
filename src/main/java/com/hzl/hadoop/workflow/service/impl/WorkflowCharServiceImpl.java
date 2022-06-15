package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.WorkflowCharMapper;
import com.hzl.hadoop.workflow.entity.WorkflowCharEntity;
import com.hzl.hadoop.workflow.service.WorkflowCharService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("workflowCharService")
public class WorkflowCharServiceImpl extends ServiceImpl<WorkflowCharMapper, WorkflowCharEntity> implements WorkflowCharService {

	@Autowired
    WorkflowCharMapper mapper;

    @Override
    public PageInfo queryPage(WorkflowCharEntity params,int start, int pageSize) {
		QueryWrapper<WorkflowCharEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<WorkflowCharEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}