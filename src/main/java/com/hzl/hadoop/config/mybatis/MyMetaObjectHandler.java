package com.hzl.hadoop.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hzl.hadoop.security.service.impl.CustomUserDetails;
import com.hzl.hadoop.security.utils.DetailHepler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * description
 * 自动填充
 * https://baomidou.com/guide/auto-fill-metainfo.html
 *
 * @author hzl 2021/11/03 6:09 PM
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


	@Override
	public void insertFill(MetaObject metaObject) {


		CustomUserDetails customUserDetails = DetailHepler.getUserDetails();
		Long userId = 0L;
		Long tenantId = 0L;
		if (customUserDetails != null) {
			userId = customUserDetails.getUserId();
			tenantId = customUserDetails.getTenantId();

		}

		this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
		this.strictInsertFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);

		this.strictInsertFill(metaObject, "createBy", Long.class,userId);
		this.strictInsertFill(metaObject, "tenantId", Long.class, tenantId);
		this.strictInsertFill(metaObject, "versionNum", Integer.class, 0);
		this.strictInsertFill(metaObject, "updateBy", Long.class, userId);


	}

	@Override
	public void updateFill(MetaObject metaObject) {


		CustomUserDetails customUserDetails = DetailHepler.getUserDetails();
		Long userId = 0L;
		Long tenantId = 0L;
		if (customUserDetails != null) {
			userId = customUserDetails.getUserId();
			tenantId = customUserDetails.getTenantId();
		}

		this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);

		this.strictUpdateFill(metaObject, "versionNum", Integer.class, (Integer) metaObject.getValue("versionNum") + 1);

		this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);

		this.strictUpdateFill(metaObject, "tenantId", Long.class, tenantId);

	}
}
