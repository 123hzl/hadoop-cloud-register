package com.hzl.hadoop.gp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.service.GpInfoService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.vo.GpInfoVO;
import org.springframework.http.ResponseEntity;




/**
 * 股票信息列表-存储所有需要爬取的股票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-14 16:50:07
 */
@RestController
@RequestMapping("gp/gpinfo")
public class GpInfoController {
    @Autowired
    private GpInfoService gpInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<GpInfoEntity>> list(GpInfoEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<GpInfoEntity> page = gpInfoService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<GpInfoEntity> info(@PathVariable("id") Long id){
		GpInfoEntity gpInfo = gpInfoService.getById(id);

        return new ResponseEntity(gpInfo,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody GpInfoEntity gpInfo){
		gpInfoService.save(gpInfo);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody GpInfoEntity gpInfo){
		gpInfoService.updateById(gpInfo);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		gpInfoService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
