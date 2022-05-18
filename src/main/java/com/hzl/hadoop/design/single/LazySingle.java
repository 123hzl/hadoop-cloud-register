package com.hzl.hadoop.design.single;

/**
 * description
 * 懒汉模式：非线程安全，读线程情况下，会返回多个对象：假设线程1判断lazySingle为空      ，线程2也同时判断为空，此时会分别创建对象返回，解决方法在创建对象的地方加上锁
 * @author hzl 2021/09/02 1:19 PM
 */
public class LazySingle {

	//static保证全局唯一
	private static LazySingle lazySingle;

	//构造私有其他类无法通过new创建LazySingle对象
	private LazySingle(){

	}

	//懒汉模式先判断对象是否为空，
	public static LazySingle getInstance(){
		if(lazySingle==null){
			try {
				//模拟业务逻辑导致的时间损耗
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lazySingle=new LazySingle();
			return lazySingle;
		}else{
			return lazySingle;
		}
	}

}
