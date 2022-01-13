package com.hzl.hadoop.gp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.service.GpNoticeService;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.vo.MaxMinHtmlVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

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

	public GpMaxMinController(GpService gpService, GpNoticeService gpNoticeService) {
		this.gpService = gpService;
		this.gpNoticeService = gpNoticeService;
	}

	/**
	 * <p>
	 * 最高价最低价波动图
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/gp/max/min/{code}")
	public String getMinMaxInfo(@PathVariable String code, Model model,@RequestParam(name = "date" ,required = false) LocalDate date) {
		MaxMinHtmlVO maxMinHtmlVO = gpNoticeService.volatilityPrice(code,date);
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
		log.info("结果"+maxMinHtmlVO.toString());
		model.addAttribute(maxMinHtmlVO);

		return "endPrice";
	}


}
