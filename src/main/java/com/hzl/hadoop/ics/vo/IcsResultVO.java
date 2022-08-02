package com.hzl.hadoop.ics.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description
 *
 * @author hzl 2022/08/02 9:56 AM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IcsResultVO {

	/**
	* 命中率
	* */
	private Integer hitRate;


	/**
	 * 关键词
	 * */
	private String key;

	/**
	 * ics_question主键
	 * */
	private Long icsQuestionId;


	/**
	 * 问题
	 * */
	private String question;


	/**
	 * 答案
	 * */
	private String solution;


	/**
	 * 询问次数
	 * */
	private Integer askNum;
}
