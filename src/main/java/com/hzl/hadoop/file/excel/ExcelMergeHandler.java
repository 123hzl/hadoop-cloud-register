package com.hzl.hadoop.file.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import java.util.List;
import java.util.Map;

/**
 * description
 * 参考：https://blog.csdn.net/weixin_44682948/article/details/112897500
 * @author hzl 2022/09/07 3:26 PM
 */
public class ExcelMergeHandler extends AbstractMergeStrategy {

	//运费明细大小
	private int transportFeesize;
	//代垫费明细大小
	private int advanceSize;

	public ExcelMergeHandler(int transportFeesize, int advanceSize) {
		this.transportFeesize = transportFeesize;
		this.advanceSize = advanceSize;
	}

	public ExcelMergeHandler() {
	}

	@Override
	protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
		if(relativeRowIndex==null ||relativeRowIndex==0){
			return;
		}

		sheet=cell.getSheet();

		CellStyle cs = cell.getCellStyle();
		cell.setCellStyle(cs);

		//运费明细（以具体明细显示）合并，只修改lastcol
		CellRangeAddress cra = new CellRangeAddress(2, 2, 11, 11+transportFeesize-1);
		sheet.addMergedRegionUnsafe(cra);
		RegionUtil.setBorderBottom(BorderStyle.THIN, cra, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, cra, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, cra, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, cra, sheet);

		//代垫费明细（以具体明细显示）合并，只修改firstcol（运费明细lastcol+1），lastcol
		CellRangeAddress cra1 = new CellRangeAddress(2, 2, 11+transportFeesize, 11+transportFeesize+advanceSize-1);
		sheet.addMergedRegionUnsafe(cra1);
		RegionUtil.setBorderBottom(BorderStyle.THIN, cra1, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, cra1, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, cra1, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, cra1, sheet);


		//合计费用合并只修改firstcol（运费明细代垫费明细lastcol+1），lastcol（运费明细代垫费明细lastcol+1）
		CellRangeAddress cra2 = new CellRangeAddress(2, 3, 11+transportFeesize+advanceSize, 11+transportFeesize+advanceSize);
		sheet.addMergedRegionUnsafe(cra2);
		RegionUtil.setBorderBottom(BorderStyle.THIN, cra2, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, cra2, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, cra2, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, cra2, sheet);

		//只改lastCol：16 ，16为最后一列的位置（运费明细代垫费明细lastcol+1）
		CellRangeAddress cra3 = new CellRangeAddress(0, 1, 0, 11+transportFeesize+advanceSize);
		sheet.addMergedRegionUnsafe(cra3);
		RegionUtil.setBorderBottom(BorderStyle.THIN, cra3, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, cra3, sheet);
		RegionUtil.setBorderRight(BorderStyle.THIN, cra3, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, cra3, sheet);
	}

}
