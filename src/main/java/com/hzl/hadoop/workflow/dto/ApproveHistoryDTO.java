package com.hzl.hadoop.workflow.dto;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/06/28 1:41 PM
 */
@Data
public class ApproveHistoryDTO {

	/**
	 * 审批人
	 */
	private Long approverId;


	/**
	 * 审批历史id
	 */
	private Long historyId;

	/**
	 * 上一节点审批人数组
	 */
	private List<Long> lastApproverIds;



	/**
	 * 上一节点审批人数组
	 */
	private List<String> lastApproverNames;

	/**
	 * 节点id
	 */
	private Long nodeId;

	/**
	 * 节点类型
	 */
	private Integer nodeType;

	/**
	 * 流程id
	 */
	private Long processId;

	/**
	 * 流程编号
	 */
	private String flowNum;

}
