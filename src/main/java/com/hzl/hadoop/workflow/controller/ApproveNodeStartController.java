package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeStartService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveNodeStartVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批开始节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvenodestart")
public class ApproveNodeStartController {
    @Autowired
    private ApproveNodeStartService approveNodeStartService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveNodeStartEntity>> list(ApproveNodeStartEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveNodeStartEntity> pageinfos = approveNodeStartService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveNodeStartEntity> info(@PathVariable("id") Integer id){
		ApproveNodeStartEntity approveNodeStart = approveNodeStartService.getById(id);

        return new ResponseEntity(approveNodeStart,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveNodeStartEntity approveNodeStart){
		approveNodeStartService.save(approveNodeStart);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveNodeStartEntity approveNodeStart){
		approveNodeStartService.updateById(approveNodeStart);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Integer[] ids){
		approveNodeStartService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
