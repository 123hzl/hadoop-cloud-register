package com.hzl.hadoop.workflow.service.impl;

import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveNodeEndMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeEndService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;


@Service("approveNodeEndService")
public class ApproveNodeEndServiceImpl extends ServiceImpl<ApproveNodeEndMapper, ApproveNodeEndEntity> implements ApproveNodeEndService {

	@Autowired
    ApproveNodeEndMapper mapper;

    @Override
    public PageInfo queryPage(ApproveNodeEndEntity params,int start, int pageSize) {
		QueryWrapper<ApproveNodeEndEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveNodeEndEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

	@Override
	public List<ApproveNodeEndEntity> selectByFlowNum(String flowNum) {
		QueryWrapper<ApproveNodeEndEntity> queryWrapper = new QueryWrapper();
		queryWrapper.eq("flow_num",flowNum);
		List<ApproveNodeEndEntity>  workflowCharEntity=mapper.selectList(queryWrapper);
		if(workflowCharEntity==null){
			throw new CommonException("流程图不存在");
		}
		return workflowCharEntity;
	}


	@Override
	public NodeDTO queryNodeById(Long nodeId) {
		return mapper.queryNodeById(nodeId);
	}


}