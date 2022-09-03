package com.hzl.hadoop.file.controller;

import com.hzl.hadoop.file.service.FillExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * description
 *
 * @author hzl 2022/08/09 10:02 AM
 */
@Controller
@Slf4j
public class FillExcelController {

	@Autowired
	FillExcelService fillExcelService;

	/**
	 * <p>
	 * 通用文件下载，可以下载任何格式的文件
	 * </p>
	 *
	 * @author hzl 2020/01/05 2:36 PM
	 */
	@GetMapping(value = "/fill/download/file")
	public void downFileResponse(HttpServletResponse response) {

		fillExcelService.estimateBill(response);
	}
}
