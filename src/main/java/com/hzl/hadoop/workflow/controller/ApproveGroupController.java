package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveGroupEntity;
import com.hzl.hadoop.workflow.service.ApproveGroupService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveGroupVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批组
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvegroup")
public class ApproveGroupController {
    @Autowired
    private ApproveGroupService approveGroupService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveGroupEntity>> list(ApproveGroupEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveGroupEntity> pageinfos = approveGroupService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveGroupEntity> info(@PathVariable("id") Long id){
		ApproveGroupEntity approveGroup = approveGroupService.getById(id);

        return new ResponseEntity(approveGroup,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveGroupEntity approveGroup){
		approveGroupService.save(approveGroup);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveGroupEntity approveGroup){
		approveGroupService.updateById(approveGroup);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		approveGroupService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
