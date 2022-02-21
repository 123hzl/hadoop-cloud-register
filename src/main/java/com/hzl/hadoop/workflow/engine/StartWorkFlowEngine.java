package com.hzl.hadoop.workflow.engine;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.constant.ApproveActionConstant;
import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.entity.StartNodeEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.hzl.hadoop.workflow.service.ProcessVariableService;
import com.hzl.hadoop.workflow.service.StartNodeService;
import com.hzl.hadoop.workflow.vo.StartWorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * description
 * 流程启动引擎
 *
 * @author hzl 2021/11/04 2:54 PM
 */
@Component
public class StartWorkFlowEngine {

	@Autowired
	private ApproveHistoryStartService approveHistoryStartService;
	@Autowired
	private ProcessHistoryService processHistoryService;
	@Autowired
	private StartNodeService startNodeService;
	@Autowired
	private ProcessVariableService processVariableService;


	public ResponseEntity startWorkFlow(@Validated StartWorkFlowVO startWorkFlowVO) {

		//根据开始节点编号，查询开始节点信息
		QueryWrapper<StartNodeEntity> queryWrapper = new QueryWrapper();
		queryWrapper.eq("node_num", startWorkFlowVO.getStartNodeNum());
		//根据启动节点获取下一节点信息
		StartNodeEntity startNodeEntity = startNodeService.getOne(queryWrapper,false);

		ProcessHistoryEntity processHistoryEntity = ProcessHistoryEntity
				.builder()
				.currentApproveUser(startWorkFlowVO.getSubmitPerson())
				.startId(startNodeEntity.getId())
				.submitPerson(startWorkFlowVO.getSubmitPerson())
				.build();

		//插入流程记录表，返回流程id
		boolean saveStatue = processHistoryService.save(processHistoryEntity);

		//流程id
		Long processId = processHistoryEntity.getId();
		//返回结果判断
		if (!saveStatue) {
			throw new CommonException("启动工作流过程-流程记录保存失败");
		}


		//插入流程变量表
		if (StringUtils.hasLength(startWorkFlowVO.getProcessVaribale())) {
			processVariableService.save(ProcessVariableEntity.builder()
					.processId(processId)
					.variable(startWorkFlowVO.getProcessVaribale())
					.build());
		}


		//保存的是启动节点审批记录
		approveHistoryStartService.save(ApproveHistoryStartEntity
				.builder()
				.approveAction(ApproveActionConstant.AGREE.value())
				.processId(processId)
				//后期从登陆信息上下文中获取
				.approverNum("hzl")
				.currentNodeId(startNodeEntity.getId())
				.nextNodeId(startNodeEntity.getNextNodeId())
				.build());

		return new ResponseEntity(HttpStatus.OK);
	}

}
