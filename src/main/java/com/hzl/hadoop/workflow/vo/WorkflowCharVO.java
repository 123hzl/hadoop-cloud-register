package com.hzl.hadoop.workflow.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 前端生成的流程图，需要转换成开始节点，审批节点，网关节点，结束节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowCharVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 流程图json数据
	 */
	private String charData;
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
	/**
	 * 流程图名称
	 */
	private String descr;

}
