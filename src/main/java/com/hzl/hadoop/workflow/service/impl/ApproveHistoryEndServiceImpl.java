package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveHistoryEndMapper;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEndEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryEndService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveHistoryEndService")
public class ApproveHistoryEndServiceImpl extends ServiceImpl<ApproveHistoryEndMapper, ApproveHistoryEndEntity> implements ApproveHistoryEndService {

	@Autowired
    ApproveHistoryEndMapper mapper;

    @Override
    public PageInfo queryPage(ApproveHistoryEndEntity params,int start, int pageSize) {
		QueryWrapper<ApproveHistoryEndEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveHistoryEndEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}