package com.hzl.hadoop.gp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * 股票交易指标天维度，价格，成交量波动情况
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2023-04-07 21:45:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("gp_index")
public class GpIndexEntity extends BaseEntity implements Serializable {
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
	 * 价格归属日期
	 */
	private LocalDate priceDate;
	/**
	 * 股票名称
	 */
	private String gpCode;
	/**
	 * 当日收盘价格
	 */
	private BigDecimal endPrice;
	/**
	 * 当日均价
	 */
	private BigDecimal avgPrice;
	/**
	 * (收盘价格-当日均价)/当日均价
	 */
	private BigDecimal dailyPercent;
	/**
	 * (今日均价-昨日均价)/昨日均价
	 */
	private BigDecimal daily1Percent;
	private BigDecimal daily2Percent;
	private BigDecimal daily4Percent;
	private BigDecimal daily6Percent;
	/**
	 * (今日成交量-昨日成交量)/昨日成交量
	 */
	private BigDecimal turnoverPercent;
	/**
	 * (今日收盘价-昨日收盘价)/昨日收盘价
	 */
	private BigDecimal overPercent;

	private BigDecimal over2Percent;

	private BigDecimal over4Percent;

	private BigDecimal over6Percent;


	/**
	 * (今日收盘价-昨日均价)/昨日均价
	 */
	private BigDecimal overPercent1;

	private BigDecimal overPercent2;

	private BigDecimal overPercent4;

	private BigDecimal overPercent6;


	/**
	 * (收盘价格-当日均价)/当日均价增速
	 */
	private BigDecimal dailyPercentSpeed;
	/**
	 * (今日均价-昨日均价)/昨日均价增速
	 */
	private BigDecimal daily1PercentSpeed;

//	private BigDecimal daily2PercentSpeed;
//
//	private BigDecimal daily4PercentSpeed;
//
//	private BigDecimal daily6PercentSpeed;
	/**
	 * (今日成交量-昨日成交量)/昨日成交量增速
	 */
	private BigDecimal turnoverPercentSpeed;
	/**
	 * (今日收盘价-昨日收盘价)/昨日收盘价增速
	 */
	private BigDecimal overPercentSpeed;

	/**
	 * (今日收盘价-昨日均价)/昨日均价
	 */
	private BigDecimal overPercentSpeed1;
	/**
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
