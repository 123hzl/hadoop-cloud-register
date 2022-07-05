package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;
import com.hzl.hadoop.workflow.vo.ApproveHistory;
import com.hzl.hadoop.workflow.vo.ApproveVO;

import java.util.List;

/**
 * description
 * 审批记录处理类
 *
 * @author hzl 2022/06/16 1:43 PM
 */
public interface ApproveHistoryHandle {

	void saveHistory(NodeType nodeType, ApproveHistoryEntity hostory);

	List<ApproveHistoryEntity> queryHistory(ApproveVO approveVO);

	Boolean updateHistory(Long historyId, Integer approveStatus,Integer nodeType);

}
