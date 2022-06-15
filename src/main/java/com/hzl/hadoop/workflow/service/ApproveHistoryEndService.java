package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEndEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 结束节点审批历史，仅仅标记流程是否结束，不配置审批人，可以配置结束监听器
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveHistoryEndService extends IService<ApproveHistoryEndEntity> {

	PageInfo queryPage(ApproveHistoryEndEntity params, int start, int pageSize);
}

