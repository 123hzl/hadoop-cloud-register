package com.hzl.hadoop.gp.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 股票信息列表-存储所有需要爬取的股票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-14 16:50:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 股票名称
	 */
	private String gpName;
	/**
	 * 股票名称
	 */
	private String gpCode;
	/**
	 * 是否爬取
	 */
	private Boolean isCreep;
	/**
	 * 是否进行买入卖出通知
	 */
	private Boolean isNotify;
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

}
