package com.hzl.hadoop.gp.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.gp.entity.GpInfoEntity;


/**
 * 股票信息列表-存储所有需要爬取的股票对象
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-14 16:50:07
 */
@Mapper
public interface GpInfoMapper extends BaseMapper<GpInfoEntity> {
	
}
