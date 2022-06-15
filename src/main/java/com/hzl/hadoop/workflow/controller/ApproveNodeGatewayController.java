package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveNodeGatewayEntity;
import com.hzl.hadoop.workflow.service.ApproveNodeGatewayService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveNodeGatewayVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批路由节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvenodegateway")
public class ApproveNodeGatewayController {
    @Autowired
    private ApproveNodeGatewayService approveNodeGatewayService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveNodeGatewayEntity>> list(ApproveNodeGatewayEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveNodeGatewayEntity> pageinfos = approveNodeGatewayService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveNodeGatewayEntity> info(@PathVariable("id") Integer id){
		ApproveNodeGatewayEntity approveNodeGateway = approveNodeGatewayService.getById(id);

        return new ResponseEntity(approveNodeGateway,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveNodeGatewayEntity approveNodeGateway){
		approveNodeGatewayService.save(approveNodeGateway);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveNodeGatewayEntity approveNodeGateway){
		approveNodeGatewayService.updateById(approveNodeGateway);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Integer[] ids){
		approveNodeGatewayService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
