package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ApproverEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 审批人
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproverService extends IService<ApproverEntity> {

	PageInfo queryPage(ApproverEntity params, int start, int pageSize);
}

