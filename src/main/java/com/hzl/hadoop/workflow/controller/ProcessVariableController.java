package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;
import com.hzl.hadoop.workflow.service.ProcessVariableService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ProcessVariableVO;
import org.springframework.http.ResponseEntity;




/**
 * 流程变量-启动流程的时候初始化，个流程节点可以通过流程id查询变量
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:05
 */
@RestController
@RequestMapping("workflow/processvariable")
public class ProcessVariableController {
    @Autowired
    private ProcessVariableService processVariableService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ProcessVariableEntity>> list(ProcessVariableEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ProcessVariableEntity> pageinfos = processVariableService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ProcessVariableEntity> info(@PathVariable("id") Long id){
		ProcessVariableEntity processVariable = processVariableService.getById(id);

        return new ResponseEntity(processVariable,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ProcessVariableEntity processVariable){
		processVariableService.save(processVariable);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ProcessVariableEntity processVariable){
		processVariableService.updateById(processVariable);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		processVariableService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
