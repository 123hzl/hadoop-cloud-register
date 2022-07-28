package com.hzl.hadoop.ics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


import java.io.Serializable;



/**
 * 智能客服-问题搜索记录表
 * 返回实体
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-07-28 13:52:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IcsQuestionSearchLogDTO implements Serializable {
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
		 * 是否回答正确
		 */
		private Integer isHit;
	
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
