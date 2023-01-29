package com.hzl.hadoop.design.single;

/**
 * description
 * 懒汉模式（双检锁方式）：线程安全样例
 * @author hzl 2021/09/02 1:27 PM
 */
public class SecurityLazySingle {

	//static保证全局唯一
	//对象加上了volatile关键字是为了保证变量的可见性，防止指令重排序
	//第二个线程拿到的可能是半实列化的对象，所以要加volatile防止指令重排序
	private volatile static SecurityLazySingle lazySingle;

	//构造私有其他类无法通过new创建LazySingle对象
	private SecurityLazySingle(){

	}

	public static SecurityLazySingle getSecurityInstance(){
		//只有为空才加锁，不然直接加锁浪费性能
		if(lazySingle==null){
			try {
				//模拟业务逻辑导致的时间损耗
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (SecurityLazySingle.class){
				if(lazySingle==null){
					lazySingle=new SecurityLazySingle();
				}
			}
		}
		return lazySingle;
	}

}
