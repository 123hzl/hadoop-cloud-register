package com.hzl.hadoop.workflow.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * description
 *
 * @author hzl 2022/06/24 5:44 PM
 */
@Data
public class ProcessHistoryPageDTO {

	/**
	 * 流程id
	 */
	private Long processId;

	/**
	 * 提交人
	 */
	private String submitPerson;

	/**
	 * 流程状态
	 */
	private Integer processStatus;

	/**
	 * 审批类型
	 */
	private String descr;

	/**
	 * 流程编号
	 */
	private String flowNum;


	/**
	 * 启动节点id
	 */
	private Long startId;

	/**
	 * 流程启动时间
	 */
	private LocalDateTime createTime;
}
