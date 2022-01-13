package com.hzl.hadoop.gp.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.constant.GpCodeEnum;
import com.hzl.hadoop.gp.entity.GpNewsEntity;
import com.hzl.hadoop.gp.service.GpNewsService;
import com.hzl.hadoop.gp.service.XinLangNews;
import com.hzl.hadoop.interfacemanager.annotation.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;


/**
 * 个股新闻
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-12-15 11:08:17
 */
@RestController
@RequestMapping("gp/gpnews")
@Permission(isLogin = false)
public class GpNewsController {
	@Autowired
	private GpNewsService gpNewsService;

	@Autowired
	private XinLangNews xinLangNews;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public ResponseEntity<PageInfo<GpNewsEntity>> list(GpNewsEntity params, @RequestParam int start, @RequestParam int pageSize) {
		PageInfo<GpNewsEntity> page = gpNewsService.queryPage(params, start, pageSize);

		return new ResponseEntity(page, HttpStatus.OK);
	}

	/**
	 * 插入今天的新闻
	 */
	@GetMapping("/insert/news/{gpCode}")
	public ResponseEntity<Boolean> insertTodayNews(@PathVariable("gpCode") String gpCode) throws IOException {


		return new ResponseEntity(xinLangNews.getTodayNews(GpCodeEnum.valueOf(gpCode)), HttpStatus.OK);
	}

	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public ResponseEntity<GpNewsEntity> info(@PathVariable("id") Long id) {
		GpNewsEntity gpNews = gpNewsService.getById(id);

		return new ResponseEntity(gpNews, HttpStatus.OK);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody GpNewsEntity gpNews) {
		gpNewsService.save(gpNews);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody GpNewsEntity gpNews) {
		gpNewsService.updateById(gpNews);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestBody Long[] ids) {
		gpNewsService.removeByIds(Arrays.asList(ids));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
