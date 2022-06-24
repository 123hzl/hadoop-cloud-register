package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.security.service.impl.CustomUserDetails;
import com.hzl.hadoop.security.utils.DetailHepler;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.constant.ProcessStatusEnum;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.hzl.hadoop.workflow.service.WorkflowCharService;
import com.hzl.hadoop.workflow.vo.StartWorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * description
 * 开始节点制作启动表识，不进行审批人配置
 * @author hzl 2022/06/15 4:46 PM
 */
@Component
public class StartWorkFlowService {

	@Autowired
	WorkflowCharService workflowCharService;
	@Autowired
	ProcessHistoryService processHistoryService;

	@Autowired
	NodeHandle nodeHandle;

	@Autowired
	ApproveHandle approveHandle;

	/**
	 * 返回流程id
	 * todo 防止重复提交，如果已经生成对应的流程记录就不能再提交，添加撤回功能
	 * @return
	 * @author hzl 2022-06-15 4:48 PM
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String startWorkFlow(StartWorkFlowVO startWorkFlowVO) {
		//0:根据流程图编号查询启动节点
		ApproveNodeStartEntity approveNodeStartEntity = nodeHandle.queryNodeInfoByFlowNum(NodeType.START, startWorkFlowVO.getFlowNum())
				.getStartNodeList().get(0);

		//1获取当前用户信息
		CustomUserDetails customUserDetails = DetailHepler.getUserDetails();

		//判断当前开始节点是否配置了审批信息，还是只是作为流程开始的标志


		//3
		ProcessHistoryEntity processHistoryEntity = new ProcessHistoryEntity();
		processHistoryEntity.setStartId(approveNodeStartEntity.getId());
		processHistoryEntity.setProcessStatus(ProcessStatusEnum.START.value());
		processHistoryEntity.setCurrentApproveUser(customUserDetails.getUsername());
		processHistoryEntity.setSubmitPerson(customUserDetails.getUsername());

		//插入流程记录
		processHistoryService.save(processHistoryEntity);

		//2根据流程节点配置，处理工作流审批逻辑
		approveHandle.afterApprove(processHistoryEntity.getId(),approveNodeStartEntity.getId(),NodeType.START.getValue());




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
