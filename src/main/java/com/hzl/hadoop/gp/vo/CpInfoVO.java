package com.hzl.hadoop.gp.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 彩票对象
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-26 16:04:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 期号
	 */
	private String lotteryDrawNum;
	/**
	 * 号码1
	 */
	private Integer first;
	/**
	 * 2
	 */
	private Integer second;
	/**
	 * 3
	 */
	private Integer three;
	/**
	 * 4
	 */
	private Integer four;
	/**
	 * 5
	 */
	private Integer five;
	/**
	 * 6
	 */
	private Integer six;
	/**
	 * 7
	 */
	private Integer seven;
	/**
	 * 开奖日期
	 */
	private LocalDate lotteryDrawTime;
	/**
	 * 租户id
	 */
	private Long tenantId;
	/**
	 * 创建人
	 */
	private Long createBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 最后更新人
	 */
	private Long updateBy;
	/**
	 * 最后更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 版本号
	 */
	private Integer versionNum;

}
