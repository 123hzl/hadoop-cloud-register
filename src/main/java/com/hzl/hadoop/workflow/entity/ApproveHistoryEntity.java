package com.hzl.hadoop.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import lombok.Data;

/**
 * description
 *
 * @author hzl 2022/06/23 5:36 PM
 */
@Data
public class ApproveHistoryEntity extends BaseEntity {

	/**
	 *
	 */
	private Long id;
	/**
	 * 流程记录id
	 */
	private Long processId;
	/**
	 * 结束节点id
	 */
	private Long currentNodeId;
	/**
	 * 审批人
	 */
	private Long approverId;
	/**
	 * 审批动作，1同意，2拒绝，3跳过，4转交
	 */
	private Integer approveAction;

	/**
	 * 节点状态，0待处理，1已结束
	 */
	private Integer nodeStatus;

}
