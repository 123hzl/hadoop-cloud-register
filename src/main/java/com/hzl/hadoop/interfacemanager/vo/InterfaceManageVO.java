package com.hzl.hadoop.interfacemanager.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-16 14:05:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceManageVO {
	/**
	 *
	 */
	private String id;
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
	 * 服务名称
	 */
	private String serviceName;

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
