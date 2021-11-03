package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ProcessVariableMapper;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.service.ProcessVariableService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("processVariableService")
public class ProcessVariableServiceImpl extends ServiceImpl<ProcessVariableMapper, ProcessVariableEntity> implements ProcessVariableService {

	@Autowired
    ProcessVariableMapper mapper;

    @Override
    public PageInfo queryPage(ProcessVariableEntity params,int start, int pageSize) {
		QueryWrapper<ProcessVariableEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<ProcessVariableEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}