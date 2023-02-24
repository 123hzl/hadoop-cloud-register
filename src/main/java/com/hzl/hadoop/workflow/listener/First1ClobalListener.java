package com.hzl.hadoop.workflow.listener;

import com.hzl.hadoop.workflow.dto.ListenerDTO;

/**
 * description
 *
 * @author hzl 2021/11/04 10:29 AM
 */
public class First1ClobalListener extends GlobalListener {

	@Override
	public ListenerDTO approveListener(Long processId) {

		return null;
	}

	@Override
	public void businessListener(Long processId) {

	}


}
