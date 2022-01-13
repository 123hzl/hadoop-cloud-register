package com.hzl.hadoop.gp.convert;

import com.hzl.hadoop.gp.vo.XlNewsVO;
import com.hzl.hadoop.util.LocalDateFormate;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hzl.hadoop.gp.constant.GpUrlConstant.NEWS_SOURCE;
import static com.hzl.hadoop.gp.constant.GpUrlConstant.SINGLE_STOCK_NEW;

/**
 * description
 * 该数据实时性不高
 * @author hzl 2021/12/15 9:15 AM
 */
@Slf4j
public class NewsConvert {

	//请求解析
	private List<XlNewsVO> parse(String gpCode) throws IOException {
		Document doc = Jsoup.connect(SINGLE_STOCK_NEW.concat(gpCode).concat(".phtml")).get();

		Elements datelist = doc.getElementById("con02-7").getElementsByClass("datelist");

		Elements ul = datelist.get(0).getElementsByTag("ul").get(0).getAllElements();

		//获取时间
		List<TextNode> values = ul.get(0).textNodes();


		List<XlNewsVO> xlNewsVOS = new ArrayList<>(16);
		for (int i = 1; i < ul.size(); i++) {
			//时间
			String time = values.get(i - 1).text().trim();
			//标题
			String title = ul.get(i).text();
			//链接
			String url = ul.get(i).attr("href");

			//log.info(time + title + url);

			//调用转换
			XlNewsVO xlNewsVO=convert(time,title,url);
			if(xlNewsVO!=null){
				xlNewsVOS.add(xlNewsVO);
			}
		}

		//log.info("新闻转换结果{}",xlNewsVOS);

		return xlNewsVOS;
	}

	//转换
	private XlNewsVO convert(String time,String title,String url){
		if(StringUtils.hasLength(time)){
			return XlNewsVO.builder()
					.releaseTime(LocalDateFormate.stringTolocalDateTimeM(time))
					.title(title)
					.url(url)
					.source(NEWS_SOURCE)
					.build();
		}

		return null;
	}


	public List<XlNewsVO> getXlNews(String gpCode) throws IOException {

		return parse(gpCode);
	}

}
