package com.hzl.hadoop.gp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzl.hadoop.gp.entity.BaiduImagesEntity;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-17 17:47:41
 */
public interface BaiduImagesService extends IService<BaiduImagesEntity> {

	PageInfo queryPage(BaiduImagesEntity params, int start, int pageSize);

	Boolean getImage(String searchName,int size);
}

