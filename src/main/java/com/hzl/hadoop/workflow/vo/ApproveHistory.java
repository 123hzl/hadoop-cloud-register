package com.hzl.hadoop.workflow.vo;

import lombok.Data;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/07/05 2:35 PM
 */
@Data
public class ApproveHistory {

	private ApproveHistoryStartVO approveHistoryStartVO;

	private List<ApproveHistoryGatewayVO> approveHistoryGatewayVOS;

	private List<ApproveHistoryApproverVO> approveHistoryApproverVOS;

	private ApproveHistoryEndVO approveHistoryEndVO;
}
