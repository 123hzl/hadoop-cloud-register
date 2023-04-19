package com.hzl.hadoop.gp.vo;

import lombok.*;

/**
 * description
 *
 * @author hzl 2023/04/12 1:11 PM
 */
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GpIndexResultVO {

	Integer up;

	Integer down;

	//预测的第二天是否涨了
	Boolean isUp;

	public Boolean isSuccess(){
		if(isUp!=null&&up>down&&isUp){
			return true;
		}
		if(isUp!=null&&up<down&&!isUp){
			return true;
		}
		return false;
	}
}
