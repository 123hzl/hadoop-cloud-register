package com.hzl.hadoop.userlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 请求日志
 * 返回实体
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 13:29:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String id;

	/**
	 *
	 */
	private String ip;

	/**
	 *
	 */
	private String method;

	/**
	 *
	 */
	private String url;

	/**
	 *
	 */
	private String city;

	/**
	 * 耗时纳秒
	 */
	private Long consumeTime;

	/**
	 *
	 */
	private String requestParam;

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
