import com.hzl.hadoop.gp.constant.GpCodeEnum;
import com.hzl.hadoop.gp.convert.NewsConvert;
import com.hzl.hadoop.gp.service.impl.XinLangNewsImpl;
import com.hzl.hadoop.gp.vo.XlNewsVO;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * description
 *
 * @author hzl 2021/12/15 9:05 AM
 */
public class XinLangNewsImplTest {

	@Test
	public void parse() throws IOException {

//		NewsConvert xinLangNews = new NewsConvert();
//
//		List<XlNewsVO> xlNewsVOS= xinLangNews.getXlNews("sz000333");

		System.out.println(GpCodeEnum.valueOf("sz000063").getCode());

	}

	@Test
	public void getTodayNews() throws IOException {
//		XinLangNewsImpl xinLangNews = new XinLangNewsImpl();
//		xinLangNews.getTodayNews(GpCodeEnum.sz000333);
	}

}
