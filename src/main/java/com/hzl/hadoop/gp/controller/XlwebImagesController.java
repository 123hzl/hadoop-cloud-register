package com.hzl.hadoop.gp.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import com.hzl.hadoop.gp.service.XlwebImagesService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.vo.XlwebImagesVO;
import org.springframework.http.ResponseEntity;




/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-12-23 13:12:30
 */
@RestController
@RequestMapping("gp/xlwebimages")
public class XlwebImagesController {
    @Autowired
    private XlwebImagesService xlwebImagesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<XlwebImagesEntity>> list(XlwebImagesEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<XlwebImagesEntity> page = xlwebImagesService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<XlwebImagesEntity> info(@PathVariable("id") Long id){
		XlwebImagesEntity xlwebImages = xlwebImagesService.getById(id);

        return new ResponseEntity(xlwebImages,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody XlwebImagesEntity xlwebImages){
		xlwebImagesService.save(xlwebImages);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody XlwebImagesEntity xlwebImages){
		xlwebImagesService.updateById(xlwebImages);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		xlwebImagesService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /**
     * 删除
     */
    @GetMapping("/all")
    public ResponseEntity<Boolean> getAllImagePage(@RequestParam Long uid){

        return new ResponseEntity(xlwebImagesService.getAllImagePage(uid),HttpStatus.OK);
    }

}
