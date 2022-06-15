package com.hzl.hadoop.workflow.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ProcessVariableEntity;


/**
 * 流程变量-启动流程的时候初始化，个流程节点可以通过流程id查询变量
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:05
 */
@Mapper
public interface ProcessVariableMapper extends BaseMapper<ProcessVariableEntity> {
	
}
