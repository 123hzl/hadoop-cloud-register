package com.hzl.hadoop.gp.vo;

import com.hzl.hadoop.gp.entity.GpIndexEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * description
 * true为大于0，false为小于等于0
 *
 * @author hzl 2023/04/12 12:45 PM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpIndexVO {

	/**
	 * (收盘价格-当日均价)/当日均价
	 */
	private Boolean dailyPercent;
	/**
	 * (今日均价-昨日均价)/昨日均价
	 */
	private Boolean daily1Percent;
	/**
	 * (今日成交量-昨日成交量)/昨日成交量
	 */
	private Boolean turnoverPercent;
	/**
	 * (今日收盘价-昨日收盘价)/昨日收盘价
	 */
	private Boolean overPercent;

	/**
	 * (收盘价格-当日均价)/当日均价增速
	 */
	private Boolean dailyPercentSpeed;
	/**
	 * (今日均价-昨日均价)/昨日均价增速
	 */
	private Boolean daily1PercentSpeed;
	/**
	 * (今日成交量-昨日成交量)/昨日成交量增速
	 */
	private Boolean turnoverPercentSpeed;
	/**
	 * (今日收盘价-昨日收盘价)/昨日收盘价增速
	 */
	private Boolean overPercentSpeed;

	public GpIndexVO(GpIndexEntity gpIndexEntity) {
		if (gpIndexEntity.getDailyPercent() == null) {
			dailyPercent = null;
		} else if (gpIndexEntity.getDailyPercent().compareTo(BigDecimal.ZERO) > 0) {
			dailyPercent = true;
		} else {
			dailyPercent = false;
		}

		if (gpIndexEntity.getDaily1Percent() == null) {
			daily1Percent = null;
		} else if (gpIndexEntity.getDaily1Percent().compareTo(BigDecimal.ZERO) > 0) {
			daily1Percent = true;
		} else {
			daily1Percent = false;
		}


		if (gpIndexEntity.getTurnoverPercent() == null) {
			turnoverPercent = null;
		} else if (gpIndexEntity.getTurnoverPercent().compareTo(BigDecimal.ZERO) > 0) {
			turnoverPercent = true;
		} else {
			turnoverPercent = false;
		}


		if (gpIndexEntity.getOverPercent() == null) {
			overPercent = null;
		} else if (gpIndexEntity.getOverPercent().compareTo(BigDecimal.ZERO) > 0) {
			overPercent = true;
		} else {
			overPercent = false;
		}

		if (gpIndexEntity.getDailyPercentSpeed() == null) {
			dailyPercentSpeed = null;
		} else if (gpIndexEntity.getDailyPercentSpeed().compareTo(BigDecimal.ZERO) > 0) {
			dailyPercentSpeed = true;
		} else {
			dailyPercentSpeed = false;
		}


		if (gpIndexEntity.getDaily1PercentSpeed() == null) {
			daily1PercentSpeed = null;
		} else if (gpIndexEntity.getDaily1PercentSpeed().compareTo(BigDecimal.ZERO) > 0) {
			daily1PercentSpeed = true;
		} else {
			daily1PercentSpeed = false;
		}

		if (gpIndexEntity.getTurnoverPercentSpeed() == null) {
			turnoverPercentSpeed = null;
		} else if (gpIndexEntity.getTurnoverPercentSpeed().compareTo(BigDecimal.ZERO) > 0) {
			turnoverPercentSpeed = true;
		} else {
			turnoverPercentSpeed = false;
		}

		if (gpIndexEntity.getOverPercentSpeed() == null) {
			overPercentSpeed = null;
		} else if (gpIndexEntity.getOverPercentSpeed().compareTo(BigDecimal.ZERO) > 0) {
			overPercentSpeed = true;
		} else {
			overPercentSpeed = false;
		}


	}

	public GpIndexVO ggg(GpIndexEntity gpIndexEntity, Stack<Integer> stack) {
		List<Integer> shu = new ArrayList<>();
		shu.add(1);
		shu.add(2);
		shu.add(3);
		shu.add(4);
		shu.add(-1);
		shu.add(-2);
		shu.add(-3);
		shu.add(-4);
		for (Integer s : stack) {
			shu.remove(s);
		}

		shu.forEach(a -> {
			if (a == 1) {
				gpIndexEntity.setDailyPercent(null);
			}
			if (a == 2) {
				gpIndexEntity.setDaily1Percent(null);
			}
			if (a == 3) {
				gpIndexEntity.setTurnoverPercent(null);
			}
			if (a == 4) {
				gpIndexEntity.setOverPercent(null);
			}
			if (a == -1) {
				gpIndexEntity.setDailyPercentSpeed(null);
			}
			if (a == -2) {
				gpIndexEntity.setDaily1PercentSpeed(null);
			}
			if (a == -3) {
				gpIndexEntity.setTurnoverPercentSpeed(null);
			}
			if (a == -4) {
				gpIndexEntity.setOverPercentSpeed(null);
			}

		});

		return new GpIndexVO(gpIndexEntity);
	}
}
