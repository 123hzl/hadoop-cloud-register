package com.hzl.hadoop.ics.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.ics.entity.IcsQuestionSulotionEntity;
import com.hzl.hadoop.ics.dto.IcsQuestionSulotionDTO;
import com.hzl.hadoop.ics.service.IcsQuestionSulotionService;
import com.hzl.hadoop.ics.vo.IcsQuestionSulotionVO;


/**
 * 智能客服-问题搜索记录回答表（用于分析优化）
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@RestController
@RequestMapping("ics/icsquestionsulotion" )
public class IcsQuestionSulotionController {
	@Autowired
	private IcsQuestionSulotionService icsQuestionSulotionService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(IcsQuestionSulotionVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<IcsQuestionSulotionDTO> pageinfos = icsQuestionSulotionService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		IcsQuestionSulotionEntity icsQuestionSulotion = icsQuestionSulotionService.getById(id);
		BaseResponse baseResponse = new BaseResponse(icsQuestionSulotion);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody IcsQuestionSulotionEntity icsQuestionSulotion) {

		icsQuestionSulotionService.save(icsQuestionSulotion);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody IcsQuestionSulotionEntity icsQuestionSulotion) {

		icsQuestionSulotionService.updateById(icsQuestionSulotion);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		icsQuestionSulotionService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
