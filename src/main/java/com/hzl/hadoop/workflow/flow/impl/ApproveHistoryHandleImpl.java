package com.hzl.hadoop.workflow.flow.impl;

import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.ApproveHistoryApproverEntity;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEndEntity;
import com.hzl.hadoop.workflow.entity.ApproveHistoryGatewayEntity;
import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
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
	public <T> void saveHistory(NodeType nodeType, T hostory) {

		switch (nodeType) {
			case START:
				approveHistoryStartService.save((ApproveHistoryStartEntity) hostory);
				break;
			case GATEWAY:
				approveHistoryGatewayService.save((ApproveHistoryGatewayEntity) hostory);
				break;
			case APPROVE:
				approveHistoryApproverService.save((ApproveHistoryApproverEntity) hostory);
				break;
			case END:
				approveHistoryEndService.save((ApproveHistoryEndEntity) hostory);
				break;
			default:
				break;
		}

	}
}
