package com.hzl.hadoop.gp.service;

import java.math.BigDecimal;

//股票交易最优解
public interface GpTradingService {
	//计算单比买入卖出利润
	 BigDecimal calculateProfit(BigDecimal buyingPrice, BigDecimal buyingNumber,BigDecimal sellingPrice, BigDecimal sellingNumber);


	//最优买入卖出股票
	 void trading();
}
