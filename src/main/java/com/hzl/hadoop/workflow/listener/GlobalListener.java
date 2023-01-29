package com.hzl.hadoop.workflow.listener;

/**
 * description
 * 全局监听，实现自定义全局监听需要实现改抽象类，监听一定要注入到spring中
 * @author hzl 2021/11/04 9:21 AM
 */
public abstract class GlobalListener {


	/**
	 *
	 * 审批监听，用于执行审批逻辑，如果配置了该监听作为前置监听，节点上配置的审批人，审批组，审批岗位将不起作用，审批逻辑由监听类完成
	 * todo 目前审批人，审批组，审批岗位仍然起作用该功能暂时未实现
	 * @param processId 流程id
	 * @author hzl 2021-11-04 9:58 AM
	 * @return
	 */

	public abstract void approveListener(Long processId);

	/**
	 *
	 * 业务监听，类似切片，比如审批前执行自定义日志输出等逻辑，执行发送消息通知的功能，例如审批完成发消息给用户
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

	public void listener(Long processId){
		this.approveListener(processId);
		this.businessListener(processId);
	};

}
