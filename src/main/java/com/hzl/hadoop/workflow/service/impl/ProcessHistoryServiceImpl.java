package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ProcessHistoryMapper;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("processHistoryService")
public class ProcessHistoryServiceImpl extends ServiceImpl<ProcessHistoryMapper, ProcessHistoryEntity> implements ProcessHistoryService {

	@Autowired
    ProcessHistoryMapper mapper;

    @Override
    public PageInfo queryPage(ProcessHistoryEntity params,int start, int pageSize) {
		QueryWrapper<ProcessHistoryEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ProcessHistoryEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}