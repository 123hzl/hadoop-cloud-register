package com.hzl.hadoop.util;

import com.hzl.hadoop.constant.DataConstant;
import com.hzl.hadoop.exception.CommonException;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import static com.hzl.hadoop.constant.DataConstant.SHANGHAI;


/**
 * description
 * 日期格式化工具
 *
 * @author hzl 2020/01/03 4:59 PM
 */
public class LocalDateFormate {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DataConstant.DATE);

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DataConstant.DATETIME);


	/**
	 * <p>
	 * 将字符串的LocalDate类型日期，转换成LocalDate类型
	 * </p>
	 *
	 * @author hzl 2020/01/03 5:17 PM
	 */
	public static LocalDate localDateFormate(String localDate) {
		if (StringUtils.hasLength(localDate)) {
			LocalDate localDateConvert = LocalDate.parse(localDate, FORMATTER);
			return localDateConvert;
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 * 将字符串的LocalDateTime类型日期，转换成LocalDateTime类型
	 * localDateTime格式：2020-11-04 23:00:00
	 * </p>
	 *
	 * @author hzl 2020/01/03 5:17 PM
	 */
	public static LocalDateTime stringTolocalDateTime(String localDateTime) {
		if (StringUtils.hasLength(localDateTime)) {
			LocalDateTime localDateTimeConvert = LocalDateTime.parse(localDateTime, TIME_FORMATTER);
			return localDateTimeConvert;
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 *
	 * @param date 时间
	 * @param type 样式
	 *             </p>
	 * @Description localDateTime转换成string
	 * @author hzl 2020/01/16 10:22 AM
	 */
	public static String localDateTimeToString(LocalDateTime date, String type) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(type);
		return date.format(formatter);
	}

	/**
	 * CST可视为美国、澳大利亚、古巴或中国的标准时间。百度百科
	 *
	 * @param stringDate 2019-09-01或者2019-09-01 00:00:00
	 * @return
	 * @author hzl 2020-01-20 6:51 PM
	 */
	public static Date localStringToDate(String stringDate) {
		if (StringUtils.hasLength(stringDate)) {
			try {
				SimpleDateFormat simpleDateFormat = null;
				if (stringDate.length() > 10) {
					simpleDateFormat = new SimpleDateFormat(DataConstant.DATETIME);
				} else {
					simpleDateFormat = new SimpleDateFormat(DataConstant.DATE);
				}
				//默认东八区
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SHANGHAI));
				return simpleDateFormat.parse(stringDate);
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommonException("string转date异常");
			}

		} else {
			return null;
		}

	}

	/**
	 *
	 *将long转换为LocalDateTime
	 * @param timestamp
	 * @author hzl 2020-03-19 5:46 PM
	 * @return
	 */
	public static LocalDateTime longToLocalDateTime(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		//ZoneId zone = ZoneId.systemDefault();
		ZoneId zone = ZoneId.of(SHANGHAI);
		return LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 *
	 *将LocalDateTime转换为long
	 * @param localDateTime
	 * @author hzl 2020-03-19 5:46 PM
	 * @return
	 */
	public static long localDateTimeTolong(LocalDateTime localDateTime) {
		//ZoneId zone = ZoneId.systemDefault();
		ZoneId zone = ZoneId.of(SHANGHAI);
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
	}

}
