package com.hzl.hadoop.workflow.vo;

import lombok.*;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/07/05 2:58 PM
 */
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApproveVO {


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
	 * START(1,"开始"),

	 GATEWAY(2,"网关"),

	 APPROVE(3,"审批节点"),

	 END(4,"结束");

	 */
	private Integer nodeType;

	/**
	 * 节点状态
	 */
	private Integer nodeStatus;

	/**
	 * 流程id
	 */
	private Long processId;

	/**
	 * 审批动作，0待审批，1同意，2拒绝，3跳过，4转交
	 */
	private Integer approveAction;

}
