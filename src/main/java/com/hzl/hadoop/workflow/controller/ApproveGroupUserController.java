package com.hzl.hadoop.workflow.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.workflow.entity.ApproveGroupUserEntity;
import com.hzl.hadoop.workflow.service.ApproveGroupUserService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.vo.ApproveGroupUserVO;
import org.springframework.http.ResponseEntity;




/**
 * 审批组人员
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@RestController
@RequestMapping("workflow/approvegroupuser")
public class ApproveGroupUserController {
    @Autowired
    private ApproveGroupUserService approveGroupUserService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<ApproveGroupUserEntity>> list(ApproveGroupUserEntity params,@RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveGroupUserEntity> pageinfos = approveGroupUserService.queryPage(params,page,pageSize);

        return new ResponseEntity(pageinfos, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<ApproveGroupUserEntity> info(@PathVariable("id") Long id){
		ApproveGroupUserEntity approveGroupUser = approveGroupUserService.getById(id);

        return new ResponseEntity(approveGroupUser,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ApproveGroupUserEntity approveGroupUser){
		approveGroupUserService.save(approveGroupUser);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ApproveGroupUserEntity approveGroupUser){
		approveGroupUserService.updateById(approveGroupUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		approveGroupUserService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
