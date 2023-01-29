package com.hzl.hadoop.workflow.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.workflow.entity.ApproveConditionsEntity;
import com.hzl.hadoop.workflow.dto.ApproveConditionsDTO;
import com.hzl.hadoop.workflow.service.ApproveConditionsService;
import com.hzl.hadoop.workflow.vo.ApproveConditionsVO;


/**
 * 审批条件
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2023-01-05 17:57:49
 */
@RestController
@RequestMapping("hadoop.workflow/approveconditions" )
public class ApproveConditionsController {
	@Autowired
	private ApproveConditionsService approveConditionsService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(ApproveConditionsVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<ApproveConditionsDTO> pageinfos = approveConditionsService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		ApproveConditionsEntity approveConditions = approveConditionsService.getById(id);
		BaseResponse baseResponse = new BaseResponse(approveConditions);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody ApproveConditionsEntity approveConditions) {

		approveConditionsService.save(approveConditions);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody ApproveConditionsEntity approveConditions) {

		approveConditionsService.updateById(approveConditions);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		approveConditionsService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
