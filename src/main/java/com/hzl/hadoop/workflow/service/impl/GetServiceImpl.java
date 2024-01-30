package com.hzl.hadoop.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetServiceImpl implements GetService {

	@Autowired
	ApproveHistoryStartService approveHistoryStartService;

	@Autowired
	ApproveHistoryGatewayService approveHistoryGatewayService;
	@Autowired
	ApproveHistoryApproverService approveHistoryApproverService;
	@Autowired
	ApproveHistoryEndService approveHistoryEndService;

	@Override
	public IService getApproveHistoryService(NodeType nodeType) {
		switch (nodeType) {
			case START:
				return approveHistoryStartService;
			case GATEWAY:
				return approveHistoryGatewayService;
			case APPROVE:
				return approveHistoryApproverService;
			case END:
				return approveHistoryEndService;
			default:
				break;
		}
		return null;
	}
}
