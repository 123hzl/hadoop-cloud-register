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
public class Selling {
	private Long sellingId;
	//买入股票数量
	private BigDecimal sellingNumber;
	//买入价格
	private BigDecimal sellingPrice;

	public Selling(BigDecimal sellingNumber, BigDecimal sellingPrice) {
		this.sellingNumber = sellingNumber;
		this.sellingPrice = sellingPrice;
	}
}
