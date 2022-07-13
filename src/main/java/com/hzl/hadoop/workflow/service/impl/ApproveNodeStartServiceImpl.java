package com.hzl.hadoop.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.mapper.ApproveNodeStartMapper;
import com.hzl.hadoop.workflow.service.ApproveNodeStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("approveNodeStartService")
public class ApproveNodeStartServiceImpl extends ServiceImpl<ApproveNodeStartMapper, ApproveNodeStartEntity> implements ApproveNodeStartService {

	@Autowired
	ApproveNodeStartMapper mapper;

	@Override
	public PageInfo queryPage(ApproveNodeStartEntity params, int start, int pageSize) {
		QueryWrapper<ApproveNodeStartEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveNodeStartEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}

	@Override
	public ApproveNodeStartEntity getStartWorkNode(String flowNum) {
		QueryWrapper<ApproveNodeStartEntity> queryWrapper = new QueryWrapper();
		queryWrapper.eq("flow_num", flowNum);
		ApproveNodeStartEntity approveNodeStartEntity = mapper.selectOne(queryWrapper);
		if (approveNodeStartEntity == null) {
			throw new CommonException("流程图不存在");
		}
		return approveNodeStartEntity;
	}

	@Override
	public List<NodeDTO> queryNode(Integer nodeType, Long nodeId) {
		return mapper.queryNode(nodeType,nodeId);
	}

	@Override
	public NodeDTO queryNodeById(Long nodeId) {
		return mapper.queryNodeById(nodeId);
	}

}