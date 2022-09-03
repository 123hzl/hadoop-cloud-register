package com.hzl.hadoop.file.excel.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * description
 *
 * @author hzl 2022/08/09 12:49 PM
 */
@Data
public class PriceDTO {

	private String feeName;

	private BigDecimal feePrice;
}
