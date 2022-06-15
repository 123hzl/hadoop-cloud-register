package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveHistoryEndEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryEndService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveHistoryEndVO;
import org.springframework.http.ResponseEntity;




/**
 * 结束节点审批历史，仅仅标记流程是否结束，不配置审批人，可以配置结束监听器
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvehistoryend")
public class ApproveHistoryEndController {
    @Autowired
    private ApproveHistoryEndService approveHistoryEndService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveHistoryEndEntity>> list(ApproveHistoryEndEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveHistoryEndEntity> pageinfos = approveHistoryEndService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveHistoryEndEntity> info(@PathVariable("id") Long id){
		ApproveHistoryEndEntity approveHistoryEnd = approveHistoryEndService.getById(id);

        return new ResponseEntity(approveHistoryEnd,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveHistoryEndEntity approveHistoryEnd){
		approveHistoryEndService.save(approveHistoryEnd);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveHistoryEndEntity approveHistoryEnd){
		approveHistoryEndService.updateById(approveHistoryEnd);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		approveHistoryEndService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
