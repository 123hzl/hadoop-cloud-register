package com.hzl.hadoop.gp.job;

import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


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


	@Autowired
	private GpInfoService gpInfoService;

	@Autowired
	private GpXlPercentService gpXlPercentService;
	@Autowired
	private GpIndexService gpIndexService;

	/**
	 * 设置定时器的线程池
	 *
	 * @return
	 * @author hzl 2020-11-03 2:28 PM
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(6);
		return taskScheduler;
	}

	public Boolean creep() {

		List<GpInfoEntity> list = gpInfoService.list();
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(gpInfoEntity -> {

				//是否爬取
				if (gpInfoEntity.getIsCreep()) {
					log.info("定时器爬取股票数据----------------------------------------------------" + Thread.currentThread());
					gpService.insert(gpInfoEntity.getGpCode());
				}
				//是否买入卖出通知
				if (gpInfoEntity.getIsNotify()) {
					log.info("定时器买入卖出提醒----------------------------------------------------" + Thread.currentThread());
				}

			});

		}

		return true;
	}

	/**
	 * fixedDelay 上传方法执行完成后开始计算
	 * 30秒执行一次@Scheduled(fixedDelay = 30 * 1000)
	 * 0 0/1 9,11 ? * MON-FR,周一到周五，9点到11
	 *
	 * @author hzl 2020-11-03 2:27 PM
	 * @}eturn
	 */
	@Scheduled(cron = "0/20 30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59 9 ? * MON-FRI")
	public Boolean getBasicInfoYl() {
		return creep();
	}


	@Scheduled(cron = "0/20 0/1 10 ? * MON-FRI")
	public Boolean getBasicInfoYl1() {
		return creep();
	}

	@Scheduled(cron = "0/20 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31 11 ? * MON-FRI")
	public Boolean getBasicInfoYl2() {
		return creep();

	}


	@Scheduled(cron = "0/20 0/1 13-14 ? * MON-FRI")
	public Boolean getBasicInfoYlT() {
		return creep();

	}

	@Scheduled(cron = "0 1 15 ? * MON-FRI")
	public Boolean getBasicInfoYlTL() {
		return creep();

	}



	/**
	 * 半小时执行一次，用于获取个股新闻数据，暂时只爬去新浪网
	 *
	 * @return
	 * @author hzl 2021-12-15 12:40 PM
	 */
	@Scheduled(cron = "0 0/50 * * * ?")
	public void getTodayNews() {
		List<GpInfoEntity> list = gpInfoService.list();
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(gpInfoEntity -> {

				//是否发送通知
				if (gpInfoEntity.getIsCreep()) {
					try {
						xinLangNews.getTodayNews(gpInfoEntity);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			});

		}

	}

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
