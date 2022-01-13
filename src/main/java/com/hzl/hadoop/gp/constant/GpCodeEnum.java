package com.hzl.hadoop.gp.constant;

/**
 * description
 *
 * @author hzl 2021/12/15 1:26 PM
 */
public enum GpCodeEnum {

	sh600887("sh600887","伊利"),
	sz000063("sz000063","中兴"),
	sh600690("sh600690","海尔"),
	sh600597("sh600597","光明"),
	sz000651("sz000651","格力"),
	sz000333("sz000333","美的")
	;



	GpCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	//股票编号
	private String code;
	//股票名称
	private String name;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
