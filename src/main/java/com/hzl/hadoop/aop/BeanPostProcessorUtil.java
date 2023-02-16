package com.hzl.hadoop.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * description
 * BeanPostProcessor也称为Bean后置处理器，它是Spring中定义的接口，
 * 在Spring容器的创建过程中（具体为Bean初始化前后）会回调BeanPostProcessor中定义的两个方法
 * <p>
 * 所有bean的初始化前后都会执行该类,不需要为每个bean单独实现BeanPostProcessor
 * <p>
 * 执行顺序BeanPostProcessor>InitializingBean
 *
 * @author hzl 2020/01/13 6:08 PM
 */
@Component
@Slf4j
public class BeanPostProcessorUtil implements BeanPostProcessor {

	@Nullable
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		log.debug("bean初始化前" + beanName);
		return bean;
	}

	@Override
	@Nullable
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		log.debug("bean初始化后" + beanName);
		return bean;
	}


	private static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH)+1;
		return month;
	}

	public static void main(String args[]){

		Date date=new Date();
		System.out.println(getMonth(date));
	}
}
