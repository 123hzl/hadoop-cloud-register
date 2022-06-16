package com.hzl.hadoop.workflow.convert;

import com.hzl.hadoop.workflow.entity.ApproveNodeApproverEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeGatewayEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;

/**
 * description
 * CharVO 转换成开始节点,网关节点,审批节点,结束节点
 * T为需要转换的类
 *
 * @author hzl 2021/11/24 4:59 PM
 */
public interface WorkflowCharConver<T> {

	/**
	 * <p>
	 * 转换成开始节点
	 * </p>
	 *
	 * @author hzl 2021/11/24 5:09 PM
	 */
	ApproveNodeStartEntity converToStartNode(T t);

	/**
	 * <p>
	 * 网关节点
	 * </p>
	 *
	 * @author hzl 2021/11/24 5:09 PM
	 */
	ApproveNodeGatewayEntity converToGateWayNode(T t);

	/**
	 * <p>
	 * 审批节点
	 * </p>
	 *
	 * @author hzl 2021/11/24 5:09 PM
	 */
	ApproveNodeApproverEntity converToApproveNode(T t);

	/**
	 * <p>
	 * 结束节点
	 * </p>
	 *
	 * @author hzl 2021/11/24 5:09 PM
	 */
	ApproveNodeEndEntity converToEndNode(T t);

}
