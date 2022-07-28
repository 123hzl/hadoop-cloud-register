package com.hzl.hadoop.ics.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.ics.entity.IcsKeyWordEntity;
import com.hzl.hadoop.ics.dto.IcsKeyWordDTO;
import com.hzl.hadoop.ics.vo.IcsKeyWordVO;
import java.util.List;
/**
 * 智能客服-问题分词库
 * 
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@Mapper
public interface IcsKeyWordMapper extends BaseMapper<IcsKeyWordEntity> {

	List<IcsKeyWordDTO> listPage(IcsKeyWordVO params);
	
}
