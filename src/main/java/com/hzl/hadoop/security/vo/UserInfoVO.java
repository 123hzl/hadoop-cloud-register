package com.hzl.hadoop.security.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
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

	private LocalDateTime creationDate;

	private Long createdBy;

	private LocalDateTime lastUpdateDate;

	private Long lastUpdatedBy;

	private Long objectVersionNumber;

	private Long tenantId;

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

	private String signature;

	private String title;

	private String group;

}
