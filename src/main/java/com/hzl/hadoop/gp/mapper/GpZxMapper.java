package com.hzl.hadoop.gp.mapper;

import com.hzl.hadoop.config.mybatis.BaseMapperUtil;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.gp.entity.YlVO;
import com.hzl.hadoop.gp.entity.ZXVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * description
 *
 * @author hzl 2020/11/03 12:32 PM
 */
@Mapper
public interface GpZxMapper extends BaseMapperUtil<ZXVO> {
	/**
	 * 获取今日最大价格波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<ZXVO> selectMaxPriceVolatility(@Param("gpCode")String gpCode,@Param("date")LocalDate date);

	/**
	 * 获取今日最小价格波动
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	List<ZXVO> selectMinPriceVolatility(@Param("gpCode")String gpCode,@Param("date") LocalDate date);

	List<LinkedHashMap<String,Object>> executeSql(String sql);

	/**
	 * 获取今日价格
	 *
	 * @return
	 * @author hzl 2020-11-04 9:45 AM
	 */
	@Select("select * from  gp_zx zx where zx.created_date>CURRENT_DATE order by zx.id desc limit 1")
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
