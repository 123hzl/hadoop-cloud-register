package com.hzl.hadoop.workflow.flow;

import com.alibaba.fastjson.JSON;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.security.service.impl.CustomUserDetails;
import com.hzl.hadoop.security.utils.DetailHepler;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.workflow.constant.ApproveActionConstant;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.constant.ProcessStatusEnum;
import com.hzl.hadoop.workflow.dto.ListenerDTO;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.hzl.hadoop.workflow.service.ProcessVariableService;
import com.hzl.hadoop.workflow.service.WorkflowCharService;
import com.hzl.hadoop.workflow.vo.ApproveVO;
import com.hzl.hadoop.workflow.vo.NodeContainer;
import com.hzl.hadoop.workflow.vo.StartWorkFlowVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptException;
import java.util.List;

/**
 * description
 * 开始节点制作启动表识，不进行审批人配置
 * 启动节点只用于启动标记，表中的审批人，审批组，等线默认为空，且目前只支持后置监听
 * @author hzl 2022/06/15 4:46 PM
 */
@Slf4j
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

	@Autowired
	ProcessVariableService processVariableService;

	/**
	 * 返回流程id
	 * todo 防止重复提交，如果已经生成对应的流程记录就不能再提交，添加撤回功能
	 * 开始节点只作为流程启动的标示。不能配置审批人信息
	 * @return
	 * @author hzl 2022-06-15 4:48 PM
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String startWorkFlow(StartWorkFlowVO startWorkFlowVO) throws ScriptException {

		//0:根据流程图编号查询启动节点
		ApproveNodeStartEntity approveNodeStartEntity = nodeHandle.queryNodeInfoByFlowNum(NodeType.START, startWorkFlowVO.getFlowNum())
				.getStartNodeList().get(0);

		//1获取当前用户信息
		CustomUserDetails customUserDetails = DetailHepler.getUserDetails();

		//3
		ProcessHistoryEntity processHistoryEntity = new ProcessHistoryEntity();
		processHistoryEntity.setStartId(approveNodeStartEntity.getId());
		processHistoryEntity.setProcessStatus(ProcessStatusEnum.START.value());
		processHistoryEntity.setCurrentApproveUser(customUserDetails.getUsername());
		processHistoryEntity.setSubmitPerson(customUserDetails.getUsername());

		//插入流程记录
		processHistoryService.save(processHistoryEntity);

		//启动的时候保存流程变量
		processVariableService.save(ProcessVariableEntity.builder()
				.processId(processHistoryEntity.getId())
				.variable(JSON.toJSONString(JsonUtils.mapToJson(startWorkFlowVO.getProcessVariable())))
				.build());

		//2根据流程节点配置，处理工作流审批逻辑
		approveHandle.approve(processHistoryEntity.getId(),approveNodeStartEntity.getId(),NodeType.START.getValue(),null);

		//执行后置监听
		approveHandle.afterApprove(processHistoryEntity.getId(),approveNodeStartEntity.getId(),NodeType.START.getValue());


		return "";
	}

	/**
	 * 审批同意或者拒绝
	 *todo 存在重复审批问题，需要处理，重复审批更新状态不会报错
	 * @param approveVO
	 * @return
	 * @author hzl 2022-06-15 6:00 PM
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Boolean approveOrReject(ApproveVO approveVO) throws ScriptException {
		Boolean isSuccess;
		ListenerDTO listenerDTO =approveHandle.beforeApprove(approveVO.getProcessId(),approveVO.getNodeId(),approveVO.getNodeType());
		if(listenerDTO==null||listenerDTO.getStatus()==2){
			log.info("正常执行监听{}",listenerDTO);
		}else if (listenerDTO.getStatus()==1){
			return true;
		}else if(listenerDTO.getStatus()==3) {
			throw new CommonException("审批失败,原因".concat(listenerDTO.getContent()));
		}else {
			throw new CommonException("监听返回状态有问题"+approveVO.toString());
		}
		//1根据审批历史id和节点类型查询对应的流程审批历史数据，将状态从审批状态从待审批，更新为审批同意
		//不需要了List<ApproveHistoryEntity> approveHistoryEntityList=approveHistoryHandle.queryHistory(approveVO);

		//todo 更新完成后，下面approveHandle.approve取发现状态已经是审批完成，只是没有提交，读取数据库的时候是完成
		isSuccess=approveHistoryHandle.updateHistory(approveVO.getHistoryId(),approveVO.getApproveAction(),approveVO.getNodeType());
		if(!isSuccess){
			throw new CommonException("审批失败！！！");
		}
		//只有审批通过才执行approve方法
		if(ApproveActionConstant.AGREE.value().equals(approveVO.getApproveAction())){
			approveHandle.approve(approveVO.getProcessId(),approveVO.getNodeId(),approveVO.getNodeType(),approveVO.getHistoryId());
		}

		//执行后置监听
		isSuccess=approveHandle.afterApprove(approveVO.getProcessId(),approveVO.getNodeId(),approveVO.getNodeType());

		return isSuccess;
	}

}
