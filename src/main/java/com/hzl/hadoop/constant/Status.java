package com.hzl.hadoop.constant;

import java.util.concurrent.TimeUnit;

/**
 * description
 * 状态枚举，redis工具类使用
 *
 * @author hzl 2020/01/17 8:55 PM
 */
public class Status {

	/**
	 * 过期时间相关枚举
	 */
	public enum ExpireEnum {
		//未读消息的有效期为30天
		UNREAD_MSG(30L, TimeUnit.DAYS);

		/**
		 * 过期时间
		 */
		private Long time;
		/**
		 * 时间单位
		 */
		private TimeUnit timeUnit;

		ExpireEnum(Long time, TimeUnit timeUnit) {
			this.time = time;
			this.timeUnit = timeUnit;
		}

		public Long getTime() {
			return time;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}
	}


}
