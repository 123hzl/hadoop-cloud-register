package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproverMapper;
import com.hzl.hadoop.workflow.entity.ApproverEntity;
import com.hzl.hadoop.workflow.service.ApproverService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approverService")
public class ApproverServiceImpl extends ServiceImpl<ApproverMapper, ApproverEntity> implements ApproverService {

	@Autowired
    ApproverMapper mapper;

    @Override
    public PageInfo queryPage(ApproverEntity params,int start, int pageSize) {
		QueryWrapper<ApproverEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproverEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}