package com.hzl.hadoop.security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import lombok.*;

import javax.persistence.Table;

/**
 * description
 *
 * @author hzl 2021/09/09 5:11 PM
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "sys_user")
public class SysUser extends BaseEntity {


	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 用户名
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
	 * 是否注销，true为已经注销，false为正常
	 */
	private Boolean deleted;

}
