package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.repository.GpStareRepository;
import com.hzl.hadoop.gp.service.GpInfoService;
import com.hzl.hadoop.gp.service.GpStareService;
import com.hzl.hadoop.gp.service.GpXlPercentService;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.util.LocalDateFormate;
import com.hzl.hadoop.util.email.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author hzl 2022/01/13 7:11 PM
 */
@Slf4j
@Service
public class GpStareServiceImpl implements GpStareService {

	@Autowired
	GpStareRepository gpStareRepository;


	@Autowired
	private MailService mailService;

	@Autowired
	private GpInfoService gpInfoService;


	@Autowired
	GpXlPercentService gpXlPercentService;

	@Override
	public Boolean notifyBuyAndSale(String gpCode) {

		QueryWrapper<GpInfoEntity> queryWrapper = new QueryWrapper();
		queryWrapper.eq("gp_code", gpCode);

		GpInfoEntity gpInfoEntity = gpInfoService.getOne(queryWrapper,false);

		if (gpInfoEntity == null) {
			//如果股票信息表没有数据，直接返回
			return true;
		}

		if (!gpInfoEntity.getIsNotify()) {
			//如果设置了不发送直接返回
			return true;
		}
		//查询时时均价
		VolumeVO gpVO = gpStareRepository.queryCurrentPercent(gpCode);

		//优化，新建股票表，是否开启通知字段

		if (gpVO != null) {
			Double big = new Double(0);
			Double currentPercent = gpVO.getPercent();
			//当前时间
			String localDate = LocalDateFormate.localDateTimeToString(LocalDateTime.now(), "HH:mm");

			if (currentPercent.compareTo(big) >= 0) {
				//建议买入价
				Map<String, BigDecimal> buyGuest = notifyBuePrice(gpCode);
				//查询三十天亏损的均价，
				List<VolumeVO> gpVOList1 = gpStareRepository.getHistoryAverage(gpCode, true);

				if (gpVOList1 != null && gpVOList1.size() > 5) {
					//求平均亏损
					Double percentList = gpVOList1.stream().mapToDouble(VolumeVO::getPercent).average().getAsDouble();

					/*if (currentPercent.compareTo(percentList) >= 0) {
						//当前亏损率大于等于近一个月平均亏损
						try {
							mailService.sendHtmlMail(localDate + "股票:" + gpVO.getGpName() + "当前亏损率大于等于近一个月平均亏损--建议买入"
									, "平均亏损：" + percentList + "当前亏损：" + currentPercent + "建议买入价格" + buyGuest.get("minPrice") + "~" + buyGuest.get("avgPrice"), null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}

					}*/
					if (currentPercent.compareTo(gpVOList1.get(2).getPercent()) >= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate + "股票:" + gpVO.getGpName() + "当前亏损率大于等于排行第三的亏损--建议买入",
									"第三的亏损：" + gpVOList1.get(2).getPercent() + "当前亏损：" + currentPercent + "建议买入价格" + buyGuest.get("minPrice") + "~" + buyGuest.get("avgPrice"), null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}

					if (currentPercent.compareTo(gpVOList1.get(0).getPercent()) >= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate + "股票:" + gpVO.getGpName() + "当前亏损率大于等于排行第一的亏损--建议买入",
									"第一的亏损：" + gpVOList1.get(0).getPercent() + "当前亏损：" + currentPercent + "建议买入价格" + buyGuest.get("minPrice") + "~" + buyGuest.get("avgPrice"), null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}


			} else if (currentPercent.compareTo(big) < 0) {
				//查询三十天盈利的均价
				List<VolumeVO> gpVOList2 = gpStareRepository.getHistoryAverage(gpCode, false);
				if (gpVOList2 != null && gpVOList2.size() > 5) {
					//建议卖入价
					Map<String, BigDecimal> buyGuest = notifyBuePrice(gpCode);
					//求平均亏损
					Double percentList = gpVOList2.stream().mapToDouble(VolumeVO::getPercent).average().getAsDouble();

					if (currentPercent.compareTo(percentList) >= 0) {
						//当前亏损率大于等于近一个月平均亏损
						try {
							mailService.sendHtmlMail(localDate + "股票:" + gpVO.getGpName() + "当前盈利小于平均盈利--建议尽早抛售",
									"平均盈利：" + percentList + "当前盈利：" + currentPercent + "建议卖出价格" + buyGuest.get("minPrice") + "~" + buyGuest.get("avgPrice"), null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}

					}
					if (currentPercent.compareTo(gpVOList2.get(2).getPercent()) >= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate + "股票:" + gpVO.getGpName() + "当前盈利小于排行第三的盈利--建议尽早抛售",
									"第三的盈利：" + gpVOList2.get(2).getPercent() + "当前盈利：" + currentPercent + "建议卖出价格" + buyGuest.get("minPrice") + "~" + buyGuest.get("avgPrice"), null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}

					if (currentPercent.compareTo(gpVOList2.get(gpVOList2.size() - 1).getPercent()) <= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate + "股票:" + gpVO.getGpName() + "当前盈利率大于历史最大--建议立马抛售",
									"第一的盈利：" + gpVOList2.get(gpVOList2.size() - 1).getPercent() + "当前盈利：" + currentPercent + "建议卖出价格" + buyGuest.get("minPrice") + "~" + buyGuest.get("avgPrice"), null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public Map<String, BigDecimal> notifyBuePrice(String gpCode) {

		List<VolumeVO> volumeVOS = gpStareRepository.gpPriceCount(gpCode);

		Map<String, BigDecimal> reuslt = new HashMap<>(2, 1);

		if (CollectionUtils.isNotEmpty(volumeVOS)) {
			//平均买入量
			BigDecimal avgNumber = volumeVOS.stream().map(a -> a.getTurnover()).reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(volumeVOS.size()), 2, BigDecimal.ROUND_HALF_UP);
			//查询大于买入次数均价的记录
			List<BigDecimal> volumeVOS1 = volumeVOS.stream().filter(a -> a.getTurnover().compareTo(avgNumber) > 0).map(b -> b.getCurrentPrice()).collect(Collectors.toList());
			log.info("过滤结果{}", volumeVOS1.toString());
			BigDecimal minPrice = volumeVOS1.stream().min((x1, x2) -> x1.compareTo(x2)).get();
			BigDecimal avgPrice = volumeVOS1.stream().reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(volumeVOS1.size()), 2, BigDecimal.ROUND_HALF_UP);

			log.info("建议买入价范围{}~{}", minPrice, avgPrice);
			reuslt.put("minPrice", minPrice);
			reuslt.put("avgPrice", avgPrice);
		} else {
			reuslt.put("minPrice", BigDecimal.valueOf(0));
			reuslt.put("avgPrice", BigDecimal.valueOf(0));
		}


		return reuslt;
	}


}
