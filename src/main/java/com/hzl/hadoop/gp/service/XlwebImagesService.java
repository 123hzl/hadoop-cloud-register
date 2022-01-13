package com.hzl.hadoop.gp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-12-23 13:12:30
 */
public interface XlwebImagesService extends IService<XlwebImagesEntity> {

	PageInfo queryPage(XlwebImagesEntity params, int start, int pageSize);

	Boolean getAllImagePage(Long uid);
}

