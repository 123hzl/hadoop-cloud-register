package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveHistoryApproverMapper;
import com.hzl.hadoop.workflow.entity.ApproveHistoryApproverEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryApproverService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveHistoryApproverService")
public class ApproveHistoryApproverServiceImpl extends ServiceImpl<ApproveHistoryApproverMapper, ApproveHistoryApproverEntity> implements ApproveHistoryApproverService {

	@Autowired
    ApproveHistoryApproverMapper mapper;

    @Override
    public PageInfo queryPage(ApproveHistoryApproverEntity params,int start, int pageSize) {
		QueryWrapper<ApproveHistoryApproverEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveHistoryApproverEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}