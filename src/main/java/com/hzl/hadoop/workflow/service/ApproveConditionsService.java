package com.hzl.hadoop.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.workflow.vo.ApproveConditionsVO;
import com.hzl.hadoop.workflow.dto.ApproveConditionsDTO;
import com.hzl.hadoop.workflow.entity.ApproveConditionsEntity;


import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 审批条件
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2023-01-05 17:57:49
 */
public interface ApproveConditionsService extends IService<ApproveConditionsEntity> {

	PageInfo<ApproveConditionsDTO> queryPage(ApproveConditionsVO params, Integer current, Integer pageSize);
}

