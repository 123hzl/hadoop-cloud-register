package com.hzl.hadoop.gp.job;

import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.service.GpInfoService;
import com.hzl.hadoop.gp.service.GpXlPercentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * description
 * 参考：https://blog.csdn.net/czx2018/article/details/83501945
 * https://www.cnblogs.com/loong-hon/p/10143322.html
 * 获取扇形图数据，实时交易量数据
 *
 * @author hzl 2020/11/03 2:14 PM
 */
@Slf4j
@Component
public class GpBasicInfoPercentJob {

	@Autowired
	private GpInfoService gpInfoService;

	@Autowired
	private GpXlPercentService gpXlPercentService;


	public Boolean creep() {

		List<GpInfoEntity> list = gpInfoService.list();
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(gpInfoEntity -> {
				//初始化扇形数据，获取交易明细
				gpXlPercentService.init(gpInfoEntity.getGpCode());

			});

		}

		return true;
	}

	/**
	 * 、fixedDelay控制方法执行的间隔时间，是以上一次方法执行完开始算起，如上一次方法执行阻塞住了，那么直到上一次执行完，并间隔给定的时间后，执行下一次
	 * 30秒执行一次@Scheduled(fixedDelay = 30 * 1000)
	 * 0 0/1 9,11 ? * MON-FR,周一到周五，9点到11
	 *
	 * @author hzl 2020-11-03 2:27 PM
	 * @}eturn
	 */
	@Scheduled(cron = "0 30,32,34,36,38,40,42,44,46,48,50,52,54,56,58 9 ? * MON-FRI")
	public Boolean getPercent() {
		return creep();
	}

	@Scheduled(cron = "0 0/1 10 ? * MON-FRI")
	public Boolean getPercent1() {
		return creep();
	}

	@Scheduled(cron = "0 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,31 11 ? * MON-FRI")
	public Boolean getPercent2() {
		return creep();

	}


	@Scheduled(cron = "0 0/2 13-14 ? * MON-FRI")
	public Boolean getPercent3() {
		return creep();

	}

	@Scheduled(cron = "0 1 15 ? * MON-FRI")
	public Boolean getPercent4() {
		return creep();

	}

	@Scheduled(cron = "0 1 19 ? * MON-FRI")
	public Boolean removePercent() {
		List<GpInfoEntity> list = gpInfoService.list();
		if (CollectionUtils.isNotEmpty(list)) {
			list.forEach(a -> gpXlPercentService.remove(a.getGpCode()));
		}
		return true;
	}


}
