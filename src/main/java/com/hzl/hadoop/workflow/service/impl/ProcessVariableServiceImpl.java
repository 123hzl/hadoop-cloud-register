package com.hzl.hadoop.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.mapper.ProcessVariableMapper;
import com.hzl.hadoop.workflow.service.ProcessVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("processVariableService")
public class ProcessVariableServiceImpl extends ServiceImpl<ProcessVariableMapper, ProcessVariableEntity> implements ProcessVariableService {

	@Autowired
	ProcessVariableMapper mapper;

	@Override
	public PageInfo queryPage(ProcessVariableEntity params, int start, int pageSize) {
		QueryWrapper<ProcessVariableEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ProcessVariableEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}

	@Override
	public List<ProcessVariableEntity> queryByProcessId(Long processId) {
		QueryWrapper<ProcessVariableEntity> queryWrapper = new QueryWrapper();
		queryWrapper.eq("process_id", processId);
		return mapper.selectList(queryWrapper);
	}

}