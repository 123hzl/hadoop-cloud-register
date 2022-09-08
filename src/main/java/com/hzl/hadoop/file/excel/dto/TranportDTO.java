package com.hzl.hadoop.file.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/09/07 1:39 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TranportDTO {

	/**
	 *运费明细
	*/
	private List<FeesDTO> transportFees;

	private String transportFee;

	private String transportFeeTitle;
}
