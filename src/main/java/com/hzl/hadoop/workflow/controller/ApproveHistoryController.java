package com.hzl.hadoop.workflow.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.interfacemanager.annotation.Permission;
import com.hzl.hadoop.workflow.dto.ApproveHistoryDTO;
import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.service.ApproveHistoryStartService;
import com.hzl.hadoop.workflow.vo.ApproveHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 * 查询审批记录
 * @author hzl 2022/06/28 1:34 PM
 */
@Permission
@RestController
@RequestMapping("workflow/approval/history")
public class ApproveHistoryController {

	@Autowired
	private ApproveHistoryStartService approveHistoryStartService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public ResponseEntity<PageInfo<ApproveHistoryDTO>> list(ApproveHistoryVO params, @RequestParam(defaultValue = "1" ) int page, @RequestParam(defaultValue = "20") int pageSize){
		PageInfo<ApproveHistoryDTO> pageinfos = approveHistoryStartService.listApproveHistory(params,page,pageSize);

		return new ResponseEntity(pageinfos, HttpStatus.OK);
	}
}
