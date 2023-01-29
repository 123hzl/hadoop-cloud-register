package com.hzl.hadoop.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * description
 * 需要多线程的方法上需要加上@Async("taskExecutor") 参考：com.hzl.hadoop.app.service.impl.RedisServiceImpl
 * 注意：被注解@Async("taskExecutor")修饰的方法需要放到spring上下文
 *
 * @author hzl 2020/05/07 7:56 PM
 */
@Configuration
@EnableAsync  // 启用异步任务
@Slf4j
public class AsyncConfiguration {

	// 声明一个线程池(并指定线程池的名字)
	@Bean("taskExecutor")
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程数5：线程池创建时候初始化的线程数
		executor.setCorePoolSize(5);
		//最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
		executor.setMaxPoolSize(5);
		//缓冲队列500：用来缓冲执行任务的队列
		executor.setQueueCapacity(500);
		//允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
		executor.setKeepAliveSeconds(60);
		//线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
		executor.setThreadNamePrefix("DailyAsync-");
		// 线程池对拒绝任务(无线程可用)的处理策略 ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 设置任务的装饰
//		executor.setTaskDecorator(new ContextCopyingDecorator());
		executor.initialize();
		return executor;
	}


	/*
	 * 等集成springsecurity后恢复，作用，将springsecurity上下文传递到自线程
	 * */
//	static class ContextCopyingDecorator implements TaskDecorator {
//		@Nonnull
//		@Override
//		public Runnable decorate(@Nonnull Runnable runnable) {
//			RequestAttributes context = RequestContextHolder.currentRequestAttributes();
//			SecurityContext securityContext = SecurityContextHolder.getContext();
//			return () -> {
//				try {
//					RequestContextHolder.setRequestAttributes(context);
//					SecurityContextHolder.setContext(securityContext);
//					runnable.run();
//				} finally {
//					SecurityContextHolder.clearContext();
//					RequestContextHolder.resetRequestAttributes();
//				}
//			};
//		}
//	}

}
