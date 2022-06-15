package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 流程变量-启动流程的时候初始化，个流程节点可以通过流程id查询变量
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:05
 */
public interface ProcessVariableService extends IService<ProcessVariableEntity> {

	PageInfo queryPage(ProcessVariableEntity params, int start, int pageSize);
}

