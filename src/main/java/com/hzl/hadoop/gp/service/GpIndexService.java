package com.hzl.hadoop.gp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.gp.entity.GpIndexEntity;
import com.hzl.hadoop.gp.vo.GpIndexResultVO;

import java.time.LocalDate;

/**
 * 股票交易指标天维度，价格，成交量波动情况
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2023-04-07 21:45:04
 */
public interface GpIndexService extends IService<GpIndexEntity> {

	void initDate(String gpCode);

	void initIndexSpeed(String code);

	void initIndexAnalyse(String code);
	
	/**
	 * <p>
	 * 判断指定日期的第二天，股价涨跌的概率
	 * </p>
	 * 
	 * @author hzl 2023/04/12 12:41 PM
	 */
	GpIndexResultVO forecast(String gpCode, LocalDate time);

	void f(int[] shu, int targ, int has, int cur);
}

