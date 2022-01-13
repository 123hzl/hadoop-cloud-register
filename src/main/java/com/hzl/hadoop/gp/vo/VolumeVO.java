package com.hzl.hadoop.gp.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * description
 * 成交量走势图
 *
 * @author hzl 2020/11/06 4:10 PM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolumeVO {
	//股票编号
	private String gpCode;
	//日期
	private String date;
	//成交额
	private BigDecimal turnover;
	//成交量
	private Long number;
	//收盘价
	private BigDecimal currentPrice;

	//开始时间
	private LocalDate startDate;

	//结束时间
	private LocalDate endDate;

	//查询指定日期的的波动情况
	private Boolean isSureDate;
}
