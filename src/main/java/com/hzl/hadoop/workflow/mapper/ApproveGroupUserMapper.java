package com.hzl.hadoop.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveGroupUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 审批组人员
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveGroupUserMapper extends BaseMapper<ApproveGroupUserEntity> {
	List<Long> queryUserIdsByGroupId(Long groupId);
}
