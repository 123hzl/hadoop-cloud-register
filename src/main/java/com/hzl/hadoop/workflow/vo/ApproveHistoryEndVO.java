package com.hzl.hadoop.workflow.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 结束节点审批历史，仅仅标记流程是否结束，不配置审批人，可以配置结束监听器
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveHistoryEndVO implements Serializable {
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
	 * 结束节点id
	 */
	private Long currentNodeId;
	/**
	 * 审批人
	 */
	private String approverNum;
	/**
	 * 监听器id
	 */
	private Long listenerId;
	/**
	 * 审批动作，0待审批,1同意，2拒绝，3跳过，4转交
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
