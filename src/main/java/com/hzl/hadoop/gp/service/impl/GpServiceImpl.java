package com.hzl.hadoop.gp.service.impl;

import com.hzl.hadoop.gp.constant.GpNumberConstant;
import com.hzl.hadoop.gp.constant.GpUrlConstant;
import com.hzl.hadoop.gp.convert.GpConvert;
import com.hzl.hadoop.gp.entity.YlVO;
import com.hzl.hadoop.gp.entity.ZXVO;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.repository.GpStareRepository;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.utils.FormulaUtils;
import com.hzl.hadoop.gp.vo.*;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.util.LocalDateFormate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@Slf4j
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
			List<EndPriceVO> endPrice = volumeVOS.stream().map(a -> EndPriceVO.builder().series("收盘价/元").x(a.getDate()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());
			List<EndPriceVO> forecast = volumeVOS.stream().map(a -> EndPriceVO.builder().series("当日均价/元").x(a.getDate()).y(FormulaUtils.avg(a.getTurnover(), a.getNumber())).build()).collect(Collectors.toList());
			List<EndPriceVO> forecastPercent = volumeVOS.stream().map(a ->
			{
				//当日均价
				BigDecimal avg = FormulaUtils.avg(a.getTurnover(), a.getNumber());
				return EndPriceVO.builder().series("(当日均价-收盘价格)/收盘价格(万分之一)").x(a.getDate())
						.y((avg.subtract(a.getCurrentPrice()).multiply(GpNumberConstant.tenThousand)).divide(a.getCurrentPrice(), 5, RoundingMode.FLOOR)).build();
			}).collect(Collectors.toList());
			List<EndPriceVO> forecastPercent1 = volumeVOS.stream().map(a -> {
				//当日均价
				BigDecimal avg = FormulaUtils.avg(a.getTurnover(), a.getNumber());
				return EndPriceVO.builder().series("(收盘价格-当日均价)/当日均价(万分之一)").x(a.getDate())
						.y((a.getCurrentPrice().subtract(avg).multiply(GpNumberConstant.tenThousand)).divide(avg, 5, RoundingMode.FLOOR))
						.build();
			}).collect(Collectors.toList());


			List<EndPriceVO> all = new ArrayList<>();
			all.addAll(endPrice);

//			all.addAll(number);
//			all.addAll(turnover);
			all.addAll(forecast);
			all.addAll(forecastPercent);
			all.addAll(forecastPercent1);
//			all.sort(Comparator.comparing(EndPriceVO::getX));
			MaxMinHtmlVO maxMinHtmlVO = MaxMinHtmlVO.builder().data(JsonUtils.objectToString(all)).build();
			return maxMinHtmlVO;
		} else {
			volumeVOS = gpRepository.queryVolume(volumeVO);
			List<EndPriceVO> endPrice = volumeVOS.stream().map(a -> EndPriceVO.builder().series("收盘价/元").x(a.getDate()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());
			List<EndPriceVO> number = volumeVOS.stream().map(a -> EndPriceVO.builder().series("成交额/万手").x(a.getDate()).y(a.getNumber().divide(GpNumberConstant.tenThousand, 5, RoundingMode.FLOOR)).build()).collect(Collectors.toList());
			List<EndPriceVO> turnover = volumeVOS.stream().map(a -> EndPriceVO.builder().series("成交额/亿元").x(a.getDate()).y(a.getTurnover()).build()).collect(Collectors.toList());
			List<EndPriceVO> forecast = volumeVOS.stream().map(a -> EndPriceVO.builder().series("当日均价/元").x(a.getDate()).y(FormulaUtils.avg(a.getTurnover(), a.getNumber())).build()).collect(Collectors.toList());
			List<EndPriceVO> forecastPercent = volumeVOS.stream().map(a ->
			{
				//当日均价
				BigDecimal avg = FormulaUtils.avg(a.getTurnover(), a.getNumber());
				return EndPriceVO.builder().series("(当日均价-收盘价格)/收盘价格(万分之一)今日实收").x(a.getDate())
						.y((avg.subtract(a.getCurrentPrice()).multiply(GpNumberConstant.tenThousand)).divide(a.getCurrentPrice(), 5, RoundingMode.FLOOR)).build();
			}).collect(Collectors.toList());
			List<EndPriceVO> forecastPercent1 = volumeVOS.stream().map(a -> {
				//当日均价
				BigDecimal avg = FormulaUtils.avg(a.getTurnover(), a.getNumber());
				return EndPriceVO.builder().series("(收盘价格-当日均价)/当日均价(万分之一)").x(a.getDate())
						.y((a.getCurrentPrice().subtract(avg).multiply(GpNumberConstant.tenThousand)).divide(avg, 5, RoundingMode.FLOOR))
						.build();
			}).collect(Collectors.toList());

			List<EndPriceVO> forecastPercent2 = new ArrayList<>();
			List<EndPriceVO> forecastPercent3 = new ArrayList<>();
			List<EndPriceVO> forecastPercent4 = new ArrayList<>();
			List<EndPriceVO> forecastPercent5 = new ArrayList<>();


			for (int i = 1; i < volumeVOS.size(); i++) {

				//当天数据
				VolumeVO a = volumeVOS.get(i);
				//昨天数据
				VolumeVO b = volumeVOS.get(i - 1);
				//当日均价
				BigDecimal avg = FormulaUtils.avg(a.getTurnover(), a.getNumber());
				//昨日均价
				BigDecimal avgYesterDay = FormulaUtils.avg(b.getTurnover(), b.getNumber());

				forecastPercent2.add(EndPriceVO.builder().series("(今日均价-昨日均价)/昨日均价(万分之一)成本").x(a.getDate())
						.y((avg.subtract(avgYesterDay).multiply(GpNumberConstant.tenThousand)).divide(avgYesterDay, 5, RoundingMode.FLOOR))
						.build());

				forecastPercent3.add(EndPriceVO.builder().series("(今日成交量-昨日成交量)/昨日成交量(百分之一)").x(a.getDate())
						.y((a.getNumber().subtract(b.getNumber()).multiply(GpNumberConstant.oneHundred)).divide(b.getNumber(), 5, RoundingMode.FLOOR))
						.build());

				forecastPercent4.add(EndPriceVO.builder().series("(今日收盘价-昨日收盘价)/昨日收盘价(万分之一)").x(a.getDate())
						.y((a.getCurrentPrice().subtract(b.getCurrentPrice()).multiply(GpNumberConstant.tenThousand)).divide(b.getCurrentPrice(), 5, RoundingMode.FLOOR))
						.build());

				forecastPercent5.add(EndPriceVO.builder().series("(今日收盘价-昨日均价)/昨日均价(万分之一)").x(a.getDate())
						.y((a.getCurrentPrice().subtract(avgYesterDay).multiply(GpNumberConstant.tenThousand)).divide(avgYesterDay, 5, RoundingMode.FLOOR))
						.build());
			}


			List<EndPriceVO> all = new ArrayList<>();
			all.addAll(endPrice);
			all.addAll(number);
			all.addAll(turnover);
			all.addAll(forecast);
			//all.addAll(forecastPercent);
			all.addAll(forecastPercent1);
			all.addAll(forecastPercent2);
			all.addAll(forecastPercent3);
			all.addAll(forecastPercent4);
			all.addAll(forecastPercent5);

			all.sort(Comparator.comparing(EndPriceVO::getX));

			log.info(JsonUtils.objectToString(all));


			MaxMinHtmlVO maxMinHtmlVO = MaxMinHtmlVO.builder().data(JsonUtils.objectToString(all)).build();
			return maxMinHtmlVO;
		}


	}

	@Override
	public MaxMinHtmlVO queryVolumeTurningPoint(VolumeVO volumeVO) {
		List<VolumeVO> volumeVOS;
		List<VolumeVO> volumeLowToHight;
		List<VolumeVO> volumeHighToLow;
		volumeVOS = gpRepository.queryVolume(volumeVO);

		volumeLowToHight= gpRepository.queryLowToHight(volumeVO);

		volumeHighToLow= gpRepository.queryHightToLow(volumeVO);

		List<EndPriceVO> endPrice = volumeVOS.stream().map(a -> EndPriceVO.builder().series("收盘价/元").x(a.getDate()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());
		List<EndPriceVO> lowToHight = volumeLowToHight.stream().map(a -> EndPriceVO.builder().series("由低转高").x(a.getDate()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());
		List<EndPriceVO> highToLow = volumeHighToLow.stream().map(a -> EndPriceVO.builder().series("由高转低").x(a.getDate()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());


		List<EndPriceVO> all = new ArrayList<>();
		all.addAll(endPrice);
		all.addAll(lowToHight);
		all.addAll(highToLow);

		all.sort(Comparator.comparing(EndPriceVO::getX));

		log.info(JsonUtils.objectToString(all));

		MaxMinHtmlVO maxMinHtmlVO = MaxMinHtmlVO.builder().data(JsonUtils.objectToString(all)).build();
		return maxMinHtmlVO;

	}

	@Override
	public List<PercentVO> gpPriceCount(String gpCode, BigDecimal currentPrice) {
		//只查询当天的数据
		List<VolumeVO> volumeVOS = currentPrice != null ? gpStareRepository.gpPriceCountByPrice(gpCode, currentPrice) : gpStareRepository.gpPriceCount(gpCode);

		BigDecimal sumTurnover = volumeVOS.stream().map(a -> a.getTurnover()).reduce(BigDecimal.ZERO, BigDecimal::add);

		List<PercentVO> percentVOS = volumeVOS.stream().map(a -> PercentVO.builder()
				.type(String.valueOf(a.getCurrentPrice()))
				.value((a.getTurnover().multiply(new BigDecimal("100"))).divide(sumTurnover, 2, BigDecimal.ROUND_HALF_UP))
				.build()).collect(Collectors.toList());

		return percentVOS;
	}

	@Override
	public void history() {


		Path path = Paths.get("gp.txt");
		try {
			List<String> lines = Files.readAllLines(path);
			System.out.println(lines.size());
			String history = lines.get(0);
			String[] splitHistory = history.split(";");
			for (String split : splitHistory) {
				String[] detail = split.split(",");
				ZXVO zxvo = new ZXVO();
				zxvo.setGpCode("sz000651");
				zxvo.setGpName("格力电器");
				zxvo.setInitPrice(new BigDecimal(detail[2]));
				zxvo.setYesterdayEndPrice(new BigDecimal(detail[11]));
				zxvo.setCurrentPrice(new BigDecimal(detail[3]));
				zxvo.setMaxPrice(new BigDecimal(detail[5]));
				zxvo.setMinPirce(new BigDecimal(detail[6]));
				zxvo.setTurnover(new BigDecimal(detail[7]).divide(GpNumberConstant.oneHundredMillion, 15, RoundingMode.FLOOR));
				zxvo.setNumber(new BigDecimal(detail[4]).divide(GpNumberConstant.oneHundred, 2, RoundingMode.FLOOR));
				zxvo.setBiddingPrice(new BigDecimal("0"));
				zxvo.setAuction(new BigDecimal("0"));
				zxvo.setCreatedDate(LocalDateFormate.stringTolocalDateTime(detail[1].concat(" 15:00:00")));
				gpRepository.insert(zxvo);
				log.info("股票{}", zxvo);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * <p>
	 * 统计成交量，成交价差额变化
	 * </p>
	 *
	 * @author hzl 2023/03/06 1:19 PM
	 */
	public void initDifference(VolumeVO volumeVO) {


		//取当前最新的，
		// 取上一条数据

	}
}
