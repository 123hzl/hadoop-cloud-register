package com.hzl.hadoop.gp.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * description
 * 股票自动盯盘程序
 * @author hzl 2022/01/13 11:03 AM
 */
public interface GpStareService {
	
	/**
	 *
	 * 提醒买入卖出
	 * @param null
	 * @author hzl 2022-01-13 11:06 AM
	 * @return 
	 */

	Boolean notifyBuyAndSale(String gpCode);

	/**
	 * 建议购买价格
	 *
	 * @param null
	 * @author hzl 2022-01-19 1:39 PM
	 * @return
	 */
	Map<String,BigDecimal> notifyBuePrice(String gpCode);
}
