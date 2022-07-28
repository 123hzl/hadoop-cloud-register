package com.hzl.hadoop.ics.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.ics.mapper.IcsKeyWordMapper;
import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;
import com.hzl.hadoop.ics.vo.IcsKeyWordVO;
import com.hzl.hadoop.ics.dto.IcsKeyWordDTO;
import com.hzl.hadoop.ics.service.IcsKeyWordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("icsKeyWordService")
public class IcsKeyWordServiceImpl extends ServiceImpl<IcsKeyWordMapper, IcsKeyWordEntity> implements IcsKeyWordService {

	@Autowired
    IcsKeyWordMapper mapper;

    @Override
    public PageInfo<IcsKeyWordDTO> queryPage(IcsKeyWordVO params,Integer current, Integer pageSize) {
		PageInfo<IcsKeyWordDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

        return pageResult;
    }

}