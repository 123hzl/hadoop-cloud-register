package com.hzl.hadoop.workflow.mapper;

import com.hzl.hadoop.workflow.dto.NodeDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeGatewayEntity;
import org.apache.ibatis.annotations.Param;


/**
 * 审批路由节点
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveNodeGatewayMapper extends BaseMapper<ApproveNodeGatewayEntity> {
	NodeDTO queryNodeById(@Param("nodeId")Long nodeId);

}