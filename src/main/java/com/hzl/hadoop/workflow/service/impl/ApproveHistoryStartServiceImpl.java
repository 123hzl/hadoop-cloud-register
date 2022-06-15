package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveHistoryStartMapper;
import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveHistoryStartService")
public class ApproveHistoryStartServiceImpl extends ServiceImpl<ApproveHistoryStartMapper, ApproveHistoryStartEntity> implements ApproveHistoryStartService {

	@Autowired
    ApproveHistoryStartMapper mapper;

    @Override
    public PageInfo queryPage(ApproveHistoryStartEntity params,int start, int pageSize) {
		QueryWrapper<ApproveHistoryStartEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveHistoryStartEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}