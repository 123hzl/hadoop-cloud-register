package com.hzl.hadoop.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.dto.ApproveHistoryDTO;
import com.hzl.hadoop.workflow.entity.ApproveHistoryStartEntity;
import com.hzl.hadoop.workflow.vo.ApproveHistoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 开始审批历史
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ApproveHistoryStartMapper extends BaseMapper<ApproveHistoryStartEntity> {

	List<ApproveHistoryDTO> listApproveHistory(ApproveHistoryVO params);
}
