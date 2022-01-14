package com.hzl.hadoop.gp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 股票信息列表-存储所有需要爬取的股票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-14 16:50:07
 */
public interface GpInfoService extends IService<GpInfoEntity> {

	PageInfo queryPage(GpInfoEntity params, int start, int pageSize);
}

