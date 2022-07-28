package com.hzl.hadoop.ics.entity;

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
 * 智能客服-问题搜索记录表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ics_question_search_log")
public class IcsQuestionSearchLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 问题
	 */
	private String question;
	/**
	 * 是否回答正确
	 */
	private Integer isHit;

}
