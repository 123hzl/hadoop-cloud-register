package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hzl.hadoop.gp.entity.GpInfoEntity;
import com.hzl.hadoop.gp.repository.GpStareRepository;
import com.hzl.hadoop.gp.service.GpInfoService;
import com.hzl.hadoop.gp.service.GpStareService;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.util.email.service.MailService;
import com.hzl.hadoop.workflow.entity.StartNodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * description
 *
 * @author hzl 2022/01/13 7:11 PM
 */
@Service
public class GpStareServiceImpl implements GpStareService {

	@Autowired
	GpStareRepository gpStareRepository;


	@Autowired
	private MailService mailService;

	@Autowired
	private GpInfoService gpInfoService;

	@Override
	public Boolean notifyBuyAndSale(String gpCode) {

		QueryWrapper<GpInfoEntity> queryWrapper = new QueryWrapper();
		queryWrapper.eq("gp_code",gpCode);

		GpInfoEntity gpInfoEntity=gpInfoService.getOne(queryWrapper);

		if(gpInfoEntity==null){
			//如果股票信息表没有数据，直接返回
			return true;
		}

		if(!gpInfoEntity.getIsNotify()){
			//如果设置了不发送直接返回
			return true;
		}
		//查询时时均价
		VolumeVO gpVO = gpStareRepository.queryCurrentPercent(gpCode);

		//优化，新建股票表，是否开启通知字段

		if (gpVO != null) {
			Double big = new Double(0);
			Double currentPercent = gpVO.getPercent();
			//当前时间
			LocalDate localDate=LocalDate.now();

			if (currentPercent.compareTo(big) >= 0) {
				//查询三十天亏损的均价，
				List<VolumeVO> gpVOList1 = gpStareRepository.getHistoryAverage(gpCode, true);

				if (gpVOList1 != null && gpVOList1.size() > 5) {
					//求平均亏损
					Double percentList = gpVOList1.stream().mapToDouble(VolumeVO::getPercent).average().getAsDouble();

					if (currentPercent.compareTo(percentList) >= 0) {
						//当前亏损率大于等于近一个月平均亏损
						try {
							mailService.sendHtmlMail(localDate+"股票:" + gpVO.getGpName() + "当前亏损率大于等于近一个月平均亏损--建议买入"
									, "平均亏损：" + percentList + "当前亏损：" + currentPercent, null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}

					}
					if (currentPercent.compareTo(gpVOList1.get(2).getPercent()) >= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate+"股票:" + gpVO.getGpName() + "当前亏损率大于等于排行第三的亏损--建议买入", "第三的亏损：" + gpVOList1.get(2).getPercent() + "当前亏损：" + currentPercent, null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}

					if (currentPercent.compareTo(gpVOList1.get(0).getPercent()) >= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate+"股票:" + gpVO.getGpName() + "当前亏损率大于等于排行第一的亏损--建议买入", "第一的亏损：" + gpVOList1.get(0).getPercent() + "当前亏损：" + currentPercent, null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}


			} else if (currentPercent.compareTo(big) < 0) {
				//查询三十天盈利的均价
				List<VolumeVO> gpVOList2 = gpStareRepository.getHistoryAverage(gpCode, false);
				if (gpVOList2 != null && gpVOList2.size() > 5) {
					//求平均亏损
					Double percentList = gpVOList2.stream().mapToDouble(VolumeVO::getPercent).average().getAsDouble();

					if (currentPercent.compareTo(percentList) >= 0) {
						//当前亏损率大于等于近一个月平均亏损
						try {
							mailService.sendHtmlMail(localDate+"股票:" + gpVO.getGpName() + "当前盈利小于平均盈利--建议尽早抛售", "平均盈利：" + percentList + "当前盈利：", null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}

					}
					if (currentPercent.compareTo(gpVOList2.get(2).getPercent()) >= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate+"股票:" + gpVO.getGpName() + "当前盈利小于排行第三的盈利--建议尽早抛售", "第三的盈利：" + gpVOList2.get(2).getPercent() + "当前盈利：" + currentPercent, null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}

					if (currentPercent.compareTo(gpVOList2.get(gpVOList2.size()).getPercent()) <= 0) {
						//当前亏损率大于等于排行低三的亏损
						try {
							mailService.sendHtmlMail(localDate+"股票:" + gpVO.getGpName() + "当前盈利率大于历史最大--建议立马抛售", "第一的盈利：" + gpVOList2.get(gpVOList2.size()).getPercent() + "当前盈利：" + currentPercent, null);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		return true;
	}


}
