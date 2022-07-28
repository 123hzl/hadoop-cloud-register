package com.hzl.hadoop.ics.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.ics.entity.IcsQuestionSearchLogEntity;
import com.hzl.hadoop.ics.dto.IcsQuestionSearchLogDTO;
import com.hzl.hadoop.ics.service.IcsQuestionSearchLogService;
import com.hzl.hadoop.ics.vo.IcsQuestionSearchLogVO;


/**
 * 智能客服-问题搜索记录表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:46
 */
@RestController
@RequestMapping("ics/icsquestionsearchlog" )
public class IcsQuestionSearchLogController {
	@Autowired
	private IcsQuestionSearchLogService icsQuestionSearchLogService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(IcsQuestionSearchLogVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<IcsQuestionSearchLogDTO> pageinfos = icsQuestionSearchLogService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		IcsQuestionSearchLogEntity icsQuestionSearchLog = icsQuestionSearchLogService.getById(id);
		BaseResponse baseResponse = new BaseResponse(icsQuestionSearchLog);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody IcsQuestionSearchLogEntity icsQuestionSearchLog) {

		icsQuestionSearchLogService.save(icsQuestionSearchLog);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody IcsQuestionSearchLogEntity icsQuestionSearchLog) {

		icsQuestionSearchLogService.updateById(icsQuestionSearchLog);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		icsQuestionSearchLogService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
