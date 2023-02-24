package com.hzl.hadoop.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author hzl 2021/07/26 4:12 PM
 */
@Component
@Slf4j
public class BeanFactoryUtil implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		BeanFactoryUtil.beanFactory=beanFactory;

	}

	public static <T> T getBean(Class<T> clazz) {
		if (beanFactory == null) {
			log.debug("applicationContext是空的");
		} else {
			log.debug("applicationContext不是空的");
		}
		return BeanFactoryUtil.getBean(clazz);
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}
}
