package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import com.github.pagehelper.PageInfo;


import java.util.List;

/**
 * 审批开始节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveNodeStartService extends IService<ApproveNodeStartEntity> {

	PageInfo queryPage(ApproveNodeStartEntity params, int start, int pageSize);

	ApproveNodeStartEntity getStartWorkNode(String flowNum);

	List<NodeDTO> queryNode(Integer nodeType, Long nodeId );

}

