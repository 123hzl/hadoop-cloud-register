package com.hzl.hadoop.userlog.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.userlog.mapper.RequestLogsMapper;
import com.hzl.hadoop.userlog.entity.RequestLogsEntity;
import com.hzl.hadoop.userlog.service.RequestLogsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("requestLogsService")
public class RequestLogsServiceImpl extends ServiceImpl<RequestLogsMapper, RequestLogsEntity> implements RequestLogsService {

	@Autowired
    RequestLogsMapper mapper;

    @Override
    public PageInfo queryPage(RequestLogsEntity params,int start, int pageSize) {
		QueryWrapper<RequestLogsEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<RequestLogsEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}