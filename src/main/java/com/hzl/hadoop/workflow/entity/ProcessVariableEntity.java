package com.hzl.hadoop.workflow.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 流程变量-启动流程的时候初始化，个流程节点可以通过流程id查询变量
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("process_variable")
public class ProcessVariableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 流程id
	 */
	private Long processId;
	/**
	 * 流程变量
	 */
	private String variable;
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
