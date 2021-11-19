package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveHistoryGatewayEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryGatewayService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveHistoryGatewayVO;
import org.springframework.http.ResponseEntity;




/**
 * 网关审批历史
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-04 14:56:40
 */
@RestController
@RequestMapping("workflow/approvehistorygateway")
public class ApproveHistoryGatewayController {
    @Autowired
    private ApproveHistoryGatewayService approveHistoryGatewayService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveHistoryGatewayEntity>> list(ApproveHistoryGatewayEntity params,@RequestParam int start, @RequestParam int pageSize){
		PageInfo<ApproveHistoryGatewayEntity> page = approveHistoryGatewayService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveHistoryGatewayEntity> info(@PathVariable("id") Long id){
		ApproveHistoryGatewayEntity approveHistoryGateway = approveHistoryGatewayService.getById(id);

        return new ResponseEntity(approveHistoryGateway,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveHistoryGatewayEntity approveHistoryGateway){
		approveHistoryGatewayService.save(approveHistoryGateway);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveHistoryGatewayEntity approveHistoryGateway){
		approveHistoryGatewayService.updateById(approveHistoryGateway);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		approveHistoryGatewayService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
