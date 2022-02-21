package com.hzl.hadoop.gp.service.impl;

import com.hzl.hadoop.gp.constant.GpUrlConstant;
import com.hzl.hadoop.gp.convert.GpConvert;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.repository.GpStareRepository;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.vo.*;
import com.hzl.hadoop.util.JsonUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author hzl 2020/10/31 5:09 PM
 */
@Service
public class GpServiceImpl implements GpService {

	GpRepository gpRepository;

	GpStareRepository gpStareRepository;

	public GpServiceImpl(GpRepository gpRepository, GpStareRepository gpStareRepository) {
		this.gpRepository = gpRepository;
		this.gpStareRepository = gpStareRepository;
	}


	@Override
	public GpVO insert(String code) {

		GpConvert gpConvert = new GpConvert();
		//通过url获取转化后的数据
		Map<String, String> data = gpConvert.getGpInfo(GpUrlConstant.GP_BASE_URL.concat(code), null);
		//生成对象
		GpVO gpVO = new GpVO();
		//初始化对象参数
		gpVO.init(data);
		gpVO.setGpCode(code);

		if (GpUrlConstant.GP_CODE_YL.equals(code)) {
			//伊利股票,利用对象克隆
			gpRepository.insert(JsonUtils.cloneObject(gpVO, YlVO.class));

		} else if (GpUrlConstant.GP_CODE_ZX.equals(code)) {
			//中兴股票,利用对象克隆
			gpRepository.insert(JsonUtils.cloneObject(gpVO, ZXVO.class));
		} else {
			gpRepository.insert(JsonUtils.cloneObject(gpVO, ZXVO.class));
		}

		return gpVO;

	}

	@Override
	public MaxMinHtmlVO queryVolume(VolumeVO volumeVO) {

		List<VolumeVO> volumeVOS;
		if (volumeVO.getIsSureDate() != null && volumeVO.getIsSureDate()) {
			volumeVOS = gpRepository.queryVolumeByDate(volumeVO);
			List<EndPriceVO> endPrice = volumeVOS.stream().map(a -> EndPriceVO.builder().series("收盘价/元").x(a.getDate()).y(a.getCurrentPrice().doubleValue()).build()).collect(Collectors.toList());
			//List<EndPriceVO> number = volumeVOS.stream().map(a -> EndPriceVO.builder().series("成交额/万手").x(a.getDate()).y(Double.valueOf(a.getNumber() / 10000)).build()).collect(Collectors.toList());
			//List<EndPriceVO> turnover = volumeVOS.stream().map(a -> EndPriceVO.builder().series("成交额/亿元").x(a.getDate()).y(a.getTurnover().doubleValue()).build()).collect(Collectors.toList());
			List<EndPriceVO> forecast = volumeVOS.stream().map(a -> EndPriceVO.builder().series("当日均价/元").x(a.getDate()).y(((a.getTurnover().doubleValue() * 1000000) / a.getNumber())).build()).collect(Collectors.toList());
			List<EndPriceVO> forecastPercent = volumeVOS.stream().map(a -> EndPriceVO.builder().series("(当日均价-收盘价格)/收盘价格(万分之一)").x(a.getDate()).y(((((a.getTurnover().doubleValue() * 1000000) / a.getNumber()) - a.getCurrentPrice().doubleValue()) / (a.getCurrentPrice().doubleValue())) * 10000).build()).collect(Collectors.toList());

			List<EndPriceVO> all = new ArrayList<>();
			all.addAll(endPrice);

//			all.addAll(number);
//			all.addAll(turnover);
			all.addAll(forecast);
			all.addAll(forecastPercent);
//			all.sort(Comparator.comparing(EndPriceVO::getX));
			MaxMinHtmlVO maxMinHtmlVO = MaxMinHtmlVO.builder().data(JsonUtils.objectToString(all)).build();
			return maxMinHtmlVO;
		} else {
			volumeVOS = gpRepository.queryVolume(volumeVO);
			List<EndPriceVO> endPrice = volumeVOS.stream().map(a -> EndPriceVO.builder().series("收盘价/元").x(a.getDate()).y(a.getCurrentPrice().doubleValue()).build()).collect(Collectors.toList());
			List<EndPriceVO> number = volumeVOS.stream().map(a -> EndPriceVO.builder().series("成交额/万手").x(a.getDate()).y(Double.valueOf(a.getNumber() / 10000)).build()).collect(Collectors.toList());
			List<EndPriceVO> turnover = volumeVOS.stream().map(a -> EndPriceVO.builder().series("成交额/亿元").x(a.getDate()).y(a.getTurnover().doubleValue()).build()).collect(Collectors.toList());
			List<EndPriceVO> forecast = volumeVOS.stream().map(a -> EndPriceVO.builder().series("当日均价/元").x(a.getDate()).y(((a.getTurnover().doubleValue() * 1000000) / a.getNumber())).build()).collect(Collectors.toList());
			List<EndPriceVO> forecastPercent = volumeVOS.stream().map(a -> EndPriceVO.builder().series("(当日均价-收盘价格)/收盘价格(万分之一)").x(a.getDate()).y(((((a.getTurnover().doubleValue() * 1000000) / a.getNumber()) - a.getCurrentPrice().doubleValue()) / (a.getCurrentPrice().doubleValue())) * 10000).build()).collect(Collectors.toList());

			List<EndPriceVO> all = new ArrayList<>();
			all.addAll(endPrice);
			all.addAll(number);
			all.addAll(turnover);
			all.addAll(forecast);
			all.addAll(forecastPercent);
			all.sort(Comparator.comparing(EndPriceVO::getX));
			MaxMinHtmlVO maxMinHtmlVO = MaxMinHtmlVO.builder().data(JsonUtils.objectToString(all)).build();
			return maxMinHtmlVO;
		}


	}

	@Override
	public List<PercentVO> gpPriceCount(String gpCode, BigDecimal currentPrice) {
		List<VolumeVO> volumeVOS = currentPrice != null ? gpStareRepository.gpPriceCountByPrice(gpCode, currentPrice) : gpStareRepository.gpPriceCount(gpCode);

		BigDecimal sumTurnover = volumeVOS.stream().map(a -> a.getTurnover()).reduce(BigDecimal.ZERO, BigDecimal::add);

		List<PercentVO> percentVOS = volumeVOS.stream().map(a -> PercentVO.builder()
				.type(String.valueOf(a.getCurrentPrice()))
				.value((a.getTurnover().multiply(BigDecimal.valueOf(100))).divide(sumTurnover, 2, BigDecimal.ROUND_HALF_UP))
				.build()).collect(Collectors.toList());

		return percentVOS;
	}

}
