package com.hzl.hadoop.gp.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * description
 *
 * @author hzl 2020/11/03 11:35 AM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpVO {

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
	private Long number;
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

	public void init(Map<String, String> data){
		//准备对象参数
		String gpName = data.get("股票名字");
		BigDecimal currentPrice = new BigDecimal(data.get("当前价格"));
		BigDecimal initPrice = new BigDecimal(data.get("今日开盘价"));
		BigDecimal maxPrice = new BigDecimal(data.get("今日最高价"));
		BigDecimal minPirce = new BigDecimal(data.get("今日最低价"));
		BigDecimal turnover = new BigDecimal(data.get("成交金额/元"));
		BigDecimal yesterdayEndPrice = new BigDecimal(data.get("昨日收盘价"));
		Long number = Long.valueOf(data.get("成交的股票数"));
		BigDecimal num = new BigDecimal("100000000");
		BigDecimal auction =  new BigDecimal(data.get("竞卖价，即卖一报价"));
		BigDecimal biddingPrice =  new BigDecimal(data.get("竞买价，即买一报价"));
		//生成对象
		this.gpName=gpName;
		this.currentPrice= currentPrice;
		this.initPrice = initPrice;
		this.maxPrice=maxPrice;
		this.minPirce = minPirce;
		this.turnover = turnover.divide(num);
		this.number = number / 100;
		this.createdDate = LocalDateTime.now();
		this.yesterdayEndPrice = yesterdayEndPrice;
		this.auction = auction;
		this.biddingPrice = biddingPrice;
	}
}
