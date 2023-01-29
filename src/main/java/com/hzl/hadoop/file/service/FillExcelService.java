package com.hzl.hadoop.file.service;

import javax.servlet.http.HttpServletResponse;

/**
 * description
 * excel模版填充
 * @author hzl 2022/08/03 3:31 PM
 */
public interface FillExcelService {

	void estimateBill(HttpServletResponse response);

	void estimateBillNew(HttpServletResponse response);
}
