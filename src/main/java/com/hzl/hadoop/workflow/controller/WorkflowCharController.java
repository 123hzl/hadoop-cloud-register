package com.hzl.hadoop.workflow.controller;

import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.workflow.entity.WorkflowCharEntity;
import com.hzl.hadoop.workflow.service.WorkflowCharService;
import com.hzl.hadoop.workflow.vo.WorkflowCharVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 前端生成的流程图，需要转换成开始节点，审批节点，网关节点，结束节点
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-24 09:34:57
 */
@RestController
@RequestMapping("workflow/workflowchar")
public class WorkflowCharController {
	@Autowired
	private WorkflowCharService workflowCharService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public ResponseEntity<PageInfo<WorkflowCharEntity>> list(WorkflowCharEntity params, @RequestParam int start, @RequestParam int pageSize) {
		PageInfo<WorkflowCharEntity> page = workflowCharService.queryPage(params, start, pageSize);

		return new ResponseEntity(page, HttpStatus.OK);
	}


	/**
	 * 信息
	 */
	@GetMapping("/info/{id}")
	public ResponseEntity<WorkflowCharEntity> info(@PathVariable("id") Long id) {
		WorkflowCharEntity workflowChar = workflowCharService.getById(id);

		return new ResponseEntity(workflowChar, HttpStatus.OK);
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody WorkflowCharEntity workflowChar) {
		workflowCharService.saveAndConvert(workflowChar);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody WorkflowCharEntity workflowChar) {
		workflowCharService.updateById(workflowChar);

		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestBody Long[] ids) {
		workflowCharService.removeByIds(Arrays.asList(ids));

		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
