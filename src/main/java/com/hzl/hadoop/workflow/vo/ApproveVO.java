package com.hzl.hadoop.workflow.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/07/05 2:58 PM
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApproveVO {

	/**
	 * 审批人
	 */
	private Long approverId;



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
	 * 审批历史id
	 */
	private Long historyId;


	/**
	 * 节点类型
	 */
	private Integer nodeType;

	/**
	 * 流程id
	 */
	private Long processId;

	/**
	 * 审批动作，0待审批，1同意，2拒绝，3跳过，4转交
	 */
	private Integer approveAction;

	/**
	 * 流程编号
	 */
	private String flowNum;
}
