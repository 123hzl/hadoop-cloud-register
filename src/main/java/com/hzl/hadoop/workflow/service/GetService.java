package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.constant.NodeType;

public interface GetService {

	IService getApproveHistoryService(NodeType nodeType);
}
