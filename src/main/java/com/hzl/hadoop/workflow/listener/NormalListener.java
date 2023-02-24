package com.hzl.hadoop.workflow.listener;

import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.dto.ListenerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * description ，监听一定要注入到spring中
 * 非全局监听
 * 后置监听approveListener直接返回null，不要加逻辑
 * @author hzl 2022/06/24 1:30 PM
 */
public abstract class NormalListener {


	/**
	 *
	 * 审批监听，用于执行审批逻辑，如果配置了该监听，节点上配置的审批人，审批组，审批岗位将不起作用
	 *  todo 目前审批人，审批组，审批岗位仍然起作用
	 * @param processId 流程id
	 * @author hzl 2021-11-04 9:58 AM
	 * @return
	 */

	public abstract ListenerDTO approveListener(Long processId);

	/**
	 *
	 * 业务监听，类似切片，比如审批前执行自定义日志输出等逻辑
	 * @param processId 流程id
	 * @author hzl 2021-11-04 9:58 AM
	 * @return
	 */

	public abstract void businessListener(Long processId);

	/**
	 *
	 * 监听功能
	 * @param processId 流程id
	 * @author hzl 2021-11-04 9:58 AM
	 * @return
	 */

	/**
	 *
	 * 监听功能
	 * @param processId 流程id
	 * @author hzl 2021-11-04 9:58 AM
	 * @return
	 */

	public ListenerDTO listener(Long processId){

		ListenerDTO listenerDTO=this.approveListener(processId);
		//业务监听不需要返回值
		this.businessListener(processId);

		return listenerDTO;
	}

}
