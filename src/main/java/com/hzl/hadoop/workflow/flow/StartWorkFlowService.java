package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.security.service.impl.CustomUserDetails;
import com.hzl.hadoop.security.utils.DetailHepler;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.constant.ProcessStatusEnum;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.hzl.hadoop.workflow.service.WorkflowCharService;
import com.hzl.hadoop.workflow.vo.ApproveVO;
import com.hzl.hadoop.workflow.vo.NodeContainer;
import com.hzl.hadoop.workflow.vo.StartWorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description
 * 开始节点制作启动表识，不进行审批人配置
 * 启动节点只用于启动标记，表中的审批人，审批组，等线默认为空
 * @author hzl 2022/06/15 4:46 PM
 */
@Component
public class StartWorkFlowService {

	@Autowired
	private WorkflowCharService workflowCharService;
	@Autowired
	private ProcessHistoryService processHistoryService;

	@Autowired
	private NodeHandle nodeHandle;

	@Autowired
	private ApproveHandle approveHandle;
	@Autowired
	private ApproveHistoryHandle approveHistoryHandle;

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
	 * 审批
	 *
	 * @param approveVO
	 * @return
	 * @author hzl 2022-06-15 6:00 PM
	 */
	public String approve(ApproveVO approveVO) {

		//1根据审批历史id和节点类型查询对应的流程审批历史数据，将状态从审批状态从待审批，更新为审批同意
		//不需要了List<ApproveHistoryEntity> approveHistoryEntityList=approveHistoryHandle.queryHistory(approveVO);

		Boolean isSuccess=approveHistoryHandle.updateHistory(approveVO.getHistoryId(),approveVO.getApproveAction(),approveVO.getNodeType());
		if(!isSuccess){
			throw new CommonException("审批失败！！！");
		}

		approveHandle.afterApprove(approveVO.getProcessId(),approveVO.getNodeId(),approveVO.getNodeType());

		return "";
	}


}
