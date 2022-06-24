package com.hzl.hadoop.security.controller;

import java.util.Arrays;

import com.hzl.hadoop.config.mvc.BaseResponse;
import com.hzl.hadoop.interfacemanager.annotation.Permission;
import com.hzl.hadoop.security.entity.SysUser;
import com.hzl.hadoop.security.dto.SysUserDTO;
import com.hzl.hadoop.security.service.SysUserService;
import com.hzl.hadoop.security.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.github.pagehelper.PageInfo;
import org.springframework.http.ResponseEntity;




/**
 * 用户信息表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-17 09:10:15
 */
@RestController
@RequestMapping("/api/sysuser")
@Permission(isLogin = true)
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<BaseResponse> list(SysUserVO params, @RequestParam(defaultValue = "1" ) Integer page, @RequestParam(defaultValue = "20") Integer pageSize){
		PageInfo<SysUserDTO> pageinfos = sysUserService.queryPage(params,page,pageSize);
        BaseResponse baseResponse=new BaseResponse(pageinfos);

        return new ResponseEntity(baseResponse, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<BaseResponse> info(@PathVariable("id") Long id){
		SysUser sysUser = sysUserService.getById(id);
        BaseResponse baseResponse=new BaseResponse(sysUser);
        return new ResponseEntity(baseResponse,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody SysUser sysUser){
		sysUserService.save(sysUser);
		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody SysUser sysUser){
		sysUserService.updateById(sysUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		sysUserService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
