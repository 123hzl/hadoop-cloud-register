package com.hzl.hadoop.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description
 * 线程池对象设置成单利，全局唯一,不能通过静态内部类方式进行声明，因为静态内部类只加载一次，当线程池被关闭后，不会重新初始化
 *
 * @author hzl 2021/09/15 6:07 PM
 */
@Slf4j
public class SingleExecutor {

	private static ThreadPoolExecutor executor;


	/**
	 *
	 * 正常通过接口方式调用的线程池，如果会多次调用不建议关闭线程池，不然会出现有人正在用，突然关闭的情况
	 * @author hzl 2021-11-19 3:04 PM
	 * @return
	 */
	public static ThreadPoolExecutor getInstance() {
		//只有为空才加锁，不然直接加锁浪费性能
		if (executor == null) {
			synchronized (SingleExecutor.class) {
				if (executor == null) {
					log.info("线程池初始化----SingleExecutor");
					executor = new ThreadPoolExecutor(3
							, 50, 60L
							, TimeUnit.SECONDS
							, new ArrayBlockingQueue(500)
							, new ThreadFactoryService()
							, new ThreadPoolExecutor.CallerRunsPolicy()) {
						@Override
						protected void beforeExecute(Thread t, Runnable r) {
							//System.out.println("任务执行前" + t.getName());
						}

						@Override
						protected void afterExecute(Runnable r, Throwable t) {
							//System.out.println("任务执行后" + Thread.currentThread().getName());
						}

						@Override
						protected void terminated() {
							log.info("线程池结束");
						}
					};
				}
			}
		}
		return executor;
	}

	private SingleExecutor() {

	}


}
