package com.hzl.hadoop.gp.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * 时时成交价和具体价格的成交量，只存储当天的数据。前天的会自动清除
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-01-21 17:02:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpXlPercentVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Long id;
	/**
	 * 股票编号
	 */
	private String symbol;
	/**
	 * 股票名称
	 */
	private String name;
	/**
	 * 交易时间
	 */
	private LocalTime ticktime;
	/**
	 * 成交价格
	 */
	private BigDecimal price;
	/**
	 * 成交量
	 */
	private Long volume;
	/**
	 * 先手价格
	 */
	private BigDecimal prevPrice;
	/**
	 * 卖方价格高，还是买房价格高
	 */
	private String kind;

}
