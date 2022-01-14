package com.hzl.hadoop.gp.service;

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
}
