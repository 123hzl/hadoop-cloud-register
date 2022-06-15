package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ProcessMountEntity;
import com.hzl.hadoop.workflow.service.ProcessMountService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ProcessMountVO;
import org.springframework.http.ResponseEntity;




/**
 * 流程挂载
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/processmount")
public class ProcessMountController {
    @Autowired
    private ProcessMountService processMountService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ProcessMountEntity>> list(ProcessMountEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ProcessMountEntity> pageinfos = processMountService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ProcessMountEntity> info(@PathVariable("id") Long id){
		ProcessMountEntity processMount = processMountService.getById(id);

        return new ResponseEntity(processMount,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ProcessMountEntity processMount){
		processMountService.save(processMount);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ProcessMountEntity processMount){
		processMountService.updateById(processMount);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		processMountService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
