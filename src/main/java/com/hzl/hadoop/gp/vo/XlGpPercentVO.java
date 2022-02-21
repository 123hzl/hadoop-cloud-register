package com.hzl.hadoop.gp.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * description
 *
 * @author hzl 2022/01/21 3:41 PM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XlGpPercentVO {

	//股票编号
	private String symbol;

	//股票名称
	private String name;

	//交易时间
	private LocalTime ticktime;

	//成交价格
	private BigDecimal price;

	//成交量
	private Long volume;

	//先手价格
	private String prev_price;

	//kind 	卖方价格高，还是买房价格高
	private String kind;

}
