package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import com.hzl.hadoop.config.mvc.BaseResponse;
import com.hzl.hadoop.workflow.dto.ProcessHistoryPageDTO;
import com.hzl.hadoop.workflow.vo.ProcessHistoryPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;
import com.hzl.hadoop.workflow.service.ProcessHistoryService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ProcessHistoryVO;
import org.springframework.http.ResponseEntity;




/**
 * 流程记录-每次启动流程就插入一条流程记录
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/processhistory")
public class ProcessHistoryController {
    @Autowired
    private ProcessHistoryService processHistoryService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<BaseResponse> list(ProcessHistoryPageVO params, @RequestParam(defaultValue = "1" ) int current, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ProcessHistoryPageDTO> pageinfos = processHistoryService.queryPage(params,current,pageSize);
        BaseResponse baseResponse=new BaseResponse(pageinfos);

        return new ResponseEntity(baseResponse, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ProcessHistoryEntity> info(@PathVariable("id") Long id){
		ProcessHistoryEntity processHistory = processHistoryService.getById(id);

        return new ResponseEntity(processHistory,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ProcessHistoryEntity processHistory){
		processHistoryService.save(processHistory);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ProcessHistoryEntity processHistory){
		processHistoryService.updateById(processHistory);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		processHistoryService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
