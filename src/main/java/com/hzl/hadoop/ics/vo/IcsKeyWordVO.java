package com.hzl.hadoop.ics.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;




/**
 * 智能客服-问题分词库
 * 请求实体
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IcsKeyWordVO implements Serializable {
	private static final long serialVersionUID = 1L;

    		/**
		 * 
		 */
		private Long id;
    		/**
		 * 分词
		 */
		private String key;
    		/**
		 * 问题表id
		 */
		private Long icsQuestionId;
    		/**
		 * 分词器类型，用于方便后期替换分词器
		 */
		private Integer keyWordMachine;
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
