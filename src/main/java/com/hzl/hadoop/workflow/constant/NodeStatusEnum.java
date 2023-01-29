package com.hzl.hadoop.workflow.constant;

/**
 * description
 * 节点类型
 *
 * @author hzl 2021/11/04 7:32 PM
 */
public enum NodeStatusEnum {

	WAIT(0,"待处理"),

	END(1,"已结束");

	private final Integer value;

	private final String dec;

	NodeStatusEnum(Integer value, String dec) {
		this.value = value;
		this.dec = dec;
	}

	public Integer getValue() {
		return value;
	}

	public String getDec() {
		return dec;
	}


	public static NodeStatusEnum match(Integer value){

		for(NodeStatusEnum nodeType: NodeStatusEnum.values()){
	       if(value.equals(nodeType.getValue())){
	       	return nodeType;
		   }
		}
		return null;
	}
}
