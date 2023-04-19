import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.engine.ansj.AnsjEngine;
import com.hzl.hadoop.util.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author hzl 2023/04/12 11:44 AM
 */
@Slf4j
public class ListUtilsTest {


	@Test
	public void keyword(){
		List<Long> a=new ArrayList<>();
		a.add(1L);
		a.add(2L);
		a.add(3L);
		List<Long> b=new ArrayList<>();
		//b.add(1L);
		b.add(2L);
		b.add(3L);



		log.info(String.valueOf(CollectionUtils.subtract(a,CollectionUtils.intersection(a, b))));

	}

}
