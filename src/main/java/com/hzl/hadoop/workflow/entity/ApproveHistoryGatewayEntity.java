package com.hzl.hadoop.workflow.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 网关审批历史
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-04 14:56:40
 */
@Data
@TableName("approve_history_gateway")
public class ApproveHistoryGatewayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 流程记录id
	 */
	private Long processId;
	/**
	 * 当前节点id
	 */
	private Long currentNodeId;
	/**
	 * 当前节点类型
	 */
	private Integer currentNodeType;
	/**
	 * 下一节点id
	 */
	private Long nextNodeId;
	/**
	 * 下一节点类型
	 */
	private Integer nextNodeType;
	/**
	 * 审批人
	 */
	private String approverNum;
	/**
	 * 审批动作，1同意，2拒绝，3跳过，4转交
	 */
	private Long approveAction;
	/**
	 * 租户id
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long tenantId;
	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createBy;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 最后更新人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateBy;
	/**
	 * 最后更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
	/**
	 * 版本号
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer versionNum;

}
