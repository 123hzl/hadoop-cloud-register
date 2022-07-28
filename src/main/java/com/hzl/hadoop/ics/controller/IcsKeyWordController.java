package com.hzl.hadoop.ics.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;
import com.hzl.hadoop.ics.dto.IcsKeyWordDTO;
import com.hzl.hadoop.ics.service.IcsKeyWordService;
import com.hzl.hadoop.ics.vo.IcsKeyWordVO;


/**
 * 智能客服-问题分词库
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@RestController
@RequestMapping("ics/icskeyword" )
public class IcsKeyWordController {
	@Autowired
	private IcsKeyWordService icsKeyWordService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(IcsKeyWordVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<IcsKeyWordDTO> pageinfos = icsKeyWordService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		IcsKeyWordEntity icsKeyWord = icsKeyWordService.getById(id);
		BaseResponse baseResponse = new BaseResponse(icsKeyWord);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody IcsKeyWordEntity icsKeyWord) {

		icsKeyWordService.save(icsKeyWord);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody IcsKeyWordEntity icsKeyWord) {

		icsKeyWordService.updateById(icsKeyWord);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		icsKeyWordService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
