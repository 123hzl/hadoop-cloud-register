package com.hzl.hadoop.gp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzl.hadoop.gp.constant.GpNumberConstant;
import com.hzl.hadoop.gp.entity.GpIndexEntity;
import com.hzl.hadoop.gp.mapper.GpIndexMapper;
import com.hzl.hadoop.gp.repository.GpRepository;
import com.hzl.hadoop.gp.service.GpIndexService;
import com.hzl.hadoop.gp.utils.FormulaUtils;
import com.hzl.hadoop.gp.vo.GpIndexResultVO;
import com.hzl.hadoop.gp.vo.GpIndexVO;
import com.hzl.hadoop.gp.vo.VolumeVO;
import com.hzl.hadoop.util.LocalDateFormate;
import com.hzl.hadoop.util.StringUtils;
import com.hzl.hadoop.util.email.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("gpIndexService")
public class GpIndexServiceImpl extends ServiceImpl<GpIndexMapper, GpIndexEntity> implements GpIndexService {

	@Autowired
	GpIndexMapper mapper;
	@Autowired
	GpRepository gpRepository;




	@Override
	public void initDate(String gpCode) {
		List<VolumeVO> volumeVOS = gpRepository.queryVolume(VolumeVO.builder().gpCode(gpCode).isNormalDate(true).build());


		for (int i = 1; i < volumeVOS.size(); i++) {

			//当天数据
			VolumeVO a = volumeVOS.get(i);
			//昨天数据
			VolumeVO b = volumeVOS.get(i - 1);
			//当日均价
			BigDecimal avg = FormulaUtils.avg(a.getTurnover(), a.getNumber());
			//昨日均价
			BigDecimal avgYesterDay = FormulaUtils.avg(b.getTurnover(), b.getNumber());

			//(收盘价格-当日均价)/当日均价
			BigDecimal dailyPercent = (a.getCurrentPrice().subtract(avg).multiply(GpNumberConstant.oneHundred)).divide(avg, 5, RoundingMode.FLOOR);
			//(今日均价-昨日均价)/昨日均价
			BigDecimal daily1Percent = (avg.subtract(avgYesterDay).multiply(GpNumberConstant.oneHundred)).divide(avgYesterDay, 5, RoundingMode.FLOOR);
			//(今日成交量-昨日成交量)/昨日成交量
			BigDecimal turnoverPercent = (a.getNumber().subtract(b.getNumber()).multiply(GpNumberConstant.oneHundred)).divide(b.getNumber(), 5, RoundingMode.FLOOR);
			//(今日收盘价-昨日收盘价)/昨日收盘价
			BigDecimal overPercent = (a.getCurrentPrice().subtract(b.getCurrentPrice()).multiply(GpNumberConstant.oneHundred)).divide(b.getCurrentPrice(), 5, RoundingMode.FLOOR);

			/**
			 * (今日收盘价-昨日均价)/昨日均价
			 */
			BigDecimal overPercent1 = (a.getCurrentPrice().subtract(avgYesterDay).multiply(GpNumberConstant.oneHundred)).divide(avgYesterDay, 5, RoundingMode.FLOOR);

			//当日买盘，卖盘占比 todo
			GpIndexEntity gpIndexEntity = GpIndexEntity.builder()
					.priceDate(LocalDateFormate.stringTolocalDate(a.getDate()))
					.gpCode(gpCode)
					.gpName(a.getGpName())
					.endPrice(a.getCurrentPrice())
					.dailyPercent(dailyPercent)
					.daily1Percent(daily1Percent)
					.turnoverPercent(turnoverPercent)
					.overPercent(overPercent)
					.overPercent1(overPercent1)
					.build();
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.eq("price_date", gpIndexEntity.getPriceDate());
			queryWrapper.eq("gp_code", gpIndexEntity.getGpCode());
			GpIndexEntity isExist = mapper.selectOne(queryWrapper);
			if (isExist == null) {
				mapper.insert(gpIndexEntity);
			} else {
				gpIndexEntity.setId(isExist.getId());
				gpIndexEntity.setVersionNum(isExist.getVersionNum());
				log.info("擦拭{}", gpIndexEntity.toString());
				log.info("擦拭1{}", mapper.toString());
				mapper.updateById(gpIndexEntity);
			}

		}

	}

	@Override
	public void initIndexSpeed(String code) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("gp_code", code);
		List<GpIndexEntity> gpIndexEntities = mapper.selectList(queryWrapper);

		for (int i = 1; i < gpIndexEntities.size(); i++) {
			GpIndexEntity a = gpIndexEntities.get(i);
			GpIndexEntity b = gpIndexEntities.get(i - 1);
			//（今天-昨天）/昨天
			BigDecimal dailyPercentSpeed = GpNumberConstant.oneHundred;
			//今天-昨天）/昨天
			BigDecimal daily1PercentSpeed = GpNumberConstant.oneHundred;
			BigDecimal turnoverPercentSpeed = GpNumberConstant.oneHundred;
			BigDecimal overPercentSpeed = GpNumberConstant.oneHundred;
			BigDecimal overPercentSpeed1 = GpNumberConstant.oneHundred;

			if (b.getDailyPercent().compareTo(BigDecimal.ZERO) != 0) {
				dailyPercentSpeed = ((a.getDailyPercent().subtract(b.getDailyPercent()))
						.multiply(GpNumberConstant.oneHundred))
						.divide(b.getDailyPercent().abs(), 5, RoundingMode.FLOOR);
			}

			if (b.getDaily1Percent().compareTo(BigDecimal.ZERO) != 0) {
				daily1PercentSpeed = ((a.getDaily1Percent().subtract(b.getDaily1Percent()))
						.multiply(GpNumberConstant.oneHundred))
						.divide(b.getDaily1Percent().abs(), 5, RoundingMode.FLOOR);
			}
			if (b.getTurnoverPercent().compareTo(BigDecimal.ZERO) != 0) {
				turnoverPercentSpeed = ((a.getTurnoverPercent().subtract(b.getTurnoverPercent()))
						.multiply(GpNumberConstant.oneHundred))
						.divide(b.getTurnoverPercent().abs(), 5, RoundingMode.FLOOR);
			}
			if (b.getOverPercent().compareTo(BigDecimal.ZERO) != 0) {
				overPercentSpeed = ((a.getOverPercent().subtract(b.getOverPercent()))
						.multiply(GpNumberConstant.oneHundred))
						.divide(b.getOverPercent().abs(), 5, RoundingMode.FLOOR);
			}
			if (b.getOverPercent1().compareTo(BigDecimal.ZERO) != 0) {
				overPercentSpeed1 = ((a.getOverPercent1().subtract(b.getOverPercent1()))
						.multiply(GpNumberConstant.oneHundred))
						.divide(b.getOverPercent1().abs(), 5, RoundingMode.FLOOR);
			}

			//当日买盘，卖盘占比 todo
			a.setDailyPercentSpeed(dailyPercentSpeed);
			a.setDaily1PercentSpeed(daily1PercentSpeed);
			a.setTurnoverPercentSpeed(turnoverPercentSpeed);
			a.setOverPercentSpeed(overPercentSpeed);
			a.setOverPercentSpeed1(overPercentSpeed1);
			mapper.updateById(a);

		}
	}

	@Override
	public void initIndexAnalyse(String code) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("gp_code", code);
		List<GpIndexEntity> gpIndexEntities = mapper.selectList(queryWrapper);

		//第二天涨的
		List<GpIndexEntity> up = new ArrayList<>();
		//第二天跌的
		List<GpIndexEntity> down = new ArrayList<>();
		//预测第二天涨跌情况
		for (int i = 1; i < gpIndexEntities.size(); i++) {
			GpIndexEntity a = gpIndexEntities.get(i - 1);
			GpIndexEntity b = gpIndexEntities.get(i);
			if (b.getOverPercent().compareTo(BigDecimal.ZERO) > 0) {
				up.add(a);
			}
			if (b.getOverPercent().compareTo(BigDecimal.ZERO) < 0) {
				down.add(a);
			}
		}

		log.info("up{}", up.toString());
		log.info("down{}", down.toString());

	}

	@Override
	public GpIndexResultVO forecast(String gpCode, LocalDate time,String factors) {

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("gp_code", gpCode);
		queryWrapper.eq("price_date", time);
		GpIndexEntity gpIndexEntities = mapper.selectOne(queryWrapper);

		GpIndexVO gpIndexVO = new GpIndexVO(gpIndexEntities);
		Stack<Integer> stack = new Stack<Integer>();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(factors)){
			String[] factorss=factors.split(",");
			for(String s:factorss){
				stack.add(Integer.valueOf(s));
			}
		}else {
			stack.add(-2);
			stack.add(-3);
			stack.add(-4);
			stack.add(-5);
		}


		GpIndexVO gpIndexVO1=gpIndexVO.ggg(gpIndexEntities,stack);

		return forecast1(gpCode,gpIndexVO1,time);
	}

	public void forecastAll(String gpCode,Stack<Integer> stack) {

		HashMap<String,Integer> hashMap=new HashMap<>();
		hashMap.put(stack.toString(),0);

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("gp_code", gpCode);
		List<GpIndexEntity> gpIndexEntities = mapper.selectList(queryWrapper);

		List<GpIndexResultVO> list=new ArrayList<>();
		gpIndexEntities.forEach(a->{
			GpIndexVO gpIndexVO = new GpIndexVO().ggg(a,stack);
			GpIndexResultVO gpIndexResultVO=forecast1(gpCode,gpIndexVO,a.getPriceDate());
			if(gpIndexResultVO!=null&&gpIndexResultVO.getDown()!=null&&gpIndexResultVO.getIsUp()!=null&&gpIndexResultVO.isSuccess()){
				list.add(gpIndexResultVO);
				Integer num=hashMap.get(stack.toString())+1;
				hashMap.put(stack.toString(),num);
			}


		});
		hashMap1.putAll(hashMap);

	}

	public GpIndexResultVO forecast1(String gpCode,GpIndexVO gpIndexVO,LocalDate time){

		List<GpIndexEntity> gpIndexEntities1 = mapper.forecast(gpIndexVO);


		List<LocalDate> dates = gpIndexEntities1.stream().filter(b -> !b.getPriceDate().isEqual(time)).map(a -> {
			//周五加3天
			if (a.getPriceDate().getDayOfWeek() == DayOfWeek.FRIDAY) {
				return a.getPriceDate().plusDays(3);
			}
			return a.getPriceDate().plusDays(1);
		}).collect(Collectors.toList());

		//查询第二天的数据
		if (CollectionUtils.isNotEmpty(dates)) {
			QueryWrapper queryWrapper1 = new QueryWrapper();
			queryWrapper1.eq("gp_code", gpCode);
			queryWrapper1.in("price_date", dates);
			List<GpIndexEntity> gpIndexList = mapper.selectList(queryWrapper1);

			//第二天涨的
			Integer up = 0;
			//第二天跌的
			Integer down = 0;

			for (GpIndexEntity a : gpIndexList) {
				if (a.getOverPercent().compareTo(BigDecimal.ZERO) > 0) {
					up = up + 1;
				} else {
					down = down + 1;
				}
			}

			//周五加3天
			LocalDate last;
			if (time.getDayOfWeek() == DayOfWeek.FRIDAY) {
				last = time.plusDays(3);
			} else {
				last = time.plusDays(1);
			}
			QueryWrapper queryWrapper2 = new QueryWrapper();
			queryWrapper2.eq("gp_code", gpCode);
			queryWrapper2.eq("price_date", last);
			GpIndexEntity result = mapper.selectOne(queryWrapper2);
			Boolean isUp = null;

			if (result != null && result.getOverPercent().compareTo(BigDecimal.ZERO) > 0) {
				isUp = true;
			} else if (result != null && result.getOverPercent().compareTo(BigDecimal.ZERO) <= 0) {
				isUp = false;
			}
			return GpIndexResultVO.builder().up(up).down(down).isUp(isUp).build();

		} else {
			return GpIndexResultVO.builder().up(0).down(0).isUp(null).build();
		}

	}

	public static HashMap<String,Integer> hashMap1=new HashMap();


	public static Stack<Integer> stack = new Stack<Integer>();

	public static void main(String[] args) {
		int shu[] = {-2,-3,-4};

		ff(shu,3,0,0); // 从这个数组4个数中选择三个
	}

	public static void ff(int[] shu, int targ, int has, int cur) {
		if(has == targ) {
			System.out.println(stack);
			return;
		}

		for(int i=cur;i<shu.length;i++) {
			if(!stack.contains(shu[i])) {
				stack.add(shu[i]);
				ff(shu, targ, has+1, i);
				stack.pop();
			}
		}

	}

	/**
	 *
	 * @param shu  元素
	 * @param targ  要选多少个元素
	 * @param has   当前有多少个元素
	 * @param cur   当前选到的下标
	 *
	 * 1    2   3     //开始下标到2
	 * 1    2   4     //然后从3开始
	 */
	@Override
	public void f(int[] shu, int targ, int has, int cur) {
		if(has == targ) {
			forecastAll("sz000651",stack);
			return;
		}

		for(int i=cur;i<shu.length;i++) {
			if(!stack.contains(shu[i])) {
				stack.add(shu[i]);
				f(shu, targ, has+1, i);
				stack.pop();
			}
		}

	}


}