package com.hzl.hadoop.gp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class Buying {
	//买入股票数量
	BigDecimal buyingNumber;
	//买入价格
	BigDecimal buyingPrice;

	public Buying(BigDecimal buyingNumber, BigDecimal buyingPrice) {
		this.buyingNumber = buyingNumber;
		this.buyingPrice = buyingPrice;
	}
}
