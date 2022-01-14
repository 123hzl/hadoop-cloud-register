package com.hzl.hadoop.gp.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.mapper.GpInfoMapper;
import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.service.GpInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("gpInfoService")
public class GpInfoServiceImpl extends ServiceImpl<GpInfoMapper, GpInfoEntity> implements GpInfoService {

	@Autowired
    GpInfoMapper mapper;

    @Override
    public PageInfo queryPage(GpInfoEntity params,int start, int pageSize) {
		QueryWrapper<GpInfoEntity> queryWrapper = new QueryWrapper(params);

		PageInfo<GpInfoEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

        return pageResult;
    }

}