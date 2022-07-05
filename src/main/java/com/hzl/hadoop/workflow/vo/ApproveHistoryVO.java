package com.hzl.hadoop.workflow.vo;

import lombok.Data;

/**
 * description
 * 查询审批记录vo
 * @author hzl 2022/06/28 1:37 PM
 */
@Data
public class ApproveHistoryVO {

	/**
	* 提交人id
	* */
	private Long submitPerson;


	/**
	 * 审批人id
	 * */
	private Long approverId;
}
