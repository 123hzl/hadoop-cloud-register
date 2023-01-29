package com.hzl.hadoop.workflow.flow;

import com.hzl.hadoop.workflow.dto.NodeDTO;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.vo.NodeContainer;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/06/16 10:15 AM
 */
public interface NodeHandle {

	NodeContainer queryNodeInfo(NodeType nodeType, Long nodeId);

	NodeContainer queryNodeInfoByFlowNum(NodeType nodeType, String flowNum);

	NodeDTO queryNodeById(NodeType nodeType, Long nodeId );

}
