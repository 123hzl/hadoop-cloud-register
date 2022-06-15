package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ApproveNodeGatewayEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 审批路由节点
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveNodeGatewayService extends IService<ApproveNodeGatewayEntity> {

	PageInfo queryPage(ApproveNodeGatewayEntity params, int start, int pageSize);
}

