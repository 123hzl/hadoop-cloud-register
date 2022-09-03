package com.hzl.hadoop.util.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.bigdecimal.BigDecimalNumberConverter;
import com.alibaba.excel.converters.date.DateStringConverter;
import com.alibaba.excel.converters.integer.IntegerNumberConverter;
import com.alibaba.excel.converters.longconverter.LongNumberConverter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * description
 *
 * @author hzl 2020/11/16 3:23 PM
 */
@Slf4j
public class EasyExcelUtils {
	/**
	 * @param response
	 * @param list     显示的字段名,excel的列头
	 * @param list     数据内容
	 * @param fileName 导出的文件名
	 * @throws IOException
	 */
	public static void downloadFailedUsingJson(HttpServletResponse response,
											   List<LinkedHashMap<String, Object>> list, String fileName) throws IOException {

		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");

		// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

		// 这里需要设置不关闭流
		EasyExcel.write(response.getOutputStream()).registerConverter(new IntegerNumberConverter())
				.registerConverter(new BigDecimalNumberConverter())
				.registerConverter(new DateStringConverter())
				.registerConverter(new LongNumberConverter())
				//todo 待优化
//				.registerConverter(new BigIntegerNumberConverter())
//				.registerConverter(new LocalDateTimeConverter())
//				.registerConverter(new TimeStampStringConverter())
//				.registerConverter(new SqlDateStringConverter())
				.autoCloseStream(Boolean.TRUE)
				.head(head(list))
				.sheet("模板")
				.doWrite(dataList(list));

	}

	/**
	 * 通过解析显示字段集合得到列头
	 *
	 * @param listLayoutFieldList
	 * @return
	 */
	private static List<List<String>> head(List<LinkedHashMap<String, Object>> listLayoutFieldList) {
		log.info("keys结果1" + listLayoutFieldList.get(0).keySet());
		String keys[] = listLayoutFieldList.get(0).keySet().toArray(new String[0]);
		List<List<String>> list = new ArrayList<List<String>>();
		listLayoutFieldList.get(0).keySet().forEach(a -> {
			List<String> title = new ArrayList<>();
			title.add(a);
			list.add(title);
		});
		log.info("keys结果2" + list.toString());
		return list;
	}

	/**
	 * 通过解析所有数据将数据写入excel中，有特殊业务处理也可在这里进行
	 *
	 * @param list
	 * @return
	 */
	private static List<List<Object>> dataList(List<LinkedHashMap<String, Object>> list) {
		List<List<Object>> excellist = new ArrayList<List<Object>>();
		for (LinkedHashMap<String, Object> map : list) {
			List<Object> data = new ArrayList<Object>();
			for (String e : map.keySet()) {
				if (!("isdelete").equals(e)) {
					data.add(map.get(e));
				}

			}
			excellist.add(data);
		}
		return excellist;
	}

}
