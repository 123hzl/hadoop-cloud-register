package com.hzl.hadoop.util;

import java.util.Random;

/**
 * description
 * 编码生产规则工具类
 *
 * @author hzl 2022/06/06 9:31 AM
 */
public class GenerateCodeUtils {


	/**
	 * 数字字母验证码，基于ASCII
	 *
	 * @param digit 位数
	 * @return
	 * @author hzl 2022-06-06 9:32 AM
	 */
	public static String generateNumCode(int digit) {
		StringBuilder val = new StringBuilder();
		Random random = new Random();

		//参数length，表示生成几位随机数
		for (int i = 0; i < digit; i++) {
			//随机生产0(字母)，1(数字），用于表示生成数字还是字母
			int charOrNum = random.nextInt(2);
			//输出字母还是数字
			if (charOrNum == 0) {
				//输出是大写字母还是小写字母，65是大写A，97是小写a
				int temp = random.nextInt(2)== 0 ? 65 : 97;
				val.append ((char) (random.nextInt(26) + temp));
			} else if (charOrNum == 1) {
				val.append(String.valueOf(random.nextInt(10)));
			}
		}
		return val.toString();
	}




}
