package com.hzl.hadoop.workflow.constant;

/**
 * description
 * 审批动作常量
 *
 * @author hzl 2021/11/04 7:27 PM
 */
public enum ApproveActionConstant {


	//待审批
	WAIT(0),

	//同意
	AGREE(1),

	//拒绝
	REJECT(2),

	//跳过
	SKIP(3),

	//转交
	TURN_ORVER(4);

	private final Integer value;

	ApproveActionConstant(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}



