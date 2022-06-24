package com.hzl.hadoop.userlog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 请求日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-11-19 16:18:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("request_logs")
public class RequestLogsEntity extends BaseEntity{

	/**
	 *
	 */
	private Long id;
	/**
	 *
	 */
	private String ip;
	/**
	 *
	 */
	private String method;
	/**
	 *
	 */
	private String url;
	/**
	 *
	 */
	private String city;
	/**
	 * 耗时纳秒
	 */
	private Long consumeTime;
	/**
	 *
	 */
	private String requestParam;
	/**
	 * 是否成功返回
	 */
	private Boolean response;
}
