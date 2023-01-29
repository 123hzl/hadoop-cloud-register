package com.hzl.hadoop.file.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.hzl.hadoop.file.excel.ExcelMergeHandler;
import com.hzl.hadoop.file.excel.dto.PriceDTO;
import com.hzl.hadoop.file.excel.dto.TranportDTO;
import com.hzl.hadoop.file.service.FillExcelService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * description
 * excel模版填充
 * @author hzl 2022/08/03 3:32 PM
 */
@Service
public class FillExcelServiceImpl implements FillExcelService {


	@Override
	public void estimateBill(HttpServletResponse response) {

		//获取模板
		ClassPathResource classPathResource = new ClassPathResource("templates/预估账单(票结).xlsx");
		try (InputStream inputStream = classPathResource.getInputStream();
			 ServletOutputStream outputStream = response.getOutputStream()) {

			response.setContentType("application/x-msdownload;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("预估账单(票结).xlsx", "utf-8"));

			//设置输出流和模板信息
			ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
			WriteSheet writeSheet = EasyExcel.writerSheet().build();
			//开启自动换行,自动换行表示每次写入一条list数据是都会重新生成一行空行,此选项默认是关闭的,需要提前设置为true
			FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

			List<PriceDTO> lists=new ArrayList<>();
			PriceDTO priceDTO=new PriceDTO();
			priceDTO.setFeeName("测试1");
			priceDTO.setFeePrice(new BigDecimal(100.00));
			PriceDTO priceDTO1=new PriceDTO();
			priceDTO1.setFeeName("测试2");
			priceDTO1.setFeePrice(new BigDecimal(200.00));
			lists.add(priceDTO);
			lists.add(priceDTO1);

			excelWriter.fill(lists, fillConfig, writeSheet);
			//excelWriter.fill(map,writeSheet);
			excelWriter.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void estimateBillNew(HttpServletResponse response) {
		//获取模板
		ClassPathResource classPathResource = new ClassPathResource("templates/供应商账单模板.xlsx");
		try (InputStream inputStream = classPathResource.getInputStream();
			 ServletOutputStream outputStream = response.getOutputStream()) {

			response.setContentType("application/x-msdownload;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("预估账单(票结).xlsx", "utf-8"));

			//数据组装
			TranportDTO feesDTO1=new TranportDTO();
			feesDTO1.setTransportFeeTitle("运费明细（以具体明细显示）");
			feesDTO1.setTransportFee("费用1");
			TranportDTO feesDTO2=new TranportDTO();
			feesDTO2.setTransportFeeTitle("运费明细（以具体明细显示）");
			feesDTO2.setTransportFee("费用2");
			TranportDTO feesDTO3=new TranportDTO();
			feesDTO3.setTransportFeeTitle("运费明细（以具体明细显示）");
			feesDTO3.setTransportFee("费用3");

			TranportDTO feesDTO4=new TranportDTO();
			feesDTO4.setTransportFeeTitle("代垫费明细（以具体明细显示）");
			feesDTO4.setTransportFee("费用4");
			TranportDTO feesDTO5=new TranportDTO();
			feesDTO5.setTransportFeeTitle("代垫费明细（以具体明细显示）");
			feesDTO5.setTransportFee("费用5");

			TranportDTO feesDTO6=new TranportDTO();
			feesDTO6.setTransportFeeTitle("合计费用");
			feesDTO6.setTransportFee("费用6");
			//运费明细大小
			int transportFeesize=3;
			//代垫费明细大小
			int advanceSize=2;

			List<TranportDTO> tranportDTO=new ArrayList<>();
			tranportDTO.add(feesDTO1);
			tranportDTO.add(feesDTO2);
			tranportDTO.add(feesDTO3);
			tranportDTO.add(feesDTO4);
			tranportDTO.add(feesDTO5);
			tranportDTO.add(feesDTO6);

			//设置输出流和模板信息
			ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
			WriteSheet writeSheet = EasyExcel.writerSheet().build();
			//自动合并头，头中相同的字段上下左右都会去尝试匹配
			WriteSheet writeSheet1 = EasyExcel.writerSheet().registerWriteHandler(new ExcelMergeHandler(transportFeesize,advanceSize)).build();

			//水平填充
			FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).forceNewRow(Boolean.TRUE).autoStyle(true).build();


			excelWriter.fill(tranportDTO, fillConfig, writeSheet1);
			excelWriter.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
