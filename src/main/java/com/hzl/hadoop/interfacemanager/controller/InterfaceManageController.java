package com.hzl.hadoop.interfacemanager.controller;

import java.util.Arrays;

import com.hzl.hadoop.interfacemanager.dto.InterfaceManageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.interfacemanager.entity.InterfaceManageEntity;
import com.hzl.hadoop.interfacemanager.service.InterfaceManageService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.interfacemanager.vo.InterfaceManageVO;
import org.springframework.http.ResponseEntity;




/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-16 14:05:48
 */
@Slf4j
@RestController
@RequestMapping("/api/interfacemanage")
public class InterfaceManageController {
    @Autowired
    private InterfaceManageService interfaceManageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<InterfaceManageDTO>> list(InterfaceManageVO params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<InterfaceManageDTO> page = interfaceManageService.queryPage(params,start,pageSize);
        log.info("接口数据{}",page.toString());
        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<InterfaceManageEntity> info(@PathVariable("id") Long id){
		InterfaceManageEntity interfaceManage = interfaceManageService.getById(id);

        return new ResponseEntity(interfaceManage,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody InterfaceManageEntity interfaceManage){
		interfaceManageService.save(interfaceManage);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody InterfaceManageEntity interfaceManage){
		interfaceManageService.updateById(interfaceManage);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		interfaceManageService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
