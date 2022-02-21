package com.hzl.hadoop.gp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hzl.hadoop.gp.entity.GpXlPercentEntity;

/**
 * 时时成交价和具体价格的成交量，只存储当天的数据。前天的会自动清除
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-21 17:02:10
 */
public interface GpXlPercentService extends IService<GpXlPercentEntity> {

	PageInfo queryPage(GpXlPercentEntity params, int start, int pageSize);

	Boolean init(String gpcode);

	Boolean remove(String gpcode);

}

