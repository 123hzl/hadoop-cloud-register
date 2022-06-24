package com.hzl.hadoop.workflow.vo;

import lombok.Data;

import java.util.Map;

/**
 * description
 * 启动工作流参数
 * @author hzl 2022/06/15 4:48 PM
 */
@Data
public class StartWorkFlowVO {

	/**
	 * 流程图编号
	 */
	private String flowNum;

	/**
	 * 流程变量
	 */
	private Map<String,Object> processVariable;
}
