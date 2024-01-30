package com.hzl.hadoop.gp.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class Selling {
	//买入股票数量
	BigDecimal sellingNumber;
	//买入价格
	BigDecimal sellingPrice;

	public Selling(BigDecimal sellingNumber, BigDecimal sellingPrice) {
		this.sellingNumber = sellingNumber;
		this.sellingPrice = sellingPrice;
	}
}
