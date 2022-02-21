package com.hzl.hadoop.gp.repository;

import com.hzl.hadoop.gp.vo.VolumeVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * description
 *
 * @author hzl 2022/01/13 11:06 AM
 */
public interface GpStareRepository {

	/**
	 * 获取历史均价信息,30天内的数据
	 *
	 * @param gpCode     股票编号
	 * @param isPositive 是否取正数
	 * @return
	 * @author hzl 2022-01-13 11:07 AM
	 */
	List<VolumeVO> getHistoryAverage(String gpCode, Boolean isPositive);

	/**
	 * 查询时时均价
	 *
	 * @param gpCode
	 * @return
	 * @author hzl 2022-01-13 11:07 AM
	 */
	VolumeVO queryCurrentPercent(String gpCode);


	/**
	 * 获取盈利均价最高的数据
	 *
	 * @param null
	 * @author hzl 2022-01-13 11:07 AM
	 * @return
	 */


	/**
	 * 获取盈利均价平均值，时间纬度
	 *
	 * @param null
	 * @author hzl 2022-01-13 11:07 AM
	 * @return
	 */


	/**
	 * 获取亏损均价平均值，时间纬度
	 *
	 * @param null
	 * @author hzl 2022-01-13 11:07 AM
	 * @return
	 */

	/**
	 * 获取指定日期的均价，包括当前均价
	 *
	 * @param null
	 * @author hzl 2022-01-13 11:07 AM
	 * @return
	 */


	/**
	 * 均价异常波动提醒，1分钟执行一次，和上一分钟的比较 ，当前均价和最高值比较，最近五日最高价
	 *
	 * @param null
	 * @author hzl 2022-01-13 11:07 AM
	 * @return
	 */

	/**
	 * 股价买入量计算-通过该数据计算出建议买入价格
	 *
	 * @param gpCode 股票编号
	 * @author hzl 2022-01-19 1:33 PM
	 * @return
	 */

	 List<VolumeVO> gpPriceCount(String gpCode);

	 List<VolumeVO> gpPriceCountByPrice(String gpCode, BigDecimal currentPrice);


}
