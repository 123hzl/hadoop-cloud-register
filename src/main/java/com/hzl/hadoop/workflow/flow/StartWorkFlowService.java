package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.security.service.MyUserDetailsService;
import com.hzl.hadoop.security.vo.UserInfoVO;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.constant.ProcessStatusEnum;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeStartService;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.hzl.hadoop.workflow.service.WorkflowCharService;
import com.hzl.hadoop.workflow.vo.StartWorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 *
 * @author hzl 2022/06/15 4:46 PM
 */
@Component
public class StartWorkFlowService {

	@Autowired
	WorkflowCharService workflowCharService;
	@Autowired
	ProcessHistoryService processHistoryService;
	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	NodeHandle nodeHandle;

	/**
	 * 返回流程id
	 *
	 * @param null
	 * @return
	 * @author hzl 2022-06-15 4:48 PM
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String startWorkFlow(StartWorkFlowVO startWorkFlowVO) {
		//0:根据流程图编号查询启动节点
		ApproveNodeStartEntity approveNodeStartEntity = nodeHandle.queryNodeInfoByFlowNum(NodeType.START, startWorkFlowVO.getFlowNum())
				.getStartNodeList().get(0);

		//1
		UserInfoVO userInfoVO = myUserDetailsService.getCurrentUserInfo();

		//2根据流程节点配置，处理工作流审批逻辑


		//3
		ProcessHistoryEntity processHistoryEntity = new ProcessHistoryEntity();
		processHistoryEntity.setStartId(approveNodeStartEntity.getId());
		processHistoryEntity.setProcessStatus(ProcessStatusEnum.START.value());
		processHistoryEntity.setCurrentApproveUser(userInfoVO.getUsername());
		processHistoryEntity.setSubmitPerson(userInfoVO.getUsername());

		//插入流程记录
		processHistoryService.save(processHistoryEntity);


		return "";
	}

	/**
	 * 流转到下一个节点
	 *
	 * @param processId 流程ID
	 * @return
	 * @author hzl 2022-06-15 6:00 PM
	 */
	public String next(Long processId) {

		return "";
	}


}
