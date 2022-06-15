package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.WorkflowListenerEntity;
import com.hzl.hadoop.workflow.service.WorkflowListenerService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.WorkflowListenerVO;
import org.springframework.http.ResponseEntity;




/**
 * 工作流监听配置类
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/workflowlistener")
public class WorkflowListenerController {
    @Autowired
    private WorkflowListenerService workflowListenerService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<WorkflowListenerEntity>> list(WorkflowListenerEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<WorkflowListenerEntity> pageinfos = workflowListenerService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<WorkflowListenerEntity> info(@PathVariable("id") Long id){
		WorkflowListenerEntity workflowListener = workflowListenerService.getById(id);

        return new ResponseEntity(workflowListener,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody WorkflowListenerEntity workflowListener){
		workflowListenerService.save(workflowListener);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody WorkflowListenerEntity workflowListener){
		workflowListenerService.updateById(workflowListener);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		workflowListenerService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
