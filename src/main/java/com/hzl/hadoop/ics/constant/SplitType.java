package com.hzl.hadoop.ics.constant;

/**
 * description
 *
 * @author hzl 2022/07/28 2:21 PM
 */
public enum SplitType {

	ANSJ("ANSJ",1);


	SplitType(String desc, Integer value) {
		this.desc = desc;
		this.value = value;
	}

	//股票编号
	private String desc;
	//股票名称
	private Integer value;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer value() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
