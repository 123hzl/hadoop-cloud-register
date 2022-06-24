package com.hzl.hadoop.security.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.config.mvc.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import com.hzl.hadoop.security.entity.SysUserRoleEntity;
import com.hzl.hadoop.security.dto.SysUserRoleDTO;
import com.hzl.hadoop.security.service.SysUserRoleService;
import com.hzl.hadoop.security.vo.SysUserRoleVO;


/**
 * 用户角色关系表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
@RestController
@RequestMapping("security/sysuserrole" )
public class SysUserRoleController {
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
     * 列表
     */
	@GetMapping("/list" )
	public ResponseEntity<BaseResponse> list(SysUserRoleVO params, @RequestParam(defaultValue = "1" ) Integer current, @RequestParam(defaultValue = "20" ) Integer pageSize) {
		PageInfo<SysUserRoleDTO> pageinfos = sysUserRoleService.queryPage(params, current, pageSize);
		BaseResponse baseResponse = new BaseResponse(pageinfos);

		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}


	/**
     * 信息
     */
	@GetMapping("/info/{id}" )
	public ResponseEntity<BaseResponse> info(@PathVariable("id" ) Long id) {

		SysUserRoleEntity sysUserRole = sysUserRoleService.getById(id);
		BaseResponse baseResponse = new BaseResponse(sysUserRole);
		return new ResponseEntity(baseResponse, HttpStatus.OK);
	}

	/**
     * 保存
     */
	@PostMapping("/save" )
	public ResponseEntity save(@RequestBody SysUserRoleEntity sysUserRole) {

		sysUserRoleService.save(sysUserRole);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 修改
     */
	@PutMapping("/update" )
	public ResponseEntity update(@RequestBody SysUserRoleEntity sysUserRole) {

		sysUserRoleService.updateById(sysUserRole);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
     * 删除
     */
	@DeleteMapping("/delete" )
	public ResponseEntity delete(@RequestBody Long[]ids) {

		sysUserRoleService.removeByIds(Arrays.asList(ids));
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
