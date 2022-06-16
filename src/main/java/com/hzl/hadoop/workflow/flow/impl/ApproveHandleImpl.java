package com.hzl.hadoop.workflow.flow.impl;

import com.hzl.hadoop.workflow.constant.ApproveActionConstant;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.flow.ApproveHandle;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import com.hzl.hadoop.workflow.service.ProcessVariableService;
import com.hzl.hadoop.workflow.vo.NodeContainer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/06/16 10:37 AM
 */
public class ApproveHandleImpl implements ApproveHandle {

	@Autowired
	ProcessVariableService processVariableService;

	@Autowired
	ApproveHistoryStartService approveHistoryStartService;

	ApproveHistoryHandle approveHistoryHandle;


	/**
	 * 启动流程和每次触发审批按钮后执行，用于生成生成以下一个节点的数据
	 *
	 * @param null
	 * @author hzl 2022-06-16 1:22 PM
	 * @return
	 */
	@Override
	public void beforeApprove(Long processId, ApproveNodeStartEntity startEntity) {

		//0流程变量结合节点配置的审批条件进行判断是否触发审批，如果没有审批条件，直接流转给具体的审批人 todo 完善审批条件后再处理，现在默认通过，审批条件不能支持人员的配置
		List<ProcessVariableEntity> processVariables = processVariableService.queryByProcessId(processId);


		//1：审批处理，审批人，审批组，岗位默认只能勾选其中一个。启动节点可以不做任何配置只用于启动标记

		//审批人
		Long approverId =startEntity.getApproverId();

		//审批组
		Long groupId =startEntity.getApproverGroupId();

		//岗位
		String positionNum =startEntity.getPositionNum();

		//监听器
		Long listenerId=startEntity.getListenerId();

		ApproveHistoryStartEntity approveHistoryStartEntity=new ApproveHistoryStartEntity();
		approveHistoryStartEntity.setApproveAction(ApproveActionConstant.WAIT.value());

		if(approverId!=null){
			//插入审批历史，状态设置为待审批

			approveHistoryStartEntity.setApproverNum("");
			approveHistoryHandle.saveHistory(NodeType.START,approveHistoryStartEntity);
		}else if(groupId!=null){

		}else if(positionNum!=null){

		}else{
			//只有启动节点可以审批人，审批组，岗位不做配置

		}
		if(listenerId!=null){

		}

		//审批完成后，判断后续是否自动审批

	}
}
