package com.hzl.hadoop.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description
 *
 * @author hzl 2022/06/14 5:56 PM
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessVO {

	/**
	 * 当前登陆的用户名字
	 */
	private String currentAuthority;

	/*
	* ok成功，error失败
	* */
	private String status;

}
