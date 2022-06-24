package com.hzl.hadoop.security.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hzl.hadoop.config.mybatis.BaseEntity;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 用户角色关系表
 *
 * @author huangzhongliang
 * @email sunlightcs@gmail.com
 * @date 2022-06-21 14:58:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role" )
public class SysUserRoleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    		/**
		 * 
		 */
	    private Long id;
    		/**
		 * 角色id
		 */
	    private Long rid;
    		/**
		 * 用户id
		 */
	    private Long uid;

    
}
