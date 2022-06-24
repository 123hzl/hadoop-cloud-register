package com.hzl.hadoop.userlog.service.impl;

import com.hzl.hadoop.userlog.dto.RequestLogsDTO;
import com.hzl.hadoop.userlog.vo.RequestLogsVO;
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
	public PageInfo<RequestLogsDTO> queryPage(RequestLogsVO params, Integer current, Integer pageSize) {
		PageInfo<RequestLogsDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

		return pageResult;
	}

}