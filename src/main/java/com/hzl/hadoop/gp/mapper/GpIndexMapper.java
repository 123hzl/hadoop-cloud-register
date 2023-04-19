package com.hzl.hadoop.gp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.gp.entity.GpIndexEntity;
import com.hzl.hadoop.gp.vo.GpIndexVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 股票交易指标天维度，价格，成交量波动情况
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2023-04-07 21:45:04
 */
@Mapper
public interface GpIndexMapper extends BaseMapper<GpIndexEntity> {


	List<GpIndexEntity> forecast(GpIndexVO gpIndexVO);


}
