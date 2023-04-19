package com.hzl.hadoop.gp.service.impl;

import com.hzl.hadoop.gp.service.GpAnalysisService;

/**
 * description
 *
 * @author hzl 2023/03/16 1:18 PM
 */
public class GpAnalysisServiceImpl implements GpAnalysisService {

	/**
	 * <p>
	 * 分析
	 * </p>
	 * 
	 * @author hzl 2023/03/16 1:18 PM
	 */
	@Override
	public void analysis() {
		
	}

	/**
	 * <p>
	 * 预测买入后会涨（也就是预测估计会涨）
	 * todo 爬取历史数据，不断进行测试调整
	 * todo 整理所有转折点数据，进行分析归纳（周末做）
	 * 集成redis进行统计分析
	 * </p>
	 *
	 * @author hzl 2023/03/16 1:33 PM
	 */
	@Override
	public void analysisBuy() {
		//买入条件集合
		//大盘涨，设置比重

		//估计连续几天涨

		//收盘价，均价差值百分比降低

		//收盘价，均价差值百分比达到

		//成交量和昨日的百分比变大

		//股价上涨(和昨日收盘价格比)，成交降低。大家都不卖

		//多个时间窗口内，股价呈上涨趋势

		//股价上涨，成交下降

		//todo 平均成交，当前成交和平均成交的差/平均成交量 计算成交的波动情况

		//成立的条件集合
	}

	/**
	 * <p>
	 * 预测卖出后会跌（也就是预测估计会跌）
	 * </p>
	 *
	 * @author hzl 2023/03/16 1:33 PM
	 */
	@Override
	public void analysisSale() {

	}
}
