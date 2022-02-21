package com.hzl.hadoop.gp.vo;

import com.hzl.hadoop.gp.entity.CpInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * description
 *
 * @author hzl 2022/01/26 3:44 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpVO {

	//期数编号
	private String lotteryDrawNum;

	//中奖号码lottery_draw_result
	private String lotteryDrawResult;

	//开奖日期lottery_draw_time
	private LocalDate lotteryDrawTime;

	public CpInfoEntity convert() {
		String[] num = lotteryDrawResult.split(" ");

		return CpInfoEntity.builder()
				.lotteryDrawNum(lotteryDrawNum)
				.lotteryDrawTime(lotteryDrawTime)
				.first(Integer.valueOf(num[0]))
				.second(Integer.valueOf(num[1]))

				.three(Integer.valueOf(num[2]))

				.four(Integer.valueOf(num[3]))

				.five(Integer.valueOf(num[4]))

				.six(Integer.valueOf(num[5]))

				.seven(Integer.valueOf(num[6]))

				.build();
	}
}
