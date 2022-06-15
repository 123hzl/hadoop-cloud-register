package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveHistoryGatewayMapper;
import com.hzl.hadoop.workflow.entity.ApproveHistoryGatewayEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryGatewayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveHistoryGatewayService")
public class ApproveHistoryGatewayServiceImpl extends ServiceImpl<ApproveHistoryGatewayMapper, ApproveHistoryGatewayEntity> implements ApproveHistoryGatewayService {

	@Autowired
    ApproveHistoryGatewayMapper mapper;

    @Override
    public PageInfo queryPage(ApproveHistoryGatewayEntity params,int start, int pageSize) {
		QueryWrapper<ApproveHistoryGatewayEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveHistoryGatewayEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}