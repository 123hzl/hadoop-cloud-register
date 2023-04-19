package com.hzl.hadoop.gp.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * description
 * 最大最小成交量折线图
 * @author hzl 2020/11/05 10:36 PM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EndPriceVO {
	//折线名称
	private String series;
	//时间
	private String x;

	//价格
	private BigDecimal y;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof EndPriceVO)) {
			return false;
		}
		EndPriceVO that = (EndPriceVO) o;
		return Objects.equals(getSeries(), that.getSeries()) &&
				Objects.equals(getX(), that.getX()) &&
				Objects.equals(getY(), that.getY());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getSeries(), getX(), getY());
	}
}
