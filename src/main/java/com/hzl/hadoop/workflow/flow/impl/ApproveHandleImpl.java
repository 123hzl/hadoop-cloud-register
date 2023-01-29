package com.hzl.hadoop.workflow.flow.impl;

import com.alibaba.fastjson.JSONObject;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.constant.ApproveActionConstant;
import com.hzl.hadoop.workflow.constant.NodeStatusEnum;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.entity.ApproveConditionsEntity;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeAbstract;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.flow.ApproveHandle;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.flow.NodeHandle;
import com.hzl.hadoop.workflow.listener.ListenerHandler;
import com.hzl.hadoop.workflow.service.*;
import com.hzl.hadoop.workflow.utils.ScriptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 * todo 路由节点加一个开关，用来控制转发的节点是否需要全部审批通过，
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
	@Autowired
	ApproveConditionsService approveConditionsService;

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

		if (nodeDTO != null&&nodeDTO.getBeListenerId()!=null) {
			listenerHandler.handle(processId, nodeDTO.getBeListenerId());
		}


		return true;
	}

	@Override
	public Boolean approve(Long processId, Long nodeId, Integer nodeType) throws ScriptException {
		//审批操作，
		NodeType nodeTypeEnum=NodeType.match(nodeType);
		//判断当前节点是否需要全部审批通过，如果需要全部审批完成后才执行后面的逻辑，也就是当前节点的审批组不为空，且为全部审批通过
		//根据nodeId查询当前节点审批组信息。
		if (!approveGroupService.isAllApprove(processId, nodeId, nodeType)) {
			return true;
		}else{
			//如果当前节点审批完成，那么更新节点状态为结束
			ApproveHistoryEntity historyEntity=new ApproveHistoryEntity();
			historyEntity.setNodeStatus(NodeStatusEnum.END.getValue());
			historyEntity.setProcessId(processId);
			historyEntity.setCurrentNodeId(nodeId);
			if(approveHistoryHandle.updateNodeStatus(nodeTypeEnum,historyEntity)){
				throw new CommonException("审批失败请联系管理员");
			}
		}


		//查询当前节点关联的所有节点
		List<NodeDTO> nodeDTOS = approveNodeStartService.queryNode(nodeType, nodeId);

		for(NodeDTO nodeEntity:nodeDTOS){
			//0流程变量结合节点配置的审批条件进行判断是否触发审批，如果没有审批条件，直接流转给具体的审批人 todo 完善审批条件后再处理，现在默认通过，审批条件不能支持人员的配置
			//根据当前节点关联的节点配置的审批条件，判断是否预先生成审批人信息
			ProcessVariableEntity processVariables = processVariableService.queryByProcessId(processId);

			//获取节点关联的审批条件
			Boolean result=true;
			if(nodeEntity.getConditionId()!=null){
				ApproveConditionsEntity approveConditionsEntity=approveConditionsService.getById(nodeEntity.getConditionId());

				result=ScriptUtil.expire(approveConditionsEntity.getExpression(), JSONObject.parseObject(processVariables.getVariable()));
			}


			//审批条件通过了才能执行下面预先生成审批人的功能（节点配置审批条件的前提是，有其它节点配置了相反的审批条件。不然流程将断掉）
			if(result){
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
					//只有启动节点,路由节点，结束节点，可以审批人，审批组，岗位不做配置

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

			}


		}

		return true;
	}

	/**
	 * 启动时和每次触发审批按钮后执行，用于生成生成下一个节点的数据
	 * nodeEntity 当前节点实体
	 * @return
	 * @author hzl 2022-06-16 1:22 PM
	 */
	@Override
	public Boolean afterApprove(Long processId, Long nodeId, Integer nodeType) {
		NodeType nodeTypeEnum=NodeType.match(nodeType);

		//执行当前节点后置监听
		NodeDTO nodeDTO = nodeHandle.queryNodeById(nodeTypeEnum, nodeId);

		if (nodeDTO != null&&nodeDTO.getAfListenerId()!=null) {
			//执行后置监听
			listenerHandler.handle(processId, nodeDTO.getAfListenerId());
		}

		return true;
	}

	public List<ApproveHistoryEntity> generateApproveHistoryEntity(Long userId, List<Long> userIds, Long nodeId, Long processId) {
		if (userId != null) {
			ApproveHistoryEntity approveHistoryStartEntity = new ApproveHistoryEntity();
			approveHistoryStartEntity.setApproveAction(ApproveActionConstant.WAIT.value());
			approveHistoryStartEntity.setCurrentNodeId(nodeId);
			approveHistoryStartEntity.setProcessId(processId);
			approveHistoryStartEntity.setNodeStatus(NodeStatusEnum.WAIT.getValue());
			approveHistoryStartEntity.setApproverId(userId);
			return Collections.singletonList(approveHistoryStartEntity);
		} else {
			return userIds.stream().map(user -> {
				ApproveHistoryEntity approveHistoryStartEntity = new ApproveHistoryEntity();
				approveHistoryStartEntity.setApproveAction(ApproveActionConstant.WAIT.value());
				approveHistoryStartEntity.setCurrentNodeId(nodeId);
				approveHistoryStartEntity.setProcessId(processId);
				approveHistoryStartEntity.setNodeStatus(NodeStatusEnum.WAIT.getValue());

				approveHistoryStartEntity.setApproverId(user);
				return approveHistoryStartEntity;
			}).collect(Collectors.toList());
		}
	}
}
