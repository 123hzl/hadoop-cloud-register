package com.hzl.hadoop.security.vo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * description
 * 查询当前登陆的用户信息
 *
 * @author hzl 2022/06/08 10:19 AM
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfoVO {

	private String userid;

	private String username;

	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 用户名-真实姓名，可以为空
	 */
	private String realName;

	private LocalDateTime creationDate;

	private Long createdBy;

	private LocalDateTime lastUpdateDate;

	private Long lastUpdatedBy;

	private Long objectVersionNumber;

	private Long tenantId;


}
