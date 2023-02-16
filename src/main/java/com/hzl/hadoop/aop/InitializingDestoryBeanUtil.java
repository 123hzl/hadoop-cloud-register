package com.hzl.hadoop.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * description
 * 1:InitializingBean接口，当BeanFactory将bean创建成功，并设置完成所有它们的属性后，我们想在这个时候去做出自定义的反应，
 * 比如检查一些强制属性是否被设置成功，这个时候我们可以让我们的bean的class实现InitializingBean接口，以被触发
 * <p>
 * 2:BeanFactoryAware接口：实现该接口后可以获取BeanFactory的实例引用
 * 就可以让当前实现类Bean拥有访问Spring容器的能力。缺点：导致代码与spring的api耦合在一起
 * <p>
 * InitializingBean需要每个类都实现，实现的类才能执行afterPropertiesSet方法
 * <p>
 * 初始化执行顺序	@PostConstruct>InitializingBean
 * 销毁执行顺序 @PreDestroy>DisposableBean
 *
 * @Lazy 可以将Bean对象的创建延迟到第一次使用Bean的时候
 *
 * @author hzl 2020/01/13 4:30 PM
 */
@Component
@Slf4j
@Lazy(value = true)
public class InitializingDestoryBeanUtil implements InitializingBean, DisposableBean {

	/**
	 * <p>
	 * 该方法会在bean的所有属性被设置完成（包括各种Aware的设置，如BeanFactoryAware， ApplicationContextAware等）后由容器（BeanFactory）调用
	 * </p>
	 *
	 * @author hzl 2020/01/13 4:31 PM
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("通过InitializingBean实现在bean初始化完成后执行");
	}


	/**
	 * <p>
	 * 关闭应用的时候bean才被销毁，而不是结束调用，因为默认spring的bean是单例模式，只有原型模式是每次都生成一个
	 * 新的对象，可以通过@Scope来修改具体的模式
	 * </p>
	 *
	 * @author hzl 2020/01/14 10:07 AM
	 */
	@Override
	public void destroy() throws Exception {
		log.debug("通过实现DisposableBean,销毁bean");
	}


	/**
	 * 自定义的初始化方法
	 */
	@PostConstruct
	public void init() {
		log.debug("通过@PostConstruct注解进行初始化，反射性能没有InitializingBean好");
	}

	/**
	 * 自定义的销毁方法
	 */
	@PreDestroy
	public void detory() {
		log.debug("通过注解@PreDestroy进行销毁，反射性能没有DisposableBean好");
	}
}
