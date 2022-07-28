package com.hzl.hadoop.ics.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.ics.entity.IcsQuestionSulotionEntity;
import com.hzl.hadoop.ics.dto.IcsQuestionSulotionDTO;
import com.hzl.hadoop.ics.vo.IcsQuestionSulotionVO;
import java.util.List;
/**
 * 智能客服-问题搜索记录回答表（用于分析优化）
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@Mapper
public interface IcsQuestionSulotionMapper extends BaseMapper<IcsQuestionSulotionEntity> {

	List<IcsQuestionSulotionDTO> listPage(IcsQuestionSulotionVO params);
	
}
