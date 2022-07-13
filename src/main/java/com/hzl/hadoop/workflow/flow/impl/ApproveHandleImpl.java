package com.hzl.hadoop.workflow.flow.impl;

import com.hzl.hadoop.workflow.constant.ApproveActionConstant;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeAbstract;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.flow.ApproveHandle;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.flow.NodeHandle;
import com.hzl.hadoop.workflow.listener.ListenerHandler;
import com.hzl.hadoop.workflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author hzl 2022/06/16 10:37 AM
 */
@Component
public class ApproveHandleImpl implements ApproveHandle {

	@Autowired
	ProcessVariableService processVariableService;

	@Autowired
	ApproveHistoryStartService approveHistoryStartService;
	@Autowired
	ApproveHistoryHandle approveHistoryHandle;
	@Autowired
	ApproveGroupUserService approveGroupUserService;

	@Autowired
	ApproveNodeStartService approveNodeStartService;
	@Autowired
	ApproveGroupService approveGroupService;
	@Autowired
	NodeHandle nodeHandle;
	@Autowired
	ListenerHandler listenerHandler;

	/**
	 * 流程启动前执行
	 *
	 * @param null
	 * @return
	 * @author hzl 2022-06-16 1:22 PM
	 */
	@Override
	public Boolean beforeApprove(Long processId, Long nodeId, Integer nodeType) {

		//执行当前节点的前置监听
		NodeDTO nodeDTO = nodeHandle.queryNodeById(NodeType.match(nodeType), nodeId);

		if (nodeDTO != null) {
			listenerHandler.handle(processId, nodeDTO.getBeListenerId());
		}
		return true;
	}

	@Override
	public Boolean approve(Long processId, ApproveNodeAbstract startEntity) {
		//审批操作，
		return true;
	}

	/**
	 * 启动时和每次触发审批按钮后执行，用于生成生成下一个节点的数据
	 * nodeEntity 当前节点实体
	 *
	 * @return
	 * @author hzl 2022-06-16 1:22 PM
	 */
	@Override
	public Boolean afterApprove(Long processId, Long nodeId, Integer nodeType) {
		//判断当前节点是否需要全部审批通过，如果需要全部审批完成后才执行后面的逻辑，也就是当前节点的审批组不为空，且为全部审批通过
		//根据nodeId查询当前节点审批组信息。
		if (!approveGroupService.isAllApprove(processId, nodeId, nodeType)) {
			return true;
		}

		//执行当前节点后置监听
		//执行当前节点的前置监听
		NodeDTO nodeDTO = nodeHandle.queryNodeById(NodeType.match(nodeType), nodeId);

		if (nodeDTO != null) {
			listenerHandler.handle(processId, nodeDTO.getAfListenerId());
		}

		//查询当前节点关联的所有节点
		List<NodeDTO> nodeDTOS = approveNodeStartService.queryNode(nodeType, nodeId);

		nodeDTOS.forEach(nodeEntity -> {
			//0流程变量结合节点配置的审批条件进行判断是否触发审批，如果没有审批条件，直接流转给具体的审批人 todo 完善审批条件后再处理，现在默认通过，审批条件不能支持人员的配置
			List<ProcessVariableEntity> processVariables = processVariableService.queryByProcessId(processId);


			//1：审批处理，审批人，审批组，岗位默认只能勾选其中一个。启动节点可以不做任何配置只用于启动标记

			//审批人
			Long approverId = nodeEntity.getApproverId();

			//审批组
			Long groupId = nodeEntity.getApproverGroupId();

			//岗位
			Long positionid = nodeEntity.getPositionId();

			//监听器
			Long beListenerId = nodeEntity.getBeListenerId();
			Long afListenerId = nodeEntity.getAfListenerId();
			//执行当前节点的后置监听，和下个节点的前置监听
			if (beListenerId != null) {
				listenerHandler.handle(processId, beListenerId);
			}

			//插入审批信息

			List<ApproveHistoryEntity> approveHistoryList = new ArrayList<>();

			if (approverId != null) {
				//插入审批历史，状态设置为待审批
				approveHistoryList = generateApproveHistoryEntity(approverId, null, nodeEntity.getId(), processId);
			} else if (groupId != null) {
				//根据审批组插入多条记录
				List<Long> userIds = approveGroupUserService.queryUserIdsByGroupId(groupId);
				approveHistoryList = generateApproveHistoryEntity(null, userIds, nodeEntity.getId(), processId);
			} else if (positionid != null) {
				//todo 查询岗位下的所有员工
				approveHistoryList = generateApproveHistoryEntity(null, null, nodeEntity.getId(), processId);
			} else {
				//只有启动节点可以审批人，审批组，岗位不做配置

			}

			//判断是否自动审批 todo 该逻辑后续完善

			if (nodeEntity.getNodeType().equals(NodeType.START.getValue())) {
				approveHistoryList.stream().forEach(entity -> approveHistoryHandle.saveHistory(NodeType.START, entity));
			} else if (nodeEntity.getNodeType().equals(NodeType.GATEWAY.getValue())) {
				approveHistoryList.stream().forEach(entity -> approveHistoryHandle.saveHistory(NodeType.GATEWAY, entity));
			} else if (nodeEntity.getNodeType().equals(NodeType.APPROVE.getValue())) {
				approveHistoryList.stream().forEach(entity -> approveHistoryHandle.saveHistory(NodeType.APPROVE, entity));
			} else if (nodeEntity.getNodeType().equals(NodeType.END.getValue())) {
				approveHistoryList.stream().forEach(entity -> approveHistoryHandle.saveHistory(NodeType.END, entity));

			}

		});

		return true;
	}

	public List<ApproveHistoryEntity> generateApproveHistoryEntity(Long userId, List<Long> userIds, Long nodeId, Long processId) {
		if (userId != null) {
			ApproveHistoryEntity approveHistoryStartEntity = new ApproveHistoryEntity();
			approveHistoryStartEntity.setApproveAction(ApproveActionConstant.WAIT.value());
			approveHistoryStartEntity.setCurrentNodeId(nodeId);
			approveHistoryStartEntity.setProcessId(processId);

			approveHistoryStartEntity.setApproverId(userId);
			return Collections.singletonList(approveHistoryStartEntity);
		} else {
			return userIds.stream().map(user -> {
				ApproveHistoryEntity approveHistoryStartEntity = new ApproveHistoryEntity();
				approveHistoryStartEntity.setApproveAction(ApproveActionConstant.WAIT.value());
				approveHistoryStartEntity.setCurrentNodeId(nodeId);
				approveHistoryStartEntity.setProcessId(processId);

				approveHistoryStartEntity.setApproverId(user);
				return approveHistoryStartEntity;
			}).collect(Collectors.toList());
		}
	}
}
