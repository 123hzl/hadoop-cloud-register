package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.entity.ProcessMountEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 流程挂载
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
public interface ProcessMountService extends IService<ProcessMountEntity> {

	PageInfo queryPage(ProcessMountEntity params, int start, int pageSize);
}

