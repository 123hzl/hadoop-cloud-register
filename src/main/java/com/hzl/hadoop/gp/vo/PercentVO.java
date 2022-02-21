package com.hzl.hadoop.gp.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * description
 *
 * @author hzl 2022/01/20 11:23 AM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PercentVO {

	String type;

	BigDecimal value;
}
