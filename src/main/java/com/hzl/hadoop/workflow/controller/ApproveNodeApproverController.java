package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveNodeApproverEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeApproverService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveNodeApproverVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvenodeapprover")
public class ApproveNodeApproverController {
    @Autowired
    private ApproveNodeApproverService approveNodeApproverService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveNodeApproverEntity>> list(ApproveNodeApproverEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveNodeApproverEntity> pageinfos = approveNodeApproverService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveNodeApproverEntity> info(@PathVariable("id") Integer id){
		ApproveNodeApproverEntity approveNodeApprover = approveNodeApproverService.getById(id);

        return new ResponseEntity(approveNodeApprover,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveNodeApproverEntity approveNodeApprover){
		approveNodeApproverService.save(approveNodeApprover);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveNodeApproverEntity approveNodeApprover){
		approveNodeApproverService.updateById(approveNodeApprover);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Integer[] ids){
		approveNodeApproverService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
