package com.hzl.hadoop.security.vo;

import com.hzl.hadoop.constant.BaseDO;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

/**
 * description
 *
 * @author hzl 2021/09/09 9:21 PM
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysUserVO{


	private Long id;

	private String realname;

	private String username;

	private String password;

	/**
	 * 头像url
	 */
	private String avatar;

	/**
	 * 用户状态，1为登陆，0为离线
	 */
	private Integer status;

	/**
	 * 是否注销，true为已经注销，false为正常
	 */
	private Boolean deleted;

	/*
	 * 电话号码
	 * */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	private LocalDateTime createTime;

	private Long createBy;

	private LocalDateTime updateTime;

	private Long updateBy;

	private Long versionNum;
	/**
	 * 租户id
	 */
	private Long tenantId;

}
