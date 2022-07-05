package com.hzl.hadoop.workflow.constant;

/**
 * description
 * 节点类型
 *
 * @author hzl 2021/11/04 7:32 PM
 */
public enum NodeType {

	START(1,"开始"),

	GATEWAY(2,"网关"),

	APPROVE(3,"审批节点"),

	END(4,"结束");

	private final Integer value;

	private final String dec;

	NodeType(Integer value, String dec) {
		this.value = value;
		this.dec = dec;
	}

	public Integer getValue() {
		return value;
	}

	public String getDec() {
		return dec;
	}


	public static NodeType match(Integer value){

		for(NodeType nodeType:NodeType.values()){
	       if(value.equals(nodeType.getValue())){
	       	return nodeType;
		   }
		}
		return null;
	}
}
