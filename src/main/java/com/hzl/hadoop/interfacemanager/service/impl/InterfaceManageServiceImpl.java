package com.hzl.hadoop.interfacemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.interfacemanager.dto.InterfaceManageDTO;
import com.hzl.hadoop.interfacemanager.entity.InterfaceManageEntity;
import com.hzl.hadoop.interfacemanager.mapper.InterfaceManageMapper;
import com.hzl.hadoop.interfacemanager.service.InterfaceManageService;
import com.hzl.hadoop.interfacemanager.vo.InterfaceManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("interfaceManageService" )
public class InterfaceManageServiceImpl extends ServiceImpl<InterfaceManageMapper, InterfaceManageEntity> implements InterfaceManageService {

	@Autowired
	InterfaceManageMapper mapper;

	/*
	 * todo 分页返回的雪花id是Long，前端自动截取了需要替换成vo，设置id为string，修改代码生成器自动生成select语句
	 * */
	@Override
	public PageInfo queryPage(InterfaceManageVO params, int start, int pageSize) {

		PageInfo<InterfaceManageDTO> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

		return pageResult;
	}

	@Override
	public List<InterfaceManageEntity> selectUrls(InterfaceManageVO interfaceManageVO) {

		return mapper.selectUrls(interfaceManageVO);
	}

}