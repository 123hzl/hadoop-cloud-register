package com.hzl.hadoop.executor.threadservice;

import com.hzl.hadoop.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 * run方法没有返回值
 * Runnable和Callable都可以理解为任务，里面封装这任务的具体逻辑，用于提交给线程池执行，区别在于Runnable任务执行没有返回值，
 * 且Runnable任务逻辑中不能通过throws抛出cheched异常(但是可以try catch)，
 * 而Callable可以获取到任务的执行结果返回值且抛出checked异常。
 * @author hzl 2021/08/27 5:29 PM
 */
@Slf4j
public class RunnableService implements Runnable {

	private int num;

	public RunnableService(int num) {
		this.num = num;
	}

	public RunnableService() {
	}

	@Override
	public void run() {
		//业务逻辑
		System.out.println("执行次数"+num +Thread.currentThread().getName());
		if(num==3){
			throw new CommonException("异常");
		}
	}
}
