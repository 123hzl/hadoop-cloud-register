package com.hzl.hadoop.workflow.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.WorkflowListenerEntity;


/**
 * 工作流监听配置类
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface WorkflowListenerMapper extends BaseMapper<WorkflowListenerEntity> {
	
}
