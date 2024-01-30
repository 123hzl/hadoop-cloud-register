package com.hzl.hadoop.workflow.flow.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.dto.ApproveHistoryDTO;
import com.hzl.hadoop.workflow.entity.*;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.service.*;
import com.hzl.hadoop.workflow.vo.ApproveHistory;
import com.hzl.hadoop.workflow.vo.ApproveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hzl.hadoop.workflow.constant.NodeType.*;

/**
 * description
 *
 * @author hzl 2022/06/16 1:44 PM
 */
@Component
public class ApproveHistoryHandleImpl implements ApproveHistoryHandle {

	@Autowired
	GetService getService;


	@Autowired
	ApproveHistoryStartService approveHistoryStartService;

	@Autowired
	ApproveHistoryGatewayService approveHistoryGatewayService;
	@Autowired
	ApproveHistoryApproverService approveHistoryApproverService;
	@Autowired
	ApproveHistoryEndService approveHistoryEndService;


	@Override
	public void saveHistory(NodeType nodeType, ApproveHistoryEntity historyEntity) {

		getService.getApproveHistoryService(nodeType).save(JsonUtils.cloneObject(historyEntity,ApproveHistoryStartEntity.class));
		/*switch (nodeType) {
			case START:

				approveHistoryStartService.save(JsonUtils.cloneObject(historyEntity,ApproveHistoryStartEntity.class));
				break;
			case GATEWAY:
				approveHistoryGatewayService.save(JsonUtils.cloneObject(historyEntity,ApproveHistoryGatewayEntity.class));
				break;
			case APPROVE:
				approveHistoryApproverService.save(JsonUtils.cloneObject(historyEntity,ApproveHistoryApproverEntity.class));
				break;
			case END:
				approveHistoryEndService.save(JsonUtils.cloneObject(historyEntity,ApproveHistoryEndEntity.class));
				break;
			default:
				break;
		}*/

	}

	@Override
	public List<ApproveHistoryEntity> queryHistory(ApproveVO approveVO) {
		QueryWrapper wrapper= new QueryWrapper();
		if(approveVO!=null&&approveVO.getHistoryId()!=null){
			wrapper.eq("id",approveVO.getHistoryId());

		}else{
			wrapper.eq("current_node_id",approveVO.getNodeId());
			wrapper.eq("process_id",approveVO.getProcessId());
			//查询待处理的节点，因为有驳回再提交的
			wrapper.eq("node_status",approveVO.getNodeStatus());

		}




		if(approveVO.getNodeType().equals(START.getValue())){
			return approveHistoryStartService.list(wrapper);
		}else if(approveVO.getNodeType().equals(GATEWAY.getValue())){
			return approveHistoryGatewayService.list(wrapper);
		}else if(approveVO.getNodeType().equals(APPROVE.getValue())){
			return approveHistoryStartService.list(wrapper);
		}else if(approveVO.getNodeType().equals(END.getValue())){
			return approveHistoryEndService.list(wrapper);
		}

		return null;
	}

	@Override
	public Boolean updateHistory(Long historyId, Integer approveStatus,Integer nodeType) {
		UpdateWrapper wrapper= new UpdateWrapper();
		wrapper.eq("id",historyId);
		wrapper.set("approve_action",approveStatus);
		if(nodeType.equals(START.getValue())){
			return approveHistoryStartService.update(wrapper);
		}else if(nodeType.equals(GATEWAY.getValue())){
			return approveHistoryGatewayService.update(wrapper);
		}else if(nodeType.equals(APPROVE.getValue())){
			return approveHistoryStartService.update(wrapper);
		}else if(nodeType.equals(END.getValue())){
			return approveHistoryEndService.update(wrapper);
		}
		return false;
	}

	@Override
	public Boolean updateNodeStatus(NodeType nodeType, ApproveHistoryEntity historyEntity) {
		Boolean result=false;
		UpdateWrapper updateWrapper=new UpdateWrapper();
		updateWrapper.set("node_status",historyEntity.getNodeStatus());
		updateWrapper.eq("process_id",historyEntity.getProcessId());
		updateWrapper.eq("current_node_id",historyEntity.getCurrentNodeId());
		switch (nodeType) {
			case START:
				result=approveHistoryStartService.update(updateWrapper);
				break;
			case GATEWAY:
				result=approveHistoryGatewayService.update(updateWrapper);
				break;
			case APPROVE:
				result=approveHistoryApproverService.update(updateWrapper);
				break;
			case END:
				result=approveHistoryEndService.update(updateWrapper);
				break;
			default:
				break;
		}
		return result;
	}
}
