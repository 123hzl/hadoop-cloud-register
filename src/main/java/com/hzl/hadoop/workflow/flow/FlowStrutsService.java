package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeEndService;
import com.hzl.hadoop.workflow.service.ApproveNodeStartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/06/15 4:59 PM
 */

public class FlowStrutsService {

	@Autowired
	ApproveNodeStartService approveNodeStartService;

	@Autowired
	ApproveNodeEndService approveNodeEndService;

	public void queryFLowStrut(String flowNum){

		//查询结束节点
		List<ApproveNodeEndEntity> approveNodeEndEntity=approveNodeEndService.selectByFlowNum(flowNum);

		//查询审批节点

		//查询路由

		//查询开始节点

	}

}
