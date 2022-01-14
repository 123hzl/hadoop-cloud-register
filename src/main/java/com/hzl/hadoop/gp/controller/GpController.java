package com.hzl.hadoop.gp.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.service.GpStareService;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 * 伊利股票
 *
 * @author hzl 2020/10/31 5:20 PM
 */
@Slf4j
@RestController
public class GpController {

	private GpService gpService;

	private GpRepository gpRepository;

	@Autowired
	private  GpStareService gpStareService;

	public GpController(GpService gpService, GpRepository gpRepository) {
		this.gpService = gpService;
		this.gpRepository = gpRepository;
	}

	/**
	 * <p>
	 * 获取伊利股票信息，插入数据库
	 * </p>
	 *
	 * @author hzl 2020/01/08 12:41 PM
	 */
	@GetMapping(value = "/gp/yl/insert/{code}")
	public ResponseEntity<GpVO> insert(@PathVariable String code) {
		return new ResponseEntity<>(gpService.insert(code), HttpStatus.OK);
	}

	@GetMapping(value = "/gp/yl/query/volume/{code}/page")
	public ResponseEntity<PageInfo<VolumeVO>> queryVolumePage(@PathVariable String code, VolumeVO volumeVO, @RequestParam int start, @RequestParam int pageSize) {
		volumeVO.setGpCode(code);
		PageInfo page1 = gpRepository.queryVolumePage(volumeVO);
		log.info("返回结果-------"+page1.toString());
		return new ResponseEntity(page1, HttpStatus.OK);
	}

	@GetMapping(value = "/gp/yl/query/volume/test/{code}")
	public ResponseEntity queryVolumetest(@PathVariable String code) {
		gpStareService.notifyBuyAndSale(code);
		return new ResponseEntity(HttpStatus.OK);
	}


}
