package com.hzl.hadoop.ics.mapper;

import com.hzl.hadoop.ics.vo.IcsResultVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.ics.entity.IcsQuestionEntity;
import com.hzl.hadoop.ics.dto.IcsQuestionDTO;
import com.hzl.hadoop.ics.vo.IcsQuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 智能客服-问题记录表
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@Mapper
public interface IcsQuestionMapper extends BaseMapper<IcsQuestionEntity> {

	List<IcsQuestionDTO> listPage(IcsQuestionVO params);

	List<IcsResultVO> searchQuestion(@Param("words") List<String> words);
}
