package com.hzl.hadoop.workflow.flow.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.flow.NodeHandle;
import com.hzl.hadoop.workflow.service.ApproveNodeApproverService;
import com.hzl.hadoop.workflow.service.ApproveNodeEndService;
import com.hzl.hadoop.workflow.service.ApproveNodeGatewayService;
import com.hzl.hadoop.workflow.service.ApproveNodeStartService;
import com.hzl.hadoop.workflow.vo.NodeContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/06/16 10:16 AM
 */
@Component
public class NodeHandleImpl implements NodeHandle {

	@Autowired
	ApproveNodeStartService approveNodeStartService;
	@Autowired
	ApproveNodeGatewayService approveNodeGatewayService;
	@Autowired
	ApproveNodeApproverService approveNodeApproverService;
	@Autowired
	ApproveNodeEndService approveNodeEndService;


	@Override
	public NodeContainer queryNodeInfo(NodeType nodeType, Long nodeId) {

		NodeContainer nodeContainer = new NodeContainer();
		switch (nodeType) {
			case START:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setApproveNodeStartEntity(approveNodeStartService.getById(nodeId));
				break;
			case GATEWAY:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setApproveNodeGatewayEntity(approveNodeGatewayService.getById(nodeId));
				break;
			case APPROVE:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setApproveNodeApproverEntity(approveNodeApproverService.getById(nodeId));
				break;
			case END:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setApproveNodeEndEntity(approveNodeEndService.getById(nodeId));
				break;
			default:
				break;
		}
		return nodeContainer;
	}

	@Override
	public NodeContainer queryNodeInfoByFlowNum(NodeType nodeType, String flowNum) {
		NodeContainer nodeContainer = new NodeContainer();
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("flow_num", flowNum);
		switch (nodeType) {
			case START:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setStartNodeList(approveNodeStartService.list(queryWrapper));
				break;
			case GATEWAY:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setGatewayNodeList(approveNodeGatewayService.list(queryWrapper));
				break;
			case APPROVE:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setApproverNodeList(approveNodeApproverService.list(queryWrapper));
				break;
			case END:
				nodeContainer.setNodeType(nodeType);
				nodeContainer.setEndNodeList(approveNodeEndService.list(queryWrapper));
				break;
			default:
				break;
		}
		return nodeContainer;
	}

	@Override
	public NodeDTO queryNodeById(NodeType nodeType, Long nodeId) {
		switch (nodeType) {
			case START:
				return approveNodeStartService.queryNodeById(nodeId);
			case GATEWAY:
				return approveNodeGatewayService.queryNodeById(nodeId);

			case APPROVE:
				return approveNodeApproverService.queryNodeById(nodeId);

			case END:
				return approveNodeEndService.queryNodeById(nodeId);

			default:
				break;
		}
		return null;
	}


}
