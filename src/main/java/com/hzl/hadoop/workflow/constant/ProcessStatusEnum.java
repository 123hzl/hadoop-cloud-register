package com.hzl.hadoop.workflow.constant;

/**
 * description
 *
 * @author hzl 2022/06/15 5:50 PM
 */
public enum ProcessStatusEnum {

	START(1, "启动"),

	PROCESSING(2, "审批中"),

	REJECT(3, "驳回"),

	REJECT_TO(4, "驳回指定节点"),

	APPROVED(5, "审批通过");

	private final Integer value;

	private final String dec;

	ProcessStatusEnum(Integer value, String dec) {
		this.value = value;
		this.dec = dec;
	}

	public Integer value() {
		return value;
	}

	public String getDec() {
		return dec;
	}
}
