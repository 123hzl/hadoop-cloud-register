package com.hzl.hadoop.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.constant.ApproveActionConstant;
import com.hzl.hadoop.workflow.constant.NodeStatusEnum;
import com.hzl.hadoop.workflow.constant.NodeType;
import com.hzl.hadoop.workflow.entity.ApproveGroupEntity;
import com.hzl.hadoop.workflow.entity.ApproveHistoryEntity;
import com.hzl.hadoop.workflow.entity.ApproveNodeAbstract;
import com.hzl.hadoop.workflow.flow.ApproveHistoryHandle;
import com.hzl.hadoop.workflow.flow.NodeHandle;
import com.hzl.hadoop.workflow.mapper.ApproveGroupMapper;
import com.hzl.hadoop.workflow.service.ApproveGroupService;
import com.hzl.hadoop.workflow.vo.ApproveVO;
import com.hzl.hadoop.workflow.vo.NodeContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("approveGroupService")
public class ApproveGroupServiceImpl extends ServiceImpl<ApproveGroupMapper, ApproveGroupEntity> implements ApproveGroupService {
	@Autowired
	NodeHandle nodeHandle;
	@Autowired
	ApproveGroupMapper mapper;
	@Autowired
	ApproveHistoryHandle approveHistoryHandle;


	@Override
	public PageInfo queryPage(ApproveGroupEntity params, int start, int pageSize) {
		QueryWrapper<ApproveGroupEntity> queryWrapper = new QueryWrapper(params);
		queryWrapper.orderByDesc("create_time");
		PageInfo<ApproveGroupEntity> pageResult = PageHelper.startPage(start, pageSize).doSelectPageInfo(() -> mapper.selectList(queryWrapper));

		return pageResult;
	}

	@Override
	public Boolean isAllApprove(Long processId, Long nodeId, Integer nodeType) {
		NodeType nodeType1 = NodeType.match(nodeType);
		//根据当前节点id和节点类型，查询节点上配置的审批组
		NodeContainer nodeContainer = nodeHandle.queryNodeInfo(nodeType1, nodeId);
		ApproveNodeAbstract approveNodeAbstract = nodeContainer.getNodeInfo(nodeType1);
		Long approverGroupId = approveNodeAbstract.getApproverGroupId();
		if (approverGroupId != null) {
			//根据groupid查询分组信息
			ApproveGroupEntity approveGroupEntity = mapper.selectById(approverGroupId);
			if (approveGroupEntity == null) {
				return true;
			} else if (approveGroupEntity.getApproveType()) {
				return approveGroupEntity.getApproveType();
			} else if (!approveGroupEntity.getApproveType()) {
				//全部同意的情况，如果节点审批人员已经全部同意则返回true，否则返回false
				//根据节点类型，流程id，当前节点id

				//todo 需要排除自身，因为无法读未提交，，，还有一个问题并非审批的问题，会出现俩人发现对方都未审批，认为流程没有结束
				List<ApproveHistoryEntity> approveHistoryEntityList = approveHistoryHandle.queryHistory(ApproveVO.builder()
						.nodeId(nodeId)
						.processId(processId)
						.nodeStatus(NodeStatusEnum.WAIT.getValue())
						.nodeType(nodeType)
						.build());
				for (ApproveHistoryEntity a : approveHistoryEntityList) {
					if (ApproveActionConstant.WAIT.value().equals(a.getApproveAction()) || ApproveActionConstant.REJECT.value().equals(a.getApproveAction())) {
						return false;
					}
				}
			}
		} else {
			return true;
		}

		throw new CommonException("判断节点是否全部审批通过失败，请联系管理员处理"+processId+"nodeId"+nodeId+"nodeType"+nodeType);
	}

}