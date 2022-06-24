package com.hzl.hadoop.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户角色关系表
 * 返回实体
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String id;

	/**
	 * 角色名称
	 */
	private Long rid;

	/**
	 * 角色描述
	 */
	private Long uid;

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
