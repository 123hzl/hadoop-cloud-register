package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.WorkflowListenerEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 工作流监听配置类
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface WorkflowListenerService extends IService<WorkflowListenerEntity> {

	PageInfo queryPage(WorkflowListenerEntity params, int start, int pageSize);
}

