package com.hzl.hadoop.workflow.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审批路由节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveNodeGatewayVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Integer id;
	/**
	 * 节点编号
	 */
	private String nodeNum;
	/**
	 * 审批人
	 */
	private Long approverId;
	/**
	 * 审批组（全组同意，或者任意一人同意）
	 */
	private Long approverGroupId;
	/**
	 * 指定岗位编号
	 */
	private String positionNum;
	/**
	 * 上一节点（一对一，可以是网管，审批节点）
	 */
	private Long upNodeId;
	/**
	 * 上一节点类型（关联审批节点表的node_type）
	 */
	private Integer upNodeType;
	/**
	 * 监听器id
	 */
	private Long listenerId;
	/**
	 * 开始节点id
	 */
	private Long startId;
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
