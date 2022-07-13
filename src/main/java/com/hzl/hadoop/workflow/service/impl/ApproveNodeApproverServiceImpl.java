package com.hzl.hadoop.workflow.service.impl;

import com.hzl.hadoop.workflow.dto.NodeDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveNodeApproverMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeApproverEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeApproverService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveNodeApproverService")
public class ApproveNodeApproverServiceImpl extends ServiceImpl<ApproveNodeApproverMapper, ApproveNodeApproverEntity> implements ApproveNodeApproverService {

	@Autowired
    ApproveNodeApproverMapper mapper;

    @Override
    public PageInfo queryPage(ApproveNodeApproverEntity params,int start, int pageSize) {
		QueryWrapper<ApproveNodeApproverEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveNodeApproverEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }


	@Override
	public NodeDTO queryNodeById(Long nodeId) {
		return mapper.queryNodeById(nodeId);
	}


}