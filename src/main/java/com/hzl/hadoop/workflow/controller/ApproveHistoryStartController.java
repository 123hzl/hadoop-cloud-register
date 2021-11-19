package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveHistoryStartVO;
import org.springframework.http.ResponseEntity;




/**
 * 开始审批历史
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-04 14:56:40
 */
@RestController
@RequestMapping("workflow/approvehistorystart")
public class ApproveHistoryStartController {
    @Autowired
    private ApproveHistoryStartService approveHistoryStartService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveHistoryStartEntity>> list(ApproveHistoryStartEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<ApproveHistoryStartEntity> page = approveHistoryStartService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveHistoryStartEntity> info(@PathVariable("id") Long id){
		ApproveHistoryStartEntity approveHistoryStart = approveHistoryStartService.getById(id);

        return new ResponseEntity(approveHistoryStart,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveHistoryStartEntity approveHistoryStart){
		approveHistoryStartService.save(approveHistoryStart);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveHistoryStartEntity approveHistoryStart){
		approveHistoryStartService.updateById(approveHistoryStart);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		approveHistoryStartService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
