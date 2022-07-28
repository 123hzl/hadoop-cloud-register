package com.hzl.hadoop.ics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.ics.vo.IcsQuestionSulotionVO;
import com.hzl.hadoop.ics.dto.IcsQuestionSulotionDTO;
import com.hzl.hadoop.ics.entity.IcsQuestionSulotionEntity;


import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 智能客服-问题搜索记录回答表（用于分析优化）
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
public interface IcsQuestionSulotionService extends IService<IcsQuestionSulotionEntity> {

	PageInfo<IcsQuestionSulotionDTO> queryPage(IcsQuestionSulotionVO params, Integer current, Integer pageSize);
}

