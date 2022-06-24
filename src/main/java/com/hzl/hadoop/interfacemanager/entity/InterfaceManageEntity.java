package com.hzl.hadoop.interfacemanager.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 内部接口管理
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 12:29:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("interface_manage" )
public class InterfaceManageEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 *服务名称
	 */
	private String serviceName;
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

}
