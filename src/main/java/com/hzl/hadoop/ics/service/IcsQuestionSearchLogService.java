package com.hzl.hadoop.ics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.ics.vo.IcsQuestionSearchLogVO;
import com.hzl.hadoop.ics.dto.IcsQuestionSearchLogDTO;
import com.hzl.hadoop.ics.entity.IcsQuestionSearchLogEntity;


import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 智能客服-问题搜索记录表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:46
 */
public interface IcsQuestionSearchLogService extends IService<IcsQuestionSearchLogEntity> {

	PageInfo<IcsQuestionSearchLogDTO> queryPage(IcsQuestionSearchLogVO params, Integer current, Integer pageSize);
}

