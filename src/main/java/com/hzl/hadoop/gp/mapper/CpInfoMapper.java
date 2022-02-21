package com.hzl.hadoop.gp.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.gp.entity.CpInfoEntity;


/**
 * 彩票对象
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-26 16:04:50
 */
@Mapper
public interface CpInfoMapper extends BaseMapper<CpInfoEntity> {
	
}
