package com.hzl.hadoop.gp.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.gp.entity.GpXlPercentEntity;


/**
 * 时时成交价和具体价格的成交量，只存储当天的数据。前天的会自动清除
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-21 17:02:10
 */
@Mapper
public interface GpXlPercentMapper extends BaseMapper<GpXlPercentEntity> {
	
}
