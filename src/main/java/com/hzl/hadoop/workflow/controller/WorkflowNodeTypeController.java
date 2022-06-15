package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.WorkflowNodeTypeEntity;
import com.hzl.hadoop.workflow.service.WorkflowNodeTypeService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.WorkflowNodeTypeVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批节点类型
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/workflownodetype")
public class WorkflowNodeTypeController {
    @Autowired
    private WorkflowNodeTypeService workflowNodeTypeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<WorkflowNodeTypeEntity>> list(WorkflowNodeTypeEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<WorkflowNodeTypeEntity> pageinfos = workflowNodeTypeService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<WorkflowNodeTypeEntity> info(@PathVariable("id") Long id){
		WorkflowNodeTypeEntity workflowNodeType = workflowNodeTypeService.getById(id);

        return new ResponseEntity(workflowNodeType,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody WorkflowNodeTypeEntity workflowNodeType){
		workflowNodeTypeService.save(workflowNodeType);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody WorkflowNodeTypeEntity workflowNodeType){
		workflowNodeTypeService.updateById(workflowNodeType);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		workflowNodeTypeService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
