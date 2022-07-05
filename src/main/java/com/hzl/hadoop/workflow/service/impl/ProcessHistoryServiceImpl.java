package com.hzl.hadoop.workflow.service.impl;

import com.hzl.hadoop.workflow.dto.ProcessHistoryPageDTO;
import com.hzl.hadoop.workflow.vo.ProcessHistoryPageVO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.mapper.ProcessHistoryMapper;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("processHistoryService")
public class ProcessHistoryServiceImpl extends ServiceImpl<ProcessHistoryMapper, ProcessHistoryEntity> implements ProcessHistoryService {

	@Autowired
    ProcessHistoryMapper mapper;

    @Override
    public PageInfo queryPage(ProcessHistoryPageVO params, int start, int pageSize) {

		PageInfo<ProcessHistoryPageDTO> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.queryPage(params));

        return pageResult;
    }

}