package com.hzl.hadoop.gp.constant;

/**
 * description
 *
 * @author hzl 2020/10/31 1:43 PM
 */
public interface GpUrlConstant {
	String YL_URL="http://hq.sinajs.cn/list=sh600887";

	String MD_URL="http://hq.sinajs.cn/list=sz000333";
	//获取成交额，时时价格
	String GP_BASE_URL="http://hq.sinajs.cn/list=";
	//伊利
	String GP_CODE_YL="sh600887";
	//中兴
	String GP_CODE_ZX="sz000063";
	//海尔
	String GP_CODE_HE="sh600690";
	//光明
	String GP_CODE_GM="sh600597";
	//格力
	String GP_CODE_GL="sz000651";
	//美的
	String GP_CODE_MD="sz000333";

	//竞价开始时间
	String BADDING_START="2020-11-04 09:15:00";

	//竞价结束时间
	String BADDING_END="2020-11-04 09:25:00";

	//新浪个股新闻接口
	String SINGLE_STOCK_NEW="https://vip.stock.finance.sina.com.cn/corp/go.php/vCB_AllNewsStock/symbol/";

	//新闻来源-新浪
	String NEWS_SOURCE="xl";
}
