package com.hzl.hadoop.ics.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.ics.entity.IcsQuestionEntity;
import com.hzl.hadoop.ics.dto.IcsQuestionDTO;
import com.hzl.hadoop.ics.service.IcsQuestionService;
import com.hzl.hadoop.ics.vo.IcsQuestionVO;


/**
 * 智能客服-问题记录表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@RestController
@RequestMapping("ics/icsquestion" )
public class IcsQuestionController {
	@Autowired
	private IcsQuestionService icsQuestionService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(IcsQuestionVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<IcsQuestionDTO> pageinfos = icsQuestionService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		IcsQuestionEntity icsQuestion = icsQuestionService.getById(id);
		BaseResponse baseResponse = new BaseResponse(icsQuestion);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody IcsQuestionEntity icsQuestion) {

		icsQuestionService.saveInfo(icsQuestion);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody IcsQuestionEntity icsQuestion) {

		icsQuestionService.updateById(icsQuestion);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		icsQuestionService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}




}
