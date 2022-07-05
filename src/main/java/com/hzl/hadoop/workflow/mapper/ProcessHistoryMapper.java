package com.hzl.hadoop.workflow.mapper;

import com.hzl.hadoop.workflow.dto.ProcessHistoryPageDTO;
import com.hzl.hadoop.workflow.vo.ProcessHistoryPageVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.workflow.entity.ProcessHistoryEntity;

import java.util.List;


/**
 * 流程记录-每次启动流程就插入一条流程记录
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-15 16:05:06
 */
@Mapper
public interface ProcessHistoryMapper extends BaseMapper<ProcessHistoryEntity> {

	List<ProcessHistoryPageDTO> queryPage(ProcessHistoryPageVO params);
}
