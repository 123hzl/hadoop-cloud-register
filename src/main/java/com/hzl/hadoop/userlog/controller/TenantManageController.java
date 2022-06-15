package com.hzl.hadoop.userlog.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.userlog.entity.TenantManageEntity;
import com.hzl.hadoop.userlog.service.TenantManageService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.userlog.vo.TenantManageVO;
import org.springframework.http.ResponseEntity;




/**
 * 租户管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-19 16:18:12
 */
@RestController
@RequestMapping("/api/tenantmanage")
public class TenantManageController {
    @Autowired
    private TenantManageService tenantManageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<TenantManageEntity>> list(TenantManageEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<TenantManageEntity> page = tenantManageService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<TenantManageEntity> info(@PathVariable("id") Long id){
		TenantManageEntity tenantManage = tenantManageService.getById(id);

        return new ResponseEntity(tenantManage,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody TenantManageEntity tenantManage){
		tenantManageService.save(tenantManage);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody TenantManageEntity tenantManage){
		tenantManageService.updateById(tenantManage);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		tenantManageService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
