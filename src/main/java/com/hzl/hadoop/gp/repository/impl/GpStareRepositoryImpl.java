package com.hzl.hadoop.gp.repository.impl;

import com.hzl.hadoop.gp.mapper.GpStareMapper;
import com.hzl.hadoop.gp.repository.GpStareRepository;
import com.hzl.hadoop.gp.vo.VolumeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * description
 *
 * @author hzl 2022/01/13 2:15 PM
 */
@Component
public class GpStareRepositoryImpl implements GpStareRepository {

	@Autowired
	GpStareMapper gpStareMapper;


	@Override
	public List<VolumeVO> getHistoryAverage(String gpCode, Boolean isPositive) {
		return gpStareMapper.queryPercent(gpCode, isPositive);
	}

	@Override
	public VolumeVO queryCurrentPercent(String gpCode) {
		return gpStareMapper.queryCurrentPercent(gpCode);
	}

	@Override
	public  List<VolumeVO> gpPriceCount(String gpCode) {
		return  gpStareMapper.gpPriceCount(gpCode);
	}

	@Override
	public List<VolumeVO> gpPriceCountByPrice(String gpCode, BigDecimal currentPrice) {
		return gpStareMapper.gpPriceCountByPrice(gpCode,currentPrice);
	}


}
