package com.hzl.hadoop.gp.mapper;

import com.hzl.hadoop.config.mybatis.BaseMapperUtil;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.gp.entity.YlVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * description
 *
 * @author hzl 2020/11/03 12:32 PM
 */
@Mapper
public interface GpYlMapper extends BaseMapperUtil<YlVO> {
	/**
	 * 获取今日最大价格波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<YlVO> selectMaxPriceVolatility(@Param("date") LocalDate date);

	/**
	 * 获取今日最小价格波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<YlVO> selectMinPriceVolatility(@Param("date") LocalDate date);


	/**
	 * 获取今日价格
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	@Select("select * from  gp_yl gy where gy.created_date>CURRENT_DATE order by gy.id desc limit 1")
	List<YlVO> selectCurrentPrice();



	/**
	 * 获取当天历史价
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	@Select("select * from  gp_yl gy where gy.created_date>CURRENT_DATE order by id asc")
	List<GpVO> selectCurrentPriceAll();

	/**
	 * 获取指定日期的收盘价格
	 *
	 * @author hzl 2022-01-13 10:25 AM
	 * @return
	 */
	GpVO selectEndPriceByDate(@Param("gpCode") String gpCode, @Param("date") LocalDate date);

}
