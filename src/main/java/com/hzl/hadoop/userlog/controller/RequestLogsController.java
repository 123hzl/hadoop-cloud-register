package com.hzl.hadoop.userlog.controller;

import java.util.Arrays;

import com.hzl.hadoop.userlog.dto.RequestLogsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hzl.hadoop.userlog.entity.RequestLogsEntity;
import com.hzl.hadoop.userlog.service.RequestLogsService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.userlog.vo.RequestLogsVO;
import org.springframework.http.ResponseEntity;




/**
 * 请求日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-19 16:18:12
 */
@RestController
@RequestMapping("/api/requestlogs")
public class RequestLogsController {
    @Autowired
    private RequestLogsService requestLogsService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageInfo<RequestLogsDTO>> list(RequestLogsVO params, @RequestParam(defaultValue = "1") int start, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<RequestLogsDTO> page = requestLogsService.queryPage(params,start,pageSize);

        return new ResponseEntity(page, HttpStatus.OK);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseEntity<RequestLogsEntity> info(@PathVariable("id") Long id){
		RequestLogsEntity requestLogs = requestLogsService.getById(id);

        return new ResponseEntity(requestLogs,HttpStatus.OK);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody RequestLogsEntity requestLogs){
		requestLogsService.save(requestLogs);

		return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody RequestLogsEntity requestLogs){
		requestLogsService.updateById(requestLogs);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestBody Long[] ids){
		requestLogsService.removeByIds(Arrays.asList(ids));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
