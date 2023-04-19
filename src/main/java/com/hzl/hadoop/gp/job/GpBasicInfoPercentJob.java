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
 * 废弃-除非走代理，不然会被限制访问(或者低频访问)
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
				if (gpInfoEntity.getIsCreep()) {
					gpXlPercentService.init(gpInfoEntity.getGpCode());
				}

			});

		}

		return true;
	}


	/**
	 * <p>
	 * 每天晚上同步一次
	 * </p>
	 *
	 * @author hzl 2023/03/10 12:51 PM
	 */
	@Scheduled(cron = "0 1 15 ? * MON-FRI")
	public Boolean getPercent4() {
		//creep();
		return true;

	}

}
