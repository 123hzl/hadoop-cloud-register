package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;

/**
 * description
 * 审批记录处理类
 *
 * @author hzl 2022/06/16 1:43 PM
 */
public interface ApproveHistoryHandle {

	void saveHistory(NodeType nodeType, ApproveHistoryEntity hostory);
}
