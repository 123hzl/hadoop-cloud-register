package com.hzl.hadoop.workflow.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.workflow.constant.NodeType;
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

	public Object cloneObject(NodeType nodeType){
		switch (nodeType) {
			case START:
				return  JsonUtils.cloneObject(this,ApproveHistoryStartEntity.class);
			case GATEWAY:
				return  JsonUtils.cloneObject(this,ApproveHistoryGatewayEntity.class);
			case APPROVE:
				return  JsonUtils.cloneObject(this,ApproveHistoryApproverEntity.class);
			case END:
				return  JsonUtils.cloneObject(this,ApproveHistoryEndEntity.class);
			default:
				break;
		}
		return null;
	}
}
