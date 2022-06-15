package com.hzl.hadoop.workflow.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEndEntity;


/**
 * 结束节点审批历史，仅仅标记流程是否结束，不配置审批人，可以配置结束监听器
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveHistoryEndMapper extends BaseMapper<ApproveHistoryEndEntity> {
	
}
