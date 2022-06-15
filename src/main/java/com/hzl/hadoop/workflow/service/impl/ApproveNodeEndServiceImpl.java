package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveNodeEndMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeEndService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



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

}