package com.hzl.hadoop.ics.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.ics.entity.IcsQuestionSearchLogEntity;
import com.hzl.hadoop.ics.dto.IcsQuestionSearchLogDTO;
import com.hzl.hadoop.ics.vo.IcsQuestionSearchLogVO;
import java.util.List;
/**
 * 智能客服-问题搜索记录表
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:46
 */
@Mapper
public interface IcsQuestionSearchLogMapper extends BaseMapper<IcsQuestionSearchLogEntity> {

	List<IcsQuestionSearchLogDTO> listPage(IcsQuestionSearchLogVO params);
	
}
