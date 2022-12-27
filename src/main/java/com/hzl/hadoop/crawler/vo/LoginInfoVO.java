package com.hzl.hadoop.crawler.vo;

import lombok.*;

/**
 * description
 * 用户登陆信息存储，cookie，token，userinfo
 * @author hzl 2022/12/14 4:33 PM
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class LoginInfoVO {


	String cookies;

	String localStorage;

}
