package com.hzl.hadoop.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 * 将excel解析成map
 *
 * @author hzl 2020/01/06 11:04 AM
 */
@Slf4j
public class ExcelParseToMapUtil {
	public static final String EXCEL_SUFFIX_XLS = ".xls";
	public static final String EXCEL_SUFFIX_XLSX = ".xlsx";

	/**
	 * 将excel数据按照不同的sheet页解析进Map
	 *
	 * @param file 导入的excel文件
	 * @return Map<String               ,               List                               <               Map               <               String               ,               String>>> key:sheet名,value : (List<Map<String,String>>) sheet页中行数据的集合（不包括头信息）
	 * Map<String,String>   key:字段名  ,  value : 字段值
	 * @throws Exception
	 */
	public static Map<String, List<Map<String, String>>> parseExcelFile(MultipartFile file) throws Exception {
		//sheetMap：key为sheet名，value为该页中所有行的集合
		Map<String, List<Map<String, String>>> sheetMap = new HashMap<>(16);
		//一个sheet页中所有行的集合，第一条数据为表头
		List<Map<String, String>> lineList;
		//LineMap：key为字段名，value为cell值
		Map<String, String> LineMap;
		Workbook workbook;
		try (InputStream inputStream = file.getInputStream()) {
			String originalFileName = file.getOriginalFilename();
			if (originalFileName.endsWith(EXCEL_SUFFIX_XLSX)) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (originalFileName.endsWith(EXCEL_SUFFIX_XLS)) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				throw new IOException("文件格式不对");
			}
		} catch (IOException e) {
			throw new IOException("EXCEL打开错误");
		}

		int sheetNum = workbook.getNumberOfSheets();
		//循环所有sheet页
		for (int i = 0; i < sheetNum; i++) {
			boolean isFirstRow = true;
			Map<Integer, String> titleMap = new HashMap<>(16);
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() == 0) {
				continue;
			}
			lineList = new ArrayList<>();
			//循环遍历sheet页中所有的行
			for (int numRow = sheet.getFirstRowNum(); numRow <= sheet.getLastRowNum(); numRow++) {
				log.info("第几行：" + numRow);
				LineMap = new HashMap<>(16);
				Row row = sheet.getRow(numRow);
				if (null == row) {
					continue;
				}
				//获取行中所有的第一列
				int numbCell = row.getFirstCellNum();
				//获取行中所有的最后一列
				int lastNum = row.getLastCellNum();
				for (; numbCell < lastNum; numbCell++) {
					log.info("第几列:" + numbCell);
					Cell cell = row.getCell(numbCell);
					log.info("row------->" + row.getRowNum() + "     " + numRow);
					String value = handleCellDataType(cell);
					if (isFirstRow) {
						titleMap.put(numbCell, value);
					} else {
						LineMap.put(titleMap.get(numbCell), value);
					}
				}
				if (!isFirstRow) {
					lineList.add(LineMap);
				}
				isFirstRow = false;
			}
			sheetMap.put(sheet.getSheetName(), lineList);
		}
		return sheetMap;
	}

	/**
	 * 单元格不同数据类型数值处理
	 *
	 * @param cell Cell
	 * @return 单元格值的字符串格式
	 */
	private static String handleCellDataType(Cell cell) {
		if (null == cell) {
			return "";
		}
		String value = "";
		// 格式化number
		DecimalFormat df = new DecimalFormat("#.#########");
		// 日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 格式化数字
		DecimalFormat df2 = new DecimalFormat("0.00");
		switch (cell.getCellType()) {
			case NUMERIC:
				String format = cell.getCellStyle().getDataFormatString();
				//TODO 数字处理完善
				if ("General".equals(format)) {
					value = df.format(cell.getNumericCellValue());
				} else if ("m/d/yy".equals(format)) {
					value = sdf.format(cell.getDateCellValue());
				} else if (DateUtil.isCellDateFormatted(cell)) {
					value = sdf.format(cell.getDateCellValue());
				} else {
					value = df2.format(cell.getNumericCellValue());
				}
				break;
			case STRING:
				value = cell.getRichStringCellValue().getString();
				break;
			case FORMULA:
				try {
					value = String.valueOf(cell.getStringCellValue());
				} catch (IllegalStateException e) {
					log.info("---type--" + cell.getCellType());
					value = String.valueOf(cell.getNumericCellValue());
				}
				break;
			case BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				break;
		}
		return value;
	}
}
