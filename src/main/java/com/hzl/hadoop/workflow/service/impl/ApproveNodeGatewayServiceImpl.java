package com.hzl.hadoop.workflow.service.impl;

import com.hzl.hadoop.workflow.dto.NodeDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveNodeGatewayMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeGatewayEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeGatewayService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveNodeGatewayService")
public class ApproveNodeGatewayServiceImpl extends ServiceImpl<ApproveNodeGatewayMapper, ApproveNodeGatewayEntity> implements ApproveNodeGatewayService {

	@Autowired
    ApproveNodeGatewayMapper mapper;

    @Override
    public PageInfo queryPage(ApproveNodeGatewayEntity params,int start, int pageSize) {
		QueryWrapper<ApproveNodeGatewayEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveNodeGatewayEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }


	@Override
	public NodeDTO queryNodeById(Long nodeId) {
		return mapper.queryNodeById(nodeId);
	}


}