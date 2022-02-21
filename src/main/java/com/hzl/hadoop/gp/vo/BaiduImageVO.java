package com.hzl.hadoop.gp.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description
 * 百度图片接受类
 * @author hzl 2022/01/17 5:16 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaiduImageVO {


	private String queryEnc;
	private String queryExt;
	private int listNum;
	private long displayNum;
	private String gsm;
	private String bdFmtDispNum;
	private String bdSearchTime;
	private int isNeedAsyncRequest;
	private String bdIsClustered;
	private List<Object> data;

	private List<String> images;

}
