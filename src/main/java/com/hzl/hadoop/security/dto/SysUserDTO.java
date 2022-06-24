package com.hzl.hadoop.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户信息表
 * 返回实体
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 11:01:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String id;

	/**
	 * 用户名-真实姓名
	 */
	private String name;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 头像url
	 */
	private String avatar;

	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 用户状态，1为登陆，0为离线
	 */
	private Integer status;

	/**
	 * 行版本号，用来处理锁
	 */
	private Long versionNum;

	/**
	 * 记录创建人
	 */
	private Long createBy;

	/**
	 * 记录创建日期
	 */
	private LocalDateTime createTime;

	/**
	 * 记录更新人
	 */
	private Long updateBy;

	/**
	 * 记录更新日期
	 */
	private LocalDateTime updateTime;

	/**
	 * 租户id
	 */
	private Long tenantId;

	/**
	 * 是否注销，1位注销，0为正常
	 */
	private Integer deleted;


}
