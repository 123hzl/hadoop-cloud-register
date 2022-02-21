package com.hzl.hadoop.gp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.gp.entity.CpInfoEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 彩票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-26 16:04:50
 */
public interface CpInfoService extends IService<CpInfoEntity> {

	PageInfo queryPage(CpInfoEntity params, int start, int pageSize);

	Boolean init();
}

