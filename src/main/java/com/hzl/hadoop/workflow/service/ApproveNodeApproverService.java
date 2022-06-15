package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ApproveNodeApproverEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 审批节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveNodeApproverService extends IService<ApproveNodeApproverEntity> {

	PageInfo queryPage(ApproveNodeApproverEntity params, int start, int pageSize);
}

