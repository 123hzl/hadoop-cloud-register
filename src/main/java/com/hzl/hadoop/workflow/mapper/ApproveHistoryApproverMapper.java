package com.hzl.hadoop.workflow.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveHistoryApproverEntity;


/**
 * 审批节点审批历史,一个节点是审批组多人审批，就插入多条记录
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveHistoryApproverMapper extends BaseMapper<ApproveHistoryApproverEntity> {
	
}
