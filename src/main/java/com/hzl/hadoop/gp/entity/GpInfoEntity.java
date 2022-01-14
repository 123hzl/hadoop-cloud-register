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
import java.time.LocalDateTime;


/**
 * 股票信息列表-存储所有需要爬取的股票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-14 16:50:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("gp_info")
public class GpInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 股票名称
	 */
	private String gpName;
	/**
	 * 股票名称
	 */
	private String gpCode;
	/**
	 * 是否爬取
	 */
	private Boolean isCreep;
	/**
	 * 是否进行买入卖出通知
	 */
	private Boolean isNotify;
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
