package com.hzl.hadoop.workflow.listener;

import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author hzl 2021/11/04 10:29 AM
 */
@Component
public class FirstClobalListener extends GlobalListener {

	@Override
	public void approveListener(Long processId) {
		System.out.println("触发审批监听"+processId);
	}

	@Override
	public void businessListener(Long processId) {
		System.out.println("触发业务监听"+processId);
	}

}
