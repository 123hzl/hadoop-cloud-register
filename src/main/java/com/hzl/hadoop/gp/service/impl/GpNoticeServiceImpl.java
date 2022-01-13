package com.hzl.hadoop.gp.service.impl;

import com.hzl.hadoop.app.service.FreemarkerService;
import com.hzl.hadoop.gp.constant.GpUrlConstant;
import com.hzl.hadoop.gp.convert.GpConvert;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.service.GpNoticeService;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.gp.vo.MaxMinHtmlVO;
import com.hzl.hadoop.gp.vo.MaxMinVO;
import com.hzl.hadoop.util.JsonUtils;
import com.hzl.hadoop.util.LocalDateFormate;
import com.hzl.hadoop.util.email.service.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.hzl.hadoop.gp.constant.GpUrlConstant.BADDING_END;
import static com.hzl.hadoop.gp.constant.GpUrlConstant.BADDING_START;

/**
 * description
 * 股票提醒
 *
 * @author hzl 2020/11/03 6:30 PM
 */
@Service
@Slf4j
public class GpNoticeServiceImpl implements GpNoticeService {

	@Autowired
	private MailService mailService;

	@Autowired
	private GpRepository gpRepository;

	@Autowired
	FreemarkerService freemarkerService;

	@Override
	public void bidding(String gpCode) {
		GpConvert gpConvert = new GpConvert();
		//每天早上9点15执行-9:25结束
		LocalDateTime localDateTimeNow = LocalDateTime.now();
		if (localDateTimeNow.toLocalTime().isAfter(LocalDateFormate.stringTolocalDateTime(BADDING_START).toLocalTime()) &&
				localDateTimeNow.toLocalTime().isBefore(LocalDateFormate.stringTolocalDateTime(BADDING_END).toLocalTime())) {
			Map<String, String> date = gpConvert.getGpInfo(GpUrlConstant.GP_BASE_URL.concat(gpCode), null);
			String name = date.get("股票名字");
			BigDecimal yesterdayEndPrice = new BigDecimal(date.get("昨日收盘价"));
			BigDecimal currentPrice = new BigDecimal(date.get("竞买价，即买一报价"));
			if ((currentPrice.compareTo(yesterdayEndPrice)) >= 0) {
				NumberFormat percent = NumberFormat.getPercentInstance();
				percent.setMaximumFractionDigits(2);
				String percentString = percent.format(currentPrice.subtract(yesterdayEndPrice).divide(yesterdayEndPrice, 5, BigDecimal.ROUND_DOWN));
				//当前价大于昨日收盘价
				log.info("昨日收盘价:" + yesterdayEndPrice + "        当前价格:" + currentPrice);
				log.info("竞价于昨日收盘价比较-涨幅" + percentString);
				mailService.sendSimpleMail(name + "竞价信息提醒", "昨日收盘价:" + yesterdayEndPrice + "        当前价格:" + currentPrice + "\n"
						+ "竞价于昨日收盘价比较-涨幅" + percentString);
			} else {
				//当前价小于昨日收盘价
				NumberFormat percent = NumberFormat.getPercentInstance();
				percent.setMaximumFractionDigits(2);
				String percentString = percent.format(yesterdayEndPrice.subtract(currentPrice).divide(yesterdayEndPrice, 5, BigDecimal.ROUND_DOWN));
				//当前价大于昨日收盘价
				log.info("昨日收盘价:" + yesterdayEndPrice + "        当前价格:" + currentPrice);
				log.info("竞价于昨日收盘价比较-跌浮" + percentString);
				mailService.sendSimpleMail(name + "竞价信息提醒", "昨日收盘价:" + yesterdayEndPrice + "        当前价格:" + currentPrice + "\n"
						+ "竞价于昨日收盘价比较-跌浮" + percentString);
			}
		} else {
			log.info("竞价还没开始");
			mailService.sendSimpleMail("竞价还没开始", "竞价还没开始!!!");
		}
	}

