package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ApproveHistoryApproverEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 审批节点审批历史,一个节点是审批组多人审批，就插入多条记录
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveHistoryApproverService extends IService<ApproveHistoryApproverEntity> {

	PageInfo queryPage(ApproveHistoryApproverEntity params, int start, int pageSize);
}

