package com.hzl.hadoop.workflow.vo;

import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.ApproveNodeApproverEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeGatewayEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
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




}
