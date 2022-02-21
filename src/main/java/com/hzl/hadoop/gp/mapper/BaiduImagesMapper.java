package com.hzl.hadoop.gp.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.gp.entity.BaiduImagesEntity;


/**
 * 新浪微博爬虫-用户图片库
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-17 17:47:41
 */
@Mapper
public interface BaiduImagesMapper extends BaseMapper<BaiduImagesEntity> {
	
}
