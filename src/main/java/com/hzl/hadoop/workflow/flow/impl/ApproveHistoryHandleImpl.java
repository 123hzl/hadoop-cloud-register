package com.hzl.hadoop.workflow.flow.impl;

import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.*;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.service.ApproveHistoryApproverService;
import com.hzl.hadoop.workflow.service.ApproveHistoryEndService;
import com.hzl.hadoop.workflow.service.ApproveHistoryGatewayService;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author hzl 2022/06/16 1:44 PM
 */
@Component
public class ApproveHistoryHandleImpl implements ApproveHistoryHandle {

	@Autowired
	ApproveHistoryStartService approveHistoryStartService;

	@Autowired
	ApproveHistoryGatewayService approveHistoryGatewayService;
	@Autowired
	ApproveHistoryApproverService approveHistoryApproverService;
	@Autowired
	ApproveHistoryEndService approveHistoryEndService;


	@Override
	public void saveHistory(NodeType nodeType, ApproveHistoryEntity hostory) {

		switch (nodeType) {
			case START:

				approveHistoryStartService.save(JsonUtils.cloneObject(hostory,ApproveHistoryStartEntity.class));
				break;
			case GATEWAY:
				approveHistoryGatewayService.save(JsonUtils.cloneObject(hostory,ApproveHistoryGatewayEntity.class));
				break;
			case APPROVE:
				approveHistoryApproverService.save(JsonUtils.cloneObject(hostory,ApproveHistoryApproverEntity.class));
				break;
			case END:
				approveHistoryEndService.save(JsonUtils.cloneObject(hostory,ApproveHistoryEndEntity.class));
				break;
			default:
				break;
		}

	}
}
