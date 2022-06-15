package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveNodeStartMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeStartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveNodeStartService")
public class ApproveNodeStartServiceImpl extends ServiceImpl<ApproveNodeStartMapper, ApproveNodeStartEntity> implements ApproveNodeStartService {

	@Autowired
    ApproveNodeStartMapper mapper;

    @Override
    public PageInfo queryPage(ApproveNodeStartEntity params,int start, int pageSize) {
		QueryWrapper<ApproveNodeStartEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveNodeStartEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}