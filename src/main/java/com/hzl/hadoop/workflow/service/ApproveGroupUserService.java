package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ApproveGroupUserEntity;
import com.github.pagehelper.PageInfo;


import java.util.List;
import java.util.Map;

/**
 * 审批组人员
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ApproveGroupUserService extends IService<ApproveGroupUserEntity> {

	PageInfo queryPage(ApproveGroupUserEntity params, int start, int pageSize);

	List<Long> queryUserIdsByGroupId(Long groupId);
}

