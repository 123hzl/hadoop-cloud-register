package com.hzl.hadoop.workflow.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ApproveConditionsMapper;
import com.hzl.hadoop.workflow.entity.ApproveConditionsEntity;
import com.hzl.hadoop.workflow.vo.ApproveConditionsVO;
import com.hzl.hadoop.workflow.dto.ApproveConditionsDTO;
import com.hzl.hadoop.workflow.service.ApproveConditionsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("approveConditionsService")
public class ApproveConditionsServiceImpl extends ServiceImpl<ApproveConditionsMapper, ApproveConditionsEntity> implements ApproveConditionsService {

	@Autowired
    ApproveConditionsMapper mapper;

    @Override
    public PageInfo<ApproveConditionsDTO> queryPage(ApproveConditionsVO params,Integer current, Integer pageSize) {
		PageInfo<ApproveConditionsDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

        return pageResult;
    }

}