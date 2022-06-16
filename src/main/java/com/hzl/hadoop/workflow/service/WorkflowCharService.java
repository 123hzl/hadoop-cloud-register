package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.WorkflowCharEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 前端生成的流程图，需要转换成开始节点，审批节点，网关节点，结束节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface WorkflowCharService extends IService<WorkflowCharEntity> {

	PageInfo queryPage(WorkflowCharEntity params, int start, int pageSize);

	WorkflowCharEntity selectCharByNum(String flowNum);
}

