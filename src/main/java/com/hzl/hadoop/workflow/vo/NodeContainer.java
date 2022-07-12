package com.hzl.hadoop.workflow.vo;

import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.*;
import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/06/16 9:54 AM
 */
@Data
public class NodeContainer {

	private NodeType nodeType;

	private ApproveNodeStartEntity approveNodeStartEntity;

	private ApproveNodeGatewayEntity approveNodeGatewayEntity;

	private ApproveNodeApproverEntity approveNodeApproverEntity;

	private ApproveNodeEndEntity approveNodeEndEntity;


	private List<ApproveNodeStartEntity> startNodeList;

	private List<ApproveNodeGatewayEntity> gatewayNodeList;

	private List<ApproveNodeApproverEntity> approverNodeList;

	private List<ApproveNodeEndEntity> endNodeList;



	public ApproveNodeAbstract getNodeInfo(NodeType nodeType){
		switch (nodeType) {
			case START:
				return approveNodeStartEntity;
			case GATEWAY:
				return approveNodeGatewayEntity;
			case APPROVE:
				return approveNodeApproverEntity;
			case END:
				return approveNodeEndEntity;
			default:
				break;
		}
		return null;
	}


}
