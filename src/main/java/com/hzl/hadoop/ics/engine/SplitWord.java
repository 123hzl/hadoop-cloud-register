package com.hzl.hadoop.ics.engine;


import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.domain.Result;

import java.util.List;

/**
 * description
 *
 * @author hzl 2022/07/28 1:57 PM
 */
@NoArgsConstructor
public class SplitWord {

	public Result keyword(String keyword) {
		//解析文本
		Result result = ToAnalysis.parse(StrUtil.str(keyword));
		return result;
	}



}
