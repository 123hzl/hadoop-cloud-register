package com.hzl.hadoop.workflow.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 开始审批历史
 * 仅仅做启动标识
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveHistoryStartVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 流程记录id
	 */
	private Long processId;
	/**
	 * 开始节点id
	 */
	private Long currentNodeId;
	/**
	 * 审批人
	 */
	private String approverNum;
	/**
	 * 审批动作，1同意，2拒绝，3跳过，4转交
	 */
	private Integer approveAction;
	/**
	 * 租户id
	 */
	private Long tenantId;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 最后更新人
	 */
	private Long updateBy;
	/**
	 * 最后更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 版本号
	 */
	private Integer versionNum;

}
