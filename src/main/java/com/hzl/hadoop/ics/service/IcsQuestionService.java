package com.hzl.hadoop.ics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.ics.vo.IcsQuestionVO;
import com.hzl.hadoop.ics.dto.IcsQuestionDTO;
import com.hzl.hadoop.ics.entity.IcsQuestionEntity;


import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 智能客服-问题记录表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
public interface IcsQuestionService extends IService<IcsQuestionEntity> {

	PageInfo<IcsQuestionDTO> queryPage(IcsQuestionVO params, Integer current, Integer pageSize);


	boolean saveInfo(IcsQuestionEntity icsQuestionEntity);

	void searchQuestion(String question);
}

