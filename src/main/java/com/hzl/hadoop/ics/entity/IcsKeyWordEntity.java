package com.hzl.hadoop.ics.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hzl.hadoop.config.mybatis.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 智能客服-问题分词库
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ics_key_word")
public class IcsKeyWordEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 分词
	 */
	@TableField(value = "`key`")
	private String key;
	/**
	 * 问题表id
	 */
	private Long icsQuestionId;
	/**
	 * 分词器类型，用于方便后期替换分词器
	 */
	private Integer keyWordMachine;

}
