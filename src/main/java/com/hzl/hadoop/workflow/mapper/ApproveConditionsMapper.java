package com.hzl.hadoop.workflow.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ApproveConditionsEntity;
import com.hzl.hadoop.workflow.dto.ApproveConditionsDTO;
import com.hzl.hadoop.workflow.vo.ApproveConditionsVO;
import java.util.List;
/**
 * 审批条件
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2023-01-05 17:57:49
 */
@Mapper
public interface ApproveConditionsMapper extends BaseMapper<ApproveConditionsEntity> {

	List<ApproveConditionsDTO> listPage(ApproveConditionsVO params);
	
}
