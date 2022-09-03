import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;
import cn.hutool.extra.tokenizer.engine.ansj.AnsjEngine;
import cn.hutool.extra.tokenizer.engine.ansj.AnsjResult;
import cn.hutool.extra.tokenizer.engine.hanlp.HanLPEngine;
import cn.hutool.extra.tokenizer.engine.word.WordEngine;
import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

/**
 * description
 *AnsjEngine速度明显快
 * https://blog.csdn.net/k21325/article/details/54015899
 * https://www.hutool.cn/docs/#/extra/%E4%B8%AD%E6%96%87%E5%88%86%E8%AF%8D/%E4%B8%AD%E6%96%87%E5%88%86%E8%AF%8D%E5%B0%81%E8%A3%85-TokenizerUtil
 * https://github.com/NLPchina/ansj_seg
 * @author hzl 2022/07/28 10:34 AM
 */

public class KeyWordTest {


	private final Analysis analysis=new ToAnalysis();

	@Test
	public void keyword(){
		long beginTime = System.currentTimeMillis();
		TokenizerEngine engine = new AnsjEngine();
		//TokenizerEngine engine = new WordEngine();

        //解析文本
		String text = "放假时间";
		org.ansj.domain.Result result = analysis.parseStr(StrUtil.str(text));
        //输出：这 两个 方法 的 区别 在于 返回 值
		result.getTerms().forEach(term -> {
			System.out.println(term.toString());
		});
		long endTime = System.currentTimeMillis();
		System.out.println("总共消耗时间为：" + (endTime - beginTime));

	}

	@Test
	public void keyword1(){
		long beginTime = System.currentTimeMillis();
		TokenizerEngine engine = new AnsjEngine();
		//TokenizerEngine engine = new WordEngine();

		//解析文本
		String text = "客服价值客服";
		Result result = engine.parse(text);
        //输出：这 两个 方法 的 区别 在于 返回 值
		String resultStr = CollUtil.join((Iterable<Word>)result, " ");
		long endTime = System.currentTimeMillis();
		System.out.println(resultStr.toString());
		System.out.println("总共消耗时间为：" + (endTime - beginTime));
	}

	@Test
	public  void ss(){
		Integer s=new Integer(1);
		Integer s1=new Integer(2);
		System.out.println(s.compareTo(s1));
	}
}
