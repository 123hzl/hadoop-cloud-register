package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeEndService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveNodeEndVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批结束节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvenodeend")
public class ApproveNodeEndController {
    @Autowired
    private ApproveNodeEndService approveNodeEndService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveNodeEndEntity>> list(ApproveNodeEndEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveNodeEndEntity> pageinfos = approveNodeEndService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveNodeEndEntity> info(@PathVariable("id") Integer id){
		ApproveNodeEndEntity approveNodeEnd = approveNodeEndService.getById(id);

        return new ResponseEntity(approveNodeEnd,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveNodeEndEntity approveNodeEnd){
		approveNodeEndService.save(approveNodeEnd);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveNodeEndEntity approveNodeEnd){
		approveNodeEndService.updateById(approveNodeEnd);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Integer[] ids){
		approveNodeEndService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
