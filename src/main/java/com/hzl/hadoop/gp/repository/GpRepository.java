package com.hzl.hadoop.gp.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.gp.vo.YlVO;
import com.hzl.hadoop.gp.vo.ZXVO;

import java.time.LocalDate;
import java.util.List;

/**
 * description
 *
 * @author hzl 2020/10/31 5:16 PM
 */
public interface GpRepository  {
	int insert(YlVO ylVO);

	int insert(ZXVO zxvo);

	/**
	 * 获取今日最大价格波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<GpVO> selectMaxPriceVolatility(String gpCode,LocalDate date);

	/**
	 * 获取今日最小价格波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<GpVO> selectMinPriceVolatility(String gpCode,LocalDate date);

	/**
	 * 获取当前股票价格
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<GpVO> selectCurrentPriceAll(String gpCode);

	/**
	 * 收盘成交价波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<VolumeVO> queryVolume(VolumeVO volumeVO);

	/**
	 * 当日成交价波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<VolumeVO> queryVolumeByDate(VolumeVO volumeVO);


	PageInfo<VolumeVO> queryVolumePage(VolumeVO volumeVO);



	/**
	 * 查询指定日期的收盘价
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	GpVO selectEndPriceByDate(String gpCode,LocalDate localDate);

}
