package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.WorkflowNodeTypeMapper;
import com.hzl.hadoop.workflow.entity.WorkflowNodeTypeEntity;
import com.hzl.hadoop.workflow.service.WorkflowNodeTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("workflowNodeTypeService")
public class WorkflowNodeTypeServiceImpl extends ServiceImpl<WorkflowNodeTypeMapper, WorkflowNodeTypeEntity> implements WorkflowNodeTypeService {

	@Autowired
    WorkflowNodeTypeMapper mapper;

    @Override
    public PageInfo queryPage(WorkflowNodeTypeEntity params,int start, int pageSize) {
		QueryWrapper<WorkflowNodeTypeEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<WorkflowNodeTypeEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}