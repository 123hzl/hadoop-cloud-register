package com.hzl.hadoop.gp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.convert.GpPercentConvert;
import com.hzl.hadoop.gp.entity.GpXlPercentEntity;
import com.hzl.hadoop.gp.service.GpXlPercentService;
import com.hzl.hadoop.gp.vo.XlGpPercentVO;
import com.hzl.hadoop.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 时时成交价和具体价格的成交量，只存储当天的数据。前天的会自动清除
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-21 17:02:10
 */
@RestController
@RequestMapping("gp/gpxlpercent")
public class GpXlPercentController {
	@Autowired
	private GpXlPercentService gpXlPercentService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public ResponseEntity<PageInfo<GpXlPercentEntity>> list(GpXlPercentEntity params, @RequestParam int start, @RequestParam int pageSize) {
		PageInfo<GpXlPercentEntity> page = gpXlPercentService.queryPage(params, start, pageSize);

		return new ResponseEntity(page, HttpStatus.OK);
	}


	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public ResponseEntity<GpXlPercentEntity> info(@PathVariable("id") Long id) {
		GpXlPercentEntity gpXlPercent = gpXlPercentService.getById(id);

		return new ResponseEntity(gpXlPercent, HttpStatus.OK);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody GpXlPercentEntity gpXlPercent) {
		gpXlPercentService.save(gpXlPercent);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody GpXlPercentEntity gpXlPercent) {
		gpXlPercentService.updateById(gpXlPercent);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 信息
	 */
	@GetMapping("/init/{gpcode}")
	public ResponseEntity<Boolean> init(@PathVariable("gpcode") String gpcode) {

		return new ResponseEntity(gpXlPercentService.init(gpcode), HttpStatus.OK);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestBody Long[] ids) {
		gpXlPercentService.removeByIds(Arrays.asList(ids));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}


}
