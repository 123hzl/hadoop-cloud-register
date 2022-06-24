package com.hzl.hadoop.userlog.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请求日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-19 16:18:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogsVO{

	/**
	 *
	 */
	private Long id;
	/**
	 *
	 */
	private String ip;
	/**
	 *
	 */
	private String url;
	/**
	 *
	 */
	private String requestParam;

	/**
	 *操作类型
	 */
	private String method;

	/**
	 * ip地址
	 *
	 * */
	private String city;
	/**
	 *耗时
	 */
	private Long consumeTime;


	/**
	 * 是否成功返回
	 */
	private Boolean response;
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
