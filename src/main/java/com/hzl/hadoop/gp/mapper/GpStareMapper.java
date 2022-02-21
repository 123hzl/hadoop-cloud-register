package com.hzl.hadoop.gp.mapper;

import com.hzl.hadoop.gp.vo.VolumeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * description
 *
 * @author hzl 2022/01/13 2:11 PM
 */
@Mapper
public interface GpStareMapper {

	/**
	 * 查询每日亏损均价
	 * 根据亏损排序，降序
	 * isPositive 是否取正数
	 *
	 * @return
	 * @author hzl 2022-01-13 3:02 PM
	 */
	List<VolumeVO> queryPercent(@Param("gpCode") String gpCode, @Param("isPositive") Boolean isPositive);


	/**
	 * 查询当日时时均价
	 * 根据亏损排序，降序
	 * isPositive 是否取正数
	 *
	 * @return
	 * @author hzl 2022-01-13 3:02 PM
	 */
	VolumeVO queryCurrentPercent(@Param("gpCode") String gpCode);


	/**
	 * <p>
	 * 获取分价
	 * </p>
	 * 
	 * @author hzl 2022/01/21 2:20 PM
	 */
	List<VolumeVO> gpPriceCount(@Param("gpCode") String gpCode);

	/**
	 * <p>
	 * 获取分家，指定价格以下的算作其他，数量累积，价格用平均值
	 * </p>
	 *
	 * @author hzl 2022/01/21 2:20 PM
	 */
	List<VolumeVO> gpPriceCountByPrice(@Param("gpCode") String gpCode, @Param("currentPrice") BigDecimal currentPrice);

}
