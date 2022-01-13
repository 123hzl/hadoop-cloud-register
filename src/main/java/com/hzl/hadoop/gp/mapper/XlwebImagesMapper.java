package com.hzl.hadoop.gp.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzl.hadoop.gp.entity.XlwebImagesEntity;


/**
 * 新浪微博爬虫-用户图片库
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-12-23 13:12:30
 */
@Mapper
public interface XlwebImagesMapper extends BaseMapper<XlwebImagesEntity> {
	
}
