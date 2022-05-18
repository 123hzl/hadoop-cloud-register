package com.hzl.hadoop.executor;

import com.hzl.hadoop.executor.threadservice.RunnableService;
import com.hzl.hadoop.executor.threadservice.SupplyAsyncService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * description
 * 参考：https://www.jianshu.com/p/6bac52527ca4
 *
 * @author hzl 2021/09/15 5:02 PM
 */
public class CompletableFutureUtils {

	//有返回值
	public static void supplyAsync() {
		List<CompletableFuture> completableFutures = new ArrayList<>();

		//定义一个Runnable对象
		for (int i = 0; i < 900; i++) {
			//使用自定义线程池
			SupplyAsyncService supplyAsyncService = new SupplyAsyncService();

			CompletableFuture completableFuture = CompletableFuture.supplyAsync(supplyAsyncService, SingleExecutor.getInstance());


			//以下方法按照写的顺序执行，除了异常方法

			// 如果执行异常获取异常信息:
			completableFuture.exceptionally(new Function<Throwable, Void>() {
				@Override
				public Void apply(Throwable throwable) {
					throwable.printStackTrace();
					return null;
				}
			});
			//获取线程执行完成的结果
			completableFuture.thenAccept((result) -> {
				System.out.println("thenAccept=" + result);
			});

			//执行完线程继续执行
			completableFuture.whenComplete(new BiConsumer<Boolean, Throwable>() {
				@Override
				public void accept(Boolean t, Throwable action) {

					System.out.println("whenComplete=" + t + Thread.currentThread().getName());
				}
			});
			//执行完线程继续执行,并且把任务交给线程池,不指定线程池，就用默认的
			completableFuture.whenCompleteAsync(new BiConsumer<Boolean, Throwable>() {
				@Override
				public void accept(Boolean t, Throwable action) {

					System.out.println("whenCompleteAsync=" + t + Thread.currentThread().getName());
				}
			}, SingleExecutor.getInstance());

			//一个线程依赖另一个线程，可以通过thenApply把俩线程串联，被依赖线程异常，不会执行thenApply
			completableFuture.thenApply(new Function<Boolean, Boolean>() {
				@Override
				public Boolean apply(Boolean t) {
					System.out.println("thenApply=" + t);
					return t;
				}
			});

			//handle 是执行任务完成时对结果的处理。
			completableFuture.handle(new BiFunction<Boolean, Throwable, Boolean>() {
				@Override
				public Boolean apply(Boolean param, Throwable throwable) {
					//获取线程的错误信息
					throwable.printStackTrace();
					System.out.println("handle=" + param);
					return param;
				}
			});
			completableFuture.handleAsync(new BiFunction<Boolean, Throwable, Boolean>() {
				@Override
				public Boolean apply(Boolean param, Throwable throwable) {
					System.out.println("handleAsync=" + param);
					return param;
				}
			}, SingleExecutor.getInstance());

			//不关系线程的返回结果
			completableFuture.thenRun(() -> {
				System.out.println("thenRun ...");
			});
			completableFutures.add(completableFuture);
		}

		//thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
		/*for(int i=0;i<completableFutures.size();i++){
			CompletableFuture<String> result=completableFutures.get(0).thenCombine(completableFutures.get(i), new BiFunction() {
				@Override
				public Object apply(Object o, Object o2) {
					return o.toString()+o2.toString();
				}
			});
			try {
				System.out.println(result.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}*/
		//等待所有线程完成后执行
		CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
		System.out.println(1111);

		//关闭线程池
		SingleExecutor.getInstance().shutdown();
	}


	//没有返回值
	public static void runAsync() {
		//定义一个Runnable对象
		RunnableService runnableService = new RunnableService();
		for (int i = 0; i < 199; i++) {
			//使用自定义线程池
			CompletableFuture completableFuture = CompletableFuture.runAsync(runnableService, SingleExecutor.getInstance());

			//如果执行成功
			int finalI = i;
			completableFuture.thenAccept((result) -> {
				System.out.println("CompletableFuture.runAsync执行成功" + finalI);
			});

//		//如果执行异常
//		completableFuture.exceptionally((e)->{
//
//		});
		}

	}


	//业务方法提供给supplyAsync使用

	public static void main(String args[]) {
		//runAsync();
		supplyAsync();
	}
}
