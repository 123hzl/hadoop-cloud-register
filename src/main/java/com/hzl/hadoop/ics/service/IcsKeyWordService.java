package com.hzl.hadoop.ics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.ics.vo.IcsKeyWordVO;
import com.hzl.hadoop.ics.dto.IcsKeyWordDTO;
import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;


import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.ics.vo.IcsResultVO;


import java.util.List;
import java.util.Map;

/**
 * 智能客服-问题分词库
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
public interface IcsKeyWordService extends IService<IcsKeyWordEntity> {

	PageInfo<IcsKeyWordDTO> queryPage(IcsKeyWordVO params, Integer current, Integer pageSize);
}

