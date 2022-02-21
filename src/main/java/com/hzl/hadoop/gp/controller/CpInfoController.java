package com.hzl.hadoop.gp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.gp.entity.CpInfoEntity;
import com.hzl.hadoop.gp.service.CpInfoService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.vo.CpInfoVO;
import org.springframework.http.ResponseEntity;




/**
 * 彩票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-26 16:04:50
 */
@RestController
@RequestMapping("gp/cpinfo")
public class CpInfoController {
    @Autowired
    private CpInfoService cpInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<CpInfoEntity>> list(CpInfoEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<CpInfoEntity> page = cpInfoService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }

    /**
     * 信息
     */
    @GetMapping("/init")
    public ResponseEntity<Boolean> init(){
        cpInfoService.init();
        return new ResponseEntity(true,HttpStatus.OK);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<CpInfoEntity> info(@PathVariable("id") Long id){
		CpInfoEntity cpInfo = cpInfoService.getById(id);

        return new ResponseEntity(cpInfo,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CpInfoEntity cpInfo){
		cpInfoService.save(cpInfo);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody CpInfoEntity cpInfo){
		cpInfoService.updateById(cpInfo);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		cpInfoService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
