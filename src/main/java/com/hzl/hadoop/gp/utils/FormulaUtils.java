package com.hzl.hadoop.gp.utils;

import com.hzl.hadoop.gp.constant.GpNumberConstant;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * description
 * 计算公式
 * @author hzl 2023/04/07 5:28 PM
 */
public class FormulaUtils {

	//计算均价(成交额(亿)/成交量(手)
	public static BigDecimal avg(BigDecimal turnover,BigDecimal number){
		//转化成元
		turnover=turnover.multiply(GpNumberConstant.oneHundredMillion);
		//转化成股
		number=number.multiply(GpNumberConstant.oneHundred);
		return turnover.divide(number,3, RoundingMode.FLOOR);
	}
}
