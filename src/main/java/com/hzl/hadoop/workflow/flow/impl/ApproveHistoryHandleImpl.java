package com.hzl.hadoop.workflow.flow.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.dto.ApproveHistoryDTO;
import com.hzl.hadoop.workflow.entity.*;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.service.ApproveHistoryApproverService;
import com.hzl.hadoop.workflow.service.ApproveHistoryEndService;
import com.hzl.hadoop.workflow.service.ApproveHistoryGatewayService;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
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
	ApproveHistoryStartService approveHistoryStartService;

	@Autowired
	ApproveHistoryGatewayService approveHistoryGatewayService;
	@Autowired
	ApproveHistoryApproverService approveHistoryApproverService;
	@Autowired
	ApproveHistoryEndService approveHistoryEndService;


	@Override
	public void saveHistory(NodeType nodeType, ApproveHistoryEntity hostory) {

		switch (nodeType) {
			case START:

				approveHistoryStartService.save(JsonUtils.cloneObject(hostory,ApproveHistoryStartEntity.class));
				break;
			case GATEWAY:
				approveHistoryGatewayService.save(JsonUtils.cloneObject(hostory,ApproveHistoryGatewayEntity.class));
				break;
			case APPROVE:
				approveHistoryApproverService.save(JsonUtils.cloneObject(hostory,ApproveHistoryApproverEntity.class));
				break;
			case END:
				approveHistoryEndService.save(JsonUtils.cloneObject(hostory,ApproveHistoryEndEntity.class));
				break;
			default:
				break;
		}

	}

	@Override
	public List<ApproveHistoryEntity> queryHistory(ApproveVO approveVO) {
		QueryWrapper wrapper= new QueryWrapper();
		if(approveVO!=null&&approveVO.getHistoryId()!=null){
			wrapper.eq("id",approveVO.getHistoryId());

		}else{
			wrapper.eq("current_node_id",approveVO.getNodeId());
			wrapper.eq("process_id",approveVO.getProcessId());
		}




		if(approveVO.getNodeType().equals(START.getValue())){
			return approveHistoryStartService.list(wrapper);
		}else if(approveVO.getNodeType().equals(GATEWAY.getValue())){
			return approveHistoryStartService.list(wrapper);
		}else if(approveVO.getNodeType().equals(APPROVE.getValue())){
			return approveHistoryStartService.list(wrapper);
		}else if(approveVO.getNodeType().equals(END.getValue())){
			return approveHistoryStartService.list(wrapper);
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
			return approveHistoryStartService.update(wrapper);
		}else if(nodeType.equals(APPROVE.getValue())){
			return approveHistoryStartService.update(wrapper);
		}else if(nodeType.equals(END.getValue())){
			return approveHistoryStartService.update(wrapper);
		}
		return false;
	}
}
