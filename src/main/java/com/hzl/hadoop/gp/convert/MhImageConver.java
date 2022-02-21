package com.hzl.hadoop.gp.convert;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hzl.hadoop.gp.vo.XlNewsVO;
import com.hzl.hadoop.util.HttpResponseException;
import com.hzl.hadoop.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description
 * http://m.alimanhua.com/manhua/2636/205238.html
 * 先爬列表，再爬文件
 *
 * @author hzl 2022/02/11 2:03 PM
 */
@Slf4j
public class MhImageConver {

	private static String URL = "http://m.alimanhua.com";

	//请求解析
	private static void parse(String gpCode) throws IOException {
		Document doc = Jsoup.connect(URL.concat(gpCode)).get();


		Elements ul = doc.getElementById("chapterList2").getElementsByTag("ul").get(0).getElementsByTag("a");
		//存储每章节的html地址
		List<String> page= new ArrayList<>(ul.size());

		for (int i = 1; i < 2; i++) {

			//链接
			String url = ul.get(1).attr("href");


			page.add(URL.concat(url));
		}


		page.forEach(a->{
			//获取每章的所有图片
			try {
				//HttpUtils.sendGet(a);
				String html=runJs(a);
				log.info("测试1111"+html);

				Document pageDocs = Jsoup.parse(html);

				Elements images=pageDocs.getElementById("manga").getElementsByTag("img");
				//log.info(images.toString());

				//每章节所有的图片
				List<String> pageImages=images.stream().map(img->img.attr("src")).collect(Collectors.toList());
				//log.info(pageImages.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}


		});
	}


	public static String runJs(String url) throws IOException {
		log.info("请求地址{}",url);
		//请求超时时间,默认20秒
		int timeout = 20000;
		//等待异步JS执行时间,默认20秒
		int waitForBackgroundJavaScript = 20000;
		String result = "";

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);

		webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);//是否启用CSS
		webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

		webClient.getOptions().setTimeout(timeout);//设置“浏览器”的请求超时时间
		webClient.setJavaScriptTimeout(timeout);//设置JS执行的超时时间

		HtmlPage page;
		try {
			page = webClient.getPage(url);
		} catch (IOException e) {
			webClient.close();
			throw e;
		}
		webClient.waitForBackgroundJavaScript(waitForBackgroundJavaScript);//该方法阻塞线程

		result = page.asXml();
		webClient.close();
		log.info("结果{}",result);
		return result;
	}




	public static void main(String args[]) throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngine = manager.getEngineByName("js");

		Object result =scriptEngine.eval("");
		log.info(String.valueOf(result));
	}

}
