package com.hzl.hadoop.security.dataobject;

import com.hzl.hadoop.config.mybatis.BaseEntity;
import com.hzl.hadoop.constant.BaseDO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

	@Id
	@Column(name = "id")
	//主键由数据库生成
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	/*
	* 电话号码
	* */
	private String phone;




}
