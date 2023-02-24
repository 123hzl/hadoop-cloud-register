package com.hzl.hadoop.workflow.dto;

import lombok.Data;
import lombok.ToString;

/**
 * description
 * 监听结果返回
 * @author hzl 2023/02/10 3:49 PM
 */
@ToString
@Data
public class ListenerDTO {

	/*
	* 1,直接返回不执行后面的逻辑，包括后面的审批逻辑
	* 2,标示执行成功，继续后面的操作
	* 3，异常
	* */
	Integer status;

	/**
	 *返回内容
	 */
	String content;

	public ListenerDTO next(){
		this.setStatus(2);
		return this;
	}

	public ListenerDTO end(){
		 this.setStatus(1);
		 return this;
	}

	public ListenerDTO fail(String error){
		this.setStatus(3);
		this.setContent(error);
		return this;
	}
	public ListenerDTO fail(){
		this.setStatus(3);
		return this;
	}


}
