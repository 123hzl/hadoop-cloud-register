package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import com.hzl.hadoop.config.mvc.BaseResponse;
import com.hzl.hadoop.workflow.flow.StartWorkFlowService;
import com.hzl.hadoop.workflow.vo.ApproveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproverEntity;
import com.hzl.hadoop.workflow.service.ApproverService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproverVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批人
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approver")
public class ApproverController {
    @Autowired
    private ApproverService approverService;
    @Autowired
    private StartWorkFlowService startWorkFlowService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproverEntity>> list(ApproverEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproverEntity> pageinfos = approverService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproverEntity> info(@PathVariable("id") Long id){
		ApproverEntity approver = approverService.getById(id);

        return new ResponseEntity(approver,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproverEntity approver){
		approverService.save(approver);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproverEntity approver){
		approverService.updateById(approver);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		approverService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 保存
     */
    @PostMapping("/approve")
    public ResponseEntity<BaseResponse> approve(@RequestBody ApproveVO approveVO){
        BaseResponse baseResponse=new BaseResponse(startWorkFlowService.approveOrReject(approveVO));
        return new ResponseEntity(baseResponse ,HttpStatus.OK);
    }

}
