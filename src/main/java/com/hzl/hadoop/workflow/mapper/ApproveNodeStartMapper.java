package com.hzl.hadoop.workflow.mapper;

import com.hzl.hadoop.workflow.dto.NodeDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveNodeStartEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 审批开始节点
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveNodeStartMapper extends BaseMapper<ApproveNodeStartEntity> {
	List<NodeDTO> queryNode(@Param("nodeType") Integer nodeType, @Param("nodeId")Long nodeId );

	NodeDTO queryNodeById(@Param("nodeId")Long nodeId);
}
