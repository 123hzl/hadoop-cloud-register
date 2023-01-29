package com.hzl.hadoop.algorithm;

import sun.reflect.generics.scope.Scope;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * description
 *
 * @author hzl 2022/11/05 2:21 PM
 */
public class Huawei {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// 注意 hasNext 和 hasNextLine 的区别
		int i=0;
		int temp=0;
		StringBuffer result=new StringBuffer();
		while (in.hasNext()) { // 注意 while 处理多个 case

			i++;
			String a = in.next();
			System.out.println(a);
			result.append(a);

			if(i==8){
				System.out.println(result.toString());
				result.setLength(0);
				temp=0;
			}else{
				temp++;
			}
		}
		System.out.println(temp);
		if(temp>0){
			while(temp<=8){
				temp++;
				result.append(0);
			}
			System.out.println(result.toString());
		}
	}
}
