package com.hzl.hadoop.constant;

/**
 * description
 * 天眼查自动登陆涉及的常量
 * @author hzl 2022/12/14 2:20 PM
 */
public interface TycConstant {

	/**
	 天眼查首页地址
	 */
	String TYC_URL="https://www.tianyancha.com/";

    /**
    公司列表查询地址
    */
	String COMPANY_LIST_URL="https://www.tianyancha.com/advance/search/result?eventPrefix=pc_homeicon";


	/**
	 redis key前缀
	 */
	String TYC_KEY_PREFIX="TYC";
}
