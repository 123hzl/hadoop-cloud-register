import com.hzl.hadoop.app.vo.Pinyin;
import com.hzl.hadoop.util.equator.Equator;
import com.hzl.hadoop.util.equator.FieldInfo;
import com.hzl.hadoop.util.equator.GetterBaseEquator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * description
 *
 * @author hzl 2023/02/08 3:06 PM
 */
@Slf4j
public class EquatorTest {

	@Test
	public void test() {
		//对比变更信息
		Equator equator = new GetterBaseEquator();
		Pinyin pinyin=new Pinyin();
		pinyin.setCh(true);
		Pinyin pinyin1=new Pinyin();
		pinyin1.setCh(true);
		// 获取不同的属性
		List<FieldInfo> diffCompanyInfo = equator.getDiffFields(pinyin, pinyin1);
		log.info(diffCompanyInfo.toString());
	}
}
