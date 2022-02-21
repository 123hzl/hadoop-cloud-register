package com.hzl.hadoop.gp.controller;

import com.hzl.hadoop.gp.service.GpNoticeService;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.service.GpXlPercentService;
import com.hzl.hadoop.gp.vo.MaxMinHtmlVO;
import com.hzl.hadoop.gp.vo.PercentVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * description
 * 伊利股票
 *
 * @author hzl 2020/10/31 5:20 PM
 */
@Slf4j
@Controller
public class GpMaxMinController {


	private GpService gpService;
	private GpNoticeService gpNoticeService;
	private GpXlPercentService gpXlPercentService;

	public GpMaxMinController(GpService gpService, GpNoticeService gpNoticeService, GpXlPercentService gpXlPercentService) {
		this.gpService = gpService;
		this.gpNoticeService = gpNoticeService;
		this.gpXlPercentService = gpXlPercentService;
	}

	/**
	 * <p>
	 * 最高价最低价波动图
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/gp/max/min/{code}")
	public String getMinMaxInfo(@PathVariable String code, Model model, @RequestParam(name = "date", required = false) LocalDate date) {
		MaxMinHtmlVO maxMinHtmlVO = gpNoticeService.volatilityPrice(code, date);
		model.addAttribute(maxMinHtmlVO);
		return "maxMinCurrent";
	}

	/**
	 * <p>
	 * 获取收盘成交金额和成交价波动情况
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/gp/yl/query/volume/{code}")
	public String queryVolume(@PathVariable String code, VolumeVO volumeVO, Model model) {
		log.info("请求参数" + volumeVO.toString());
		volumeVO.setGpCode(code);
		MaxMinHtmlVO maxMinHtmlVO = gpService.queryVolume(volumeVO);
		log.info("结果" + maxMinHtmlVO.toString());
		model.addAttribute(maxMinHtmlVO);

		return "endPrice";
	}

	/**
	 * <p>
	 * 各价格买入量占比
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/gp/yl/query/percent/{code}")
	public String queryPercent(@PathVariable String code, Model model, @RequestParam(required = false, value = "currentPrice") BigDecimal currentPrice) {

		//先初始化数据
		//gpXlPercentService.init(code);

		List<PercentVO> percentVOS = gpService.gpPriceCount(code, currentPrice);
		log.info("百分比结果{}", JsonUtils.objectToString(percentVOS));
		MaxMinHtmlVO maxMinHtmlVO = new MaxMinHtmlVO();
		maxMinHtmlVO.setData(JsonUtils.objectToString(percentVOS));
		model.addAttribute(maxMinHtmlVO);
		return "percent";
	}

}
