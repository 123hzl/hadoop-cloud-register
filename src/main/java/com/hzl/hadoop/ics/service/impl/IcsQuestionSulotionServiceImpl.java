package com.hzl.hadoop.ics.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.ics.mapper.IcsQuestionSulotionMapper;
import com.hzl.hadoop.ics.entity.IcsQuestionSulotionEntity;
import com.hzl.hadoop.ics.vo.IcsQuestionSulotionVO;
import com.hzl.hadoop.ics.dto.IcsQuestionSulotionDTO;
import com.hzl.hadoop.ics.service.IcsQuestionSulotionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;



@Service("icsQuestionSulotionService")
public class IcsQuestionSulotionServiceImpl extends ServiceImpl<IcsQuestionSulotionMapper, IcsQuestionSulotionEntity> implements IcsQuestionSulotionService {

	@Autowired
    IcsQuestionSulotionMapper mapper;

    @Override
    public PageInfo<IcsQuestionSulotionDTO> queryPage(IcsQuestionSulotionVO params,Integer current, Integer pageSize) {
		PageInfo<IcsQuestionSulotionDTO> pageResult = PageHelper.startPage(current, pageSize).doSelectPageInfo(() -> mapper.listPage(params));

        return pageResult;
    }

}