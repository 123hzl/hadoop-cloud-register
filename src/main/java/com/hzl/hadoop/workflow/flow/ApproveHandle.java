package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.workflow.entity.ApproveNodeAbstract;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.vo.NodeContainer;
import com.hzl.hadoop.workflow.vo.ProcessVariableVO;

/**
 * description
 * 根据节点配置，进行审批处理
 * @author hzl 2022/06/16 10:35 AM
 */
public interface ApproveHandle {

	Boolean beforeApprove(Long processId,Long nodeId,Integer nodeType);

	Boolean approve(Long processId,ApproveNodeAbstract startEntity);

	Boolean afterApprove(Long processId,Long nodeId,Integer nodeType);

}
