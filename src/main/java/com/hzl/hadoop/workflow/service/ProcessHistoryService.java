package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 流程记录-每次启动流程就插入一条流程记录
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ProcessHistoryService extends IService<ProcessHistoryEntity> {

	PageInfo queryPage(ProcessHistoryEntity params, int start, int pageSize);
}

