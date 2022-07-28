package com.hzl.hadoop.ics.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.ics.mapper.IcsQuestionSearchLogMapper;
import com.hzl.hadoop.ics.entity.IcsQuestionSearchLogEntity;
import com.hzl.hadoop.ics.vo.IcsQuestionSearchLogVO;
import com.hzl.hadoop.ics.dto.IcsQuestionSearchLogDTO;
import com.hzl.hadoop.ics.service.IcsQuestionSearchLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("icsQuestionSearchLogService")
public class IcsQuestionSearchLogServiceImpl extends ServiceImpl<IcsQuestionSearchLogMapper, IcsQuestionSearchLogEntity> implements IcsQuestionSearchLogService {

	@Autowired
    IcsQuestionSearchLogMapper mapper;

    @Override
    public PageInfo<IcsQuestionSearchLogDTO> queryPage(IcsQuestionSearchLogVO params,Integer current, Integer pageSize) {
		PageInfo<IcsQuestionSearchLogDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

        return pageResult;
    }

}