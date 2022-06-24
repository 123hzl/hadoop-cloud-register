package com.hzl.hadoop.interfacemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 内部接口管理
 * 返回实体
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 12:29:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceManageDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String id;

	/**
	 *
	 */
	private String serviceName;

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 接口地址
	 */
	private String url;

	/**
	 * 请求方式
	 */
	private String method;

	/**
	 * 是否需要登陆认证
	 */
	private Boolean isLogin;

	/**
	 * 接口描述
	 */
	private String description;

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
