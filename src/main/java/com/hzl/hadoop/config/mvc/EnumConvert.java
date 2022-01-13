package com.hzl.hadoop.config.mvc;

import com.hzl.hadoop.gp.constant.GpCodeEnum;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * description
 *
 * @author hzl 2021/12/16 11:02 AM
 */
public class EnumConvert implements Converter<String, GpCodeEnum> {

	@Override
	public GpCodeEnum convert(String source) {
		//将前端的string转换成enum
		return GpCodeEnum.valueOf(source);
	}
}
