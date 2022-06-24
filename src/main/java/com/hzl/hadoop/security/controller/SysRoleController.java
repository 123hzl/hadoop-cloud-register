package com.hzl.hadoop.security.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.security.entity.SysRoleEntity;
import com.hzl.hadoop.security.dto.SysRoleDTO;
import com.hzl.hadoop.security.service.SysRoleService;
import com.hzl.hadoop.security.vo.SysRoleVO;


/**
 * 用户角色表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
@RestController
@RequestMapping("security/sysrole" )
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(SysRoleVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<SysRoleDTO> pageinfos = sysRoleService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		SysRoleEntity sysRole = sysRoleService.getById(id);
		BaseResponse baseResponse = new BaseResponse(sysRole);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody SysRoleEntity sysRole) {

		sysRoleService.save(sysRole);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody SysRoleEntity sysRole) {

		sysRoleService.updateById(sysRole);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		sysRoleService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
