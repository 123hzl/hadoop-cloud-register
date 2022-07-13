package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.entity.ApproveNodeEndEntity;
import com.github.pagehelper.PageInfo;


import java.util.List;
import java.util.Map;

/**
 * 审批结束节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveNodeEndService extends IService<ApproveNodeEndEntity> {

	PageInfo queryPage(ApproveNodeEndEntity params, int start, int pageSize);

	List<ApproveNodeEndEntity> selectByFlowNum(String flowNum);

	NodeDTO queryNodeById(Long nodeId);
}

