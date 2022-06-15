package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ProcessMountMapper;
import com.hzl.hadoop.workflow.entity.ProcessMountEntity;
import com.hzl.hadoop.workflow.service.ProcessMountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("processMountService")
public class ProcessMountServiceImpl extends ServiceImpl<ProcessMountMapper, ProcessMountEntity> implements ProcessMountService {

	@Autowired
    ProcessMountMapper mapper;

    @Override
    public PageInfo queryPage(ProcessMountEntity params,int start, int pageSize) {
		QueryWrapper<ProcessMountEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ProcessMountEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}