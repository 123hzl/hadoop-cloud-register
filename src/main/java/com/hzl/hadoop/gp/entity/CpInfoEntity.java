package com.hzl.hadoop.gp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 彩票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-26 16:04:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("cp_info")
public class CpInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 期号
	 */
	private String lotteryDrawNum;
	/**
	 * 号码1
	 */
	private Integer first;
	/**
	 * 2
	 */
	private Integer second;
	/**
	 * 3
	 */
	private Integer three;
	/**
	 * 4
	 */
	private Integer four;
	/**
	 * 5
	 */
	private Integer five;
	/**
	 * 6
	 */
	private Integer six;
	/**
	 * 7
	 */
	private Integer seven;
	/**
	 * 开奖日期
	 */
	private LocalDate lotteryDrawTime;
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
