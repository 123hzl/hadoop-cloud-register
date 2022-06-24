package com.hzl.hadoop.workflow;

import lombok.Data;

/**
 * description
 *
 * @author hzl 2022/06/24 2:40 PM
 */
@Data
public class NodeDTO {

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

	private Integer nodeType;
}
