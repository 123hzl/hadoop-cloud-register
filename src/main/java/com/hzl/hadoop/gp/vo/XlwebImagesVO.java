package com.hzl.hadoop.gp.vo;


import com.hzl.hadoop.gp.entity.XlwebImagesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-12-23 13:12:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XlwebImagesVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String originalImageUrls="https://wx1.sinaimg.cn/orj1080/";


	/**
	 *
	 */
	private Long id;
	/**
	 * 微博用户id
	 */
	private String uid;
	/**
	 * 微博图片id
	 */
	private String pid;
	/**
	 * 原始图片地址
	 */
	private String originalImageUrl;


	/**
	 * 租户id
	 */
	private Long tenantId;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 最后更新人
	 */
	private Long updateBy;
	/**
	 * 最后更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 版本号
	 */
	private Integer versionNum;

	/*
	 * 分页面参数
	 * */
	private String sinceid;

	private List<String> pids;

	public List<XlwebImagesEntity> convert(){
		List<XlwebImagesEntity> list = new ArrayList<>();
		pids.forEach(a->{
			list.add( XlwebImagesEntity.builder()
					.pid(a)
					.uid(uid)
					.originalImageUrl(originalImageUrls+a+".jpg")
					.build());

		});

		return list;
	}

}
