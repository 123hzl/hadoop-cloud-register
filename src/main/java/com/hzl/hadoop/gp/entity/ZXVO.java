package com.hzl.hadoop.gp.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * description
 *
 * @author hzl 2020/10/31 4:39 PM
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "gp_zx")
public class ZXVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//股票名称
	private String gpName;
	//开盘价
	private BigDecimal initPrice;
	//当前价
	private BigDecimal currentPrice;
	//最高价
	private BigDecimal maxPrice;
	//最低价
	private BigDecimal minPirce;
	//成交额
	private BigDecimal turnover;
	//成交数
	private BigDecimal number;
	//创建时间
	private LocalDateTime createdDate;
	//昨日收盘价
	private BigDecimal yesterdayEndPrice;
	//竞卖价，即卖一报价
	private BigDecimal auction;
	//竞买价，即买一报价
	private BigDecimal biddingPrice;
	//股票编号
	private String gpCode;
}
