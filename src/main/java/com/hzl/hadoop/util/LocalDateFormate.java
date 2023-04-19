package com.hzl.hadoop.util;

import com.hzl.hadoop.constant.DataConstant;
import com.hzl.hadoop.exception.CommonException;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

	private static final DateTimeFormatter TIME_FORMATTER_MM = DateTimeFormatter.ofPattern(DataConstant.DATETIME_MM);


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

	public static LocalDate stringTolocalDate(String localDate) {
		if (StringUtils.hasLength(localDate)) {
			LocalDate localDateTimeConvert = LocalDate.parse(localDate, FORMATTER);
			return localDateTimeConvert;
		} else {
			return null;
		}

	}

	/**
	 * <p>
	 * 将字符串的LocalDateTime类型日期，转换成LocalDateTime类型
	 * localDateTime格式：2020-11-04 23:00
	 * </p>
	 *
	 * @author hzl 2020/01/03 5:17 PM
	 */
	public static LocalDateTime stringTolocalDateTimeM(String localDateTime) {
		if (StringUtils.hasLength(localDateTime)) {
			LocalDateTime localDateTimeConvert = LocalDateTime.parse(localDateTime, TIME_FORMATTER_MM);
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
	 * 将long转换为LocalDateTime
	 *
	 * @param timestamp
	 * @return
	 * @author hzl 2020-03-19 5:46 PM
	 */
	public static LocalDateTime longToLocalDateTime(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		//ZoneId zone = ZoneId.systemDefault();
		ZoneId zone = ZoneId.of(SHANGHAI);
		return LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 * 将LocalDateTime转换为long
	 *
	 * @param localDateTime
	 * @return
	 * @author hzl 2020-03-19 5:46 PM
	 */
	public static long localDateTimeTolong(LocalDateTime localDateTime) {
		//ZoneId zone = ZoneId.systemDefault();
		ZoneId zone = ZoneId.of(SHANGHAI);
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
	}

	/**
	 * <p>
	 * 获取今天凌晨的时间
	 * </p>
	 *
	 * @author hzl 2021/12/15 10:47 AM
	 */
	public static LocalDateTime getTodayInitTime(){
		return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
	}

	/**
	 * <p>
	 * 获取时间差,返回多少天，1.3天显示1天，2.6天显示2天
	 * </p>
	 *
	 * @author hzl 2021/12/15 10:47 AM
	 */
	public static String getTimeDiff(LocalDateTime localDateTime){

		return String.valueOf(LocalDateTime.now().until(localDateTime, ChronoUnit.DAYS)).concat("天");
	}
}
