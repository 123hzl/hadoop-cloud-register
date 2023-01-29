package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import com.hzl.hadoop.interfacemanager.annotation.Permission;
import com.hzl.hadoop.workflow.flow.StartWorkFlowService;
import com.hzl.hadoop.workflow.vo.StartWorkFlowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveHistoryStartVO;
import org.springframework.http.ResponseEntity;

import javax.script.ScriptException;


/**
 * 开始审批历史
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Permission(isLogin = true)
@RestController
@RequestMapping("workflow/approvehistorystart")
public class ApproveHistoryStartController {
    @Autowired
    private ApproveHistoryStartService approveHistoryStartService;
    @Autowired
    StartWorkFlowService startWorkFlowService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveHistoryStartEntity>> list(ApproveHistoryStartEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveHistoryStartEntity> pageinfos = approveHistoryStartService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 列表
     */
    @PostMapping("/start")
    public ResponseEntity<Boolean> startWorkFlow(@RequestBody StartWorkFlowVO startWorkFlowVO){

        try {
            startWorkFlowService.startWorkFlow(startWorkFlowVO);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(true, HttpStatus.OK);
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
