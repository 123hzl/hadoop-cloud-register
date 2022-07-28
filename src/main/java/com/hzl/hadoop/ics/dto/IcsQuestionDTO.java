package com.hzl.hadoop.ics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


import java.io.Serializable;



/**
 * 智能客服-问题记录表
 * 返回实体
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IcsQuestionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    			/**
		 * 
		 */
		private String id;
		
    				/**
		 * 问题
		 */
		private String question;
	
    				/**
		 * 答案
		 */
		private String solution;
	
    				/**
		 * 询问次数
		 */
		private Long askNum;
	
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
