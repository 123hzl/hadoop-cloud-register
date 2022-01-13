package com.hzl.hadoop.gp.job;

import com.hzl.hadoop.gp.constant.GpUrlConstant;
import com.hzl.hadoop.gp.service.GpNoticeService;
import com.hzl.hadoop.gp.service.GpService;
import com.hzl.hadoop.gp.service.XinLangNews;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;


/**
 * description
 * 参考：https://blog.csdn.net/czx2018/article/details/83501945
 * https://www.cnblogs.com/loong-hon/p/10143322.html
 *
 * @author hzl 2020/11/03 2:14 PM
 */
@Slf4j
@Component
public class GpBasicInfoJob {
	@Autowired
	private GpService gpService;
	@Autowired
	private GpNoticeService gpNoticeService;

	@Autowired
	private XinLangNews xinLangNews;

	/**
	 * 设置定时器的线程池
	 *
	 * @return
	 * @author hzl 2020-11-03 2:28 PM
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(5);
		return taskScheduler;
	}


	/**
	 * fixedDelay 上传方法执行完成后开始计算
	 * 30秒执行一次@Scheduled(fixedDelay = 30 * 1000)
	 * 0 0/1 9,11 ? * MON-FR,周一到周五，9点到11
	 *
	 * @author hzl 2020-11-03 2:27 PM
	 * @}eturn
	 */
//	@Scheduled(cron = "0 30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59 9 ? * MON-FRI")
//	public void getBasicInfoYl() {
//		log.info("定时器获取中兴时时基础数据d----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_MD);
//		gpService.insert(GpUrlConstant.GP_CODE_GM);
//		gpService.insert(GpUrlConstant.GP_CODE_GL);
//
//		log.info("定时器获取伊利时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_YL);
//
//		log.info("定时器获取海尔时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_HE);
//
//
//		log.info("定时器获取海尔时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_HE);
//
//		log.info("定时器获取今世缘时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_JSY);
//
//	}
//
//	@Scheduled(cron = "0 0/1 10 ? * MON-FRI")
//	public void getBasicInfoYl1() {
//		log.info("定时器获取中兴时时基础数据d----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_MD);
//		gpService.insert(GpUrlConstant.GP_CODE_GM);
//		gpService.insert(GpUrlConstant.GP_CODE_GL);
//
//		log.info("定时器获取伊利时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_YL);
//
//		log.info("定时器获取海尔时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_HE);
//
//
//		log.info("定时器获取今世缘时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_JSY);
//
//	}
//
//	@Scheduled(cron = "0 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31 11 ? * MON-FRI")
//	public void getBasicInfoYl2() {
//		log.info("定时器获取中兴时时基础数据d----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_MD);
//		gpService.insert(GpUrlConstant.GP_CODE_GM);
//		gpService.insert(GpUrlConstant.GP_CODE_GL);
//
//		log.info("定时器获取伊利时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_YL);
//
//		log.info("定时器获取海尔时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_HE);
//
//
//		log.info("定时器获取今世缘时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_JSY);
//
//	}
//
//
//	@Scheduled(cron = "0 0/1 13-14 ? * MON-FRI")
//	public void getBasicInfoYlT() {
//		log.info("定时器获取中兴时时基础数据d----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_MD);
//		gpService.insert(GpUrlConstant.GP_CODE_GM);
//		gpService.insert(GpUrlConstant.GP_CODE_GL);
//
//		log.info("定时器获取伊利时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_YL);
//
//		log.info("定时器获取海尔时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_HE);
//
//
//		log.info("定时器获取今世缘时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_JSY);
//
//	}
//
//
//	@Scheduled(cron = "0 1 15 ? * MON-FRI")
//	public void getBasicInfoYlTL() {
//		log.info("定时器获取中兴时时基础数据d----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_MD);
//		gpService.insert(GpUrlConstant.GP_CODE_GM);
//		gpService.insert(GpUrlConstant.GP_CODE_GL);
//
//		log.info("定时器获取伊利时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_YL);
//
//		log.info("定时器获取海尔时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_HE);
//
//
//		log.info("定时器获取今世缘时时基础数据----------------------------------------------------" + Thread.currentThread());
//		gpService.insert(GpUrlConstant.GP_CODE_JSY);
//
//	}

//	/**
//	 * 半小时执行一次，用于获取个股新闻数据，暂时只爬去新浪网
//	 *
//	 * @author hzl 2021-12-15 12:40 PM
//	 * @return
//	 * */
//	@Scheduled(cron = "0 0/50 * * * ?")
//	public void getTodayNews(){
//		try {
//			xinLangNews.getTodayNews(GpCodeEnum.sz000651);
//			xinLangNews.getTodayNews(GpCodeEnum.sh600690);
//			xinLangNews.getTodayNews(GpCodeEnum.sz000333);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * fixedDelay 上传方法执行完成后开始计算
	 * 30秒执行一次
	 * 竞价信息提醒
	 * 该功能不依赖数据库的
	 *
	 * @author hzl 2020-11-03 2:27 PM
	 * @}eturn
	 */
//	@Scheduled(cron = "0 15-25 9 * * ?")
//	public void getBaddingNoticeZX() {
//		log.info("中兴竞价提醒开始----------------------------------------------------" + Thread.currentThread());
//		gpNoticeService.bidding(GpUrlConstant.GP_CODE_ZX);
//		log.info("伊利竞价提醒开始----------------------------------------------------" + Thread.currentThread());
//		gpNoticeService.bidding(GpUrlConstant.GP_CODE_YL);
//		log.info("海尔竞价提醒开始----------------------------------------------------" + Thread.currentThread());
//		gpNoticeService.bidding(GpUrlConstant.GP_CODE_HE);
//	}


	/**
	 * 最高价，最低价波动情况,邮件提醒
	 * 5分钟执行一次
	 * 竞价信息提醒
	 *
	 * @author hzl 2020-11-03 2:27 PM
	 * @}eturn
	 */
//	@Scheduled(fixedDelay = 5 * 60 * 1000)
//	public void getvolatilityPricegNoticeZX() {
//		log.info("伊利高低价波动提醒----------------------------------------------------" + Thread.currentThread());
//		gpNoticeService.volatilityPriceSendMail(GpUrlConstant.GP_CODE_YL);
//
//		log.info("中兴高低价波动提醒----------------------------------------------------" + Thread.currentThread());
//		gpNoticeService.volatilityPriceSendMail(GpUrlConstant.GP_CODE_HE);
//	}
}
