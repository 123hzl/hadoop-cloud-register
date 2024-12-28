package com.hzl.hadoop.gp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Buying {
	private Long buyingId;
	//买入股票数量
	private BigDecimal buyingNumber;
	//买入价格
	private BigDecimal buyingPrice;

	//已经冲抵的卖出单数量
	private BigDecimal alreadySoldNumber;

	//剩余可冲抵的卖出单数量
	private BigDecimal remainSoldNumber;

	public Buying(BigDecimal buyingNumber, BigDecimal buyingPrice) {
		this.buyingNumber = buyingNumber;
		this.buyingPrice = buyingPrice;
	}
}
