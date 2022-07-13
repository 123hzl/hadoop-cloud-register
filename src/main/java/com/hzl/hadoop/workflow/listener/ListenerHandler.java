package com.hzl.hadoop.workflow.listener;

import com.hzl.hadoop.aop.ApplicationContextUtil;
import com.hzl.hadoop.exception.CommonException;
import com.hzl.hadoop.workflow.entity.WorkflowListenerEntity;
import com.hzl.hadoop.workflow.service.WorkflowListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * description
 *
 * @author hzl 2022/07/12 3:09 PM
 */
@Component
public class ListenerHandler {

	@Autowired
	WorkflowListenerService workflowListenerService;
	public Boolean handle(Long processId,Long listenerId){
		//根据监听执行返回结果，判断是否执行插入审批信息逻辑
		WorkflowListenerEntity listenerEntitie=workflowListenerService.getById(listenerId);
		//根据监听类，进行反射处理
		try {
			Object listenerObject= ApplicationContextUtil.getBean(Class.forName(listenerEntitie.getListenerClass()));
			Class<?> clazz = Class.forName(listenerEntitie.getListenerClass());
			Method method = clazz.getMethod("listener",Long.class);
			method.invoke(listenerObject,processId);

		} catch (ClassNotFoundException e) {
			throw new CommonException("监听类不存在"+listenerEntitie.getListenerClass());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return true;
	}

}
