package com.hzl.hadoop.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * description
 * 获取容器中的bean
 * 这个类就可以方便的获得ApplicationContext中(spring上下文中)的所有bean
 *
 * @author hzl 2020/01/10 6:29 PM
 */
@Component
@Slf4j
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;//声明一个静态变量保存

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextUtil.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (applicationContext == null) {
			log.debug("applicationContext是空的");
		} else {
			log.debug("applicationContext不是空的");
		}
		return applicationContext.getBean(clazz);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
