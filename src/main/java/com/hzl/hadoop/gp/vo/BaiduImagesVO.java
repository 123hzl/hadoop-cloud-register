package com.hzl.hadoop.gp.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 新浪微博爬虫-用户图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-17 17:47:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaiduImagesVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 微博图片id
	 */
	private String searchName;
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
	/**
	 * 是否下载过
	 */
	private Boolean isDown;

}
