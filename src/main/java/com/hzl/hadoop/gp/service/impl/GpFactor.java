package com.hzl.hadoop.gp.service.impl;

/**
 * description
 * 量化因子-按天分析
 * 计算出历史适合买入卖出点的所有量化因子，分析其共性（属于初步分析）
 * 通过组合量化因子预测，记录成功情况。在过滤出其共性，（处于通过系统进一步分析因子成功率，可通过历史数据模拟）
 * @author hzl 2023/04/06 1:16 PM
 */
public class GpFactor {

	//获取收盘价
	public void endPrice(){

	}

	//获取成交量/万手
	public void turnover(){

	}

	//获取成交额/亿元
	public void factor1(){

	}

	//获取当日均价
	public void factor2(){

	}


	//均价收盘价波动比例
	public void factor3(){

	}


	//当前价和历史均价对比，判断波动情况，判断涨幅情况（一周，2周，三周，四周，5周等时间数据）
	public void factor4(){

	}
}
