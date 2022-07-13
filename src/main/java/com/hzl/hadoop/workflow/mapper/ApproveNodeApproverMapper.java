package com.hzl.hadoop.workflow.mapper;

import com.hzl.hadoop.workflow.dto.NodeDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeApproverEntity;
import org.apache.ibatis.annotations.Param;


/**
 * 审批节点
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveNodeApproverMapper extends BaseMapper<ApproveNodeApproverEntity> {
	NodeDTO queryNodeById(@Param("nodeId")Long nodeId);

}
