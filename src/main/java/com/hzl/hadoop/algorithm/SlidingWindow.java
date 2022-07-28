package com.hzl.hadoop.algorithm;

import java.util.HashMap;
import java.util.HashSet;

/**
 * description
 * 滑动窗口 https://zhuanlan.zhihu.com/p/422908736
 * @author hzl 2021/10/22 11:27 AM
 */
public class SlidingWindow {

	/**
	 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。
	 * 如果不存在符合条件的连续子数组，返回 0。
	 *
	 * @param s 需要查询的结果
	 * @param arry 数据来源
	 * @author hzl 2021-10-22 11:31 AM
	 * @return
	 */

	public static void slidingWindow(int s,int[] arry){

	}

	/**
	 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。

	 *  异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
       固定窗口
	 *  输入: s = "cbaebabacd", p = "abc"
	 *  输出: [0,6]
	 * @param s 数据源
	 * @param p 需要查询的结果
	 * @author hzl 2021-10-22 11:31 AM
	 * @return
	 */

	public static void slidingWindow1(String s,String p){

		char[] source=s.toCharArray();
		char[] search=p.toCharArray();

		//需要查询的字符串的ascii和
		HashSet<Character> pSet=new HashSet<>(search.length);
		for(char a:search){
			pSet.add(a);
		}
		System.out.println("数据源p"+pSet.toString());

 		int sourceLength=source.length;
 		int searchLength=search.length;

		//外层循环进行窗口滑动
		for(int i=0;i<=sourceLength-searchLength;i++){
			//内层循环，循环窗口中的数据
			//窗口中字符的ascii和
			//满足条件的字符串个数
			int validNum=0;
			for(int j=i;j<searchLength+i;j++){
				if(pSet.contains(source[j])){
					validNum++;
				}else{
					break;
				}

			}
			if(validNum==searchLength){
				System.out.println(i);
			}

		}
	}

	/**
	 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
       动态窗口   todo 仍然有问题，重复字符如果t是aab，那么aab  bba都被匹配成功，这是有问题的，bba不应该匹配成功，上面的也有这样的问题，待进一步优化
	 *  输入: s = "cbaebabacd", p = "abc"
	 *  输出: [0,6]
	 * @param s 数据源
	 * @param p 需要查询的结果
	 * @author hzl 2021-10-22 11:31 AM
	 * @return
	 */

	public static void slidingWindow2(String s,String p){

		char[] source=s.toCharArray();
		char[] search=p.toCharArray();

		//需要查询的字符串的ascii和
		HashSet<Character> pSet=new HashSet<>(search.length);

		for(char a:search){
			pSet.add(a);
		}


		int sourceLength=source.length;
		int searchLength=search.length;
		//外层循环进行窗口滑动
		for(int i=0;i<=sourceLength-searchLength;i++){
			//内层循环，循环窗口中的数据
			//满足条件的字符串个数
			int validNum=0;
			StringBuilder result=new StringBuilder();
			for(int j=i;j<sourceLength;j++){
				System.out.println("大小"+j);
				result.append(source[j]);
				if(pSet.contains(source[j])){
					validNum++;
				}
				if(validNum==searchLength){
					validNum=0;
					System.out.println(result);
					break;
				}
			}

		}
	}


	//输出: [0,6]
	public static void main(String args[]){
		//slidingWindow1("cbaauuhyhyhuhuabacbaac","aabc");
		slidingWindow2("cbaebabacd","abc");

	}
}