	@Override
	public MaxMinHtmlVO volatilityPrice(String gpCode, LocalDate date) {
		List<GpVO> gpVOMax = gpRepository.selectMaxPriceVolatility(gpCode,date);
		List<GpVO> gpVOMin = gpRepository.selectMinPriceVolatility(gpCode,date);
		//List<GpVO> gpVOCurrent = gpRepository.selectCurrentPriceAll(gpCode);

		//获取当前股票价格
		GpConvert gpConvert = new GpConvert();
		Map<String, String> data1 = gpConvert.getGpInfo(GpUrlConstant.GP_BASE_URL.concat(gpCode), null);
		GpVO gpVO = new GpVO();

		if(date!=null){
			gpVO=gpRepository.selectEndPriceByDate(gpCode,date);
		}else{
			gpVO.init(data1);
		}
		gpVO.setGpCode(gpCode);



		NumberFormat percent = NumberFormat.getPercentInstance();
		percent.setMaximumFractionDigits(2);
		//当前价格和最低价格的百分比
		String currentMin = percent.format(gpVO.getCurrentPrice().subtract(gpVO.getMinPirce()).divide(gpVO.getMinPirce(), 5, BigDecimal.ROUND_DOWN));
		//当前价格和最高价格的百分比
		String currentMax = percent.format(gpVO.getCurrentPrice().subtract(gpVO.getMaxPrice()).divide(gpVO.getMaxPrice(), 5, BigDecimal.ROUND_DOWN));

		//当前价格和开盘价的百分比
		String currentInit = percent.format(gpVO.getCurrentPrice().subtract(gpVO.getInitPrice()).divide(gpVO.getInitPrice(), 5, BigDecimal.ROUND_DOWN));

		//当前价格和昨日收盘的百分比
		String currentYesterday = percent.format(gpVO.getCurrentPrice().subtract(gpVO.getYesterdayEndPrice()).divide(gpVO.getYesterdayEndPrice(), 5, BigDecimal.ROUND_DOWN));

		//今日开盘价和昨日收盘价的百分比
		String initYesterday = percent.format(gpVO.getInitPrice().subtract(gpVO.getYesterdayEndPrice()).divide(gpVO.getYesterdayEndPrice(), 5, BigDecimal.ROUND_DOWN));

		//组装折线图数据
		List<MaxMinVO> max = gpVOMax.stream().map(a -> MaxMinVO.builder().series("最高价").x(a.getCreatedDate().toLocalTime()).y(a.getMaxPrice()).build()).collect(Collectors.toList());
		List<MaxMinVO> min = gpVOMin.stream().map(a -> MaxMinVO.builder().series("最低价").x(a.getCreatedDate().toLocalTime()).y(a.getMinPirce()).build()).collect(Collectors.toList());
		List<GpVO> maxMinCurent = new ArrayList<>();
		maxMinCurent.addAll(gpVOMax);
		maxMinCurent.addAll(gpVOMin);
		List<MaxMinVO> currentM = maxMinCurent.stream().map(a -> MaxMinVO.builder().series("当前价").x(a.getCreatedDate().toLocalTime()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());
		//List<MaxMinVO> currentM = gpVOCurrent.stream().map(a -> MaxMinVO.builder().series("当前价").x(a.getCreatedDate().toLocalTime()).y(a.getCurrentPrice()).build()).collect(Collectors.toList());
		List<MaxMinVO> data = new ArrayList<>();
		data.addAll(max);
		data.addAll(min);
		data.addAll(currentM);
		data.sort(Comparator.comparing(MaxMinVO::getX));


		MaxMinHtmlVO maxMinHtmlVO = MaxMinHtmlVO.builder().gpName(gpVO.getGpName())
				.data(JsonUtils.objectToString(data))
				.currentPrice(gpVO.getCurrentPrice())
				.currentInit(currentInit)
				.currentMax(currentMax)
				.currentMin(currentMin)
				.minPrice(gpVO.getMinPirce())
				.initPrice(gpVO.getInitPrice())
				.yesterdayEndPrice(gpVO.getYesterdayEndPrice())
				.maxPrice(gpVO.getMaxPrice())
				.currentYesterday(currentYesterday)
				.initYesterday(initYesterday)
				.build();
		return maxMinHtmlVO;


	}

	@Override
	public void volatilityPriceSendMail(String gpCode) {
		MaxMinHtmlVO maxMinHtmlVO = volatilityPrice(gpCode,null);
		//     后面暂时处理，当当前价格低于开盘价的1%的时候发送邮件提醒
		if ((maxMinHtmlVO.getCurrentPrice().subtract(maxMinHtmlVO.getYesterdayEndPrice())).divide(maxMinHtmlVO.getYesterdayEndPrice(), 5, BigDecimal.ROUND_DOWN).longValue()<-0.1) {
			try {
				mailService.sendHtmlMail(maxMinHtmlVO.getGpName() + "当前价格低于开盘价超过1%", getFreemarkerMailText(maxMinHtmlVO), null);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if ((maxMinHtmlVO.getCurrentPrice().subtract(maxMinHtmlVO.getYesterdayEndPrice())).divide(maxMinHtmlVO.getYesterdayEndPrice(), 5, BigDecimal.ROUND_DOWN).longValue() > 0.1) {
			try {
				log.info("转化参数" + maxMinHtmlVO.toString());
				mailService.sendHtmlMail(maxMinHtmlVO.getGpName() + "当前价格高于开盘价多于1%", getFreemarkerMailText(maxMinHtmlVO), null);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getFreemarkerMailText(MaxMinHtmlVO maxMinHtmlVO) throws IOException, TemplateException {
		String htmlText = "";
		Map value = new HashMap();
		value.put("maxMinHtmlVO", maxMinHtmlVO);
		htmlText = freemarkerService.getFreemarkerHtml("pricePercent.html", value);

		return htmlText;
	}


}
