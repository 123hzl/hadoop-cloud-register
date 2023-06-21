package com.hzl.hadoop.util;

import cn.hutool.Hutool;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description
 * 生成随机手机号
 *
 * @author hzl 2023/06/16 11:52 AM
 */
public class PhoneUtils {

	private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");


	public static void main(String[] args) {
		String phoneNumber = getTelephone();
		System.out.println("Random phone number: " + phoneNumber);
	}

	public static String getTelephone() {
		int index=getNum(0,telFirst.length-1);
		String first=telFirst[index];
		String second=String.valueOf(getNum(1,888)+10000).substring(1);
		String thrid=String.valueOf(getNum(1,9100)+10000).substring(1);
		return first+second+thrid;
	}

	public static int getNum(int start,int end) {
		return (int)(Math.random()*(end-start+1)+start);
	}
}
