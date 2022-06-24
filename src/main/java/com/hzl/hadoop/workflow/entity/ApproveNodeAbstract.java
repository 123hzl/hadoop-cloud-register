package com.hzl.hadoop.workflow.entity;

import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * description
 * 供节点类继承
 * @author hzl 2022/06/23 5:02 PM
 */
@Data
public abstract class ApproveNodeAbstract {

	private Long id;
	/**
	 * 审批人
	 */
	private Long approverId;
	/**
	 * 审批组（全组同意，或者任意一人同意）
	 */
	private Long approverGroupId;
	/**
	 * 指定岗位编号
	 */
	private Long positionId;
	/**
	 * 后置监听器id
	 */
	private Long afListenerId;
	/**
	 * 前置监听器id
	 */
	private Long beListenerId;

}
