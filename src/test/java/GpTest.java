import com.hzl.hadoop.gp.constant.GpUrlConstant;
import com.hzl.hadoop.gp.convert.GpConvert;
import com.hzl.hadoop.gp.vo.GpVO;
import com.hzl.hadoop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * description
 * 股票接口测试
 *
 * @author hzl 2020/10/31 1:47 PM
 */
@Slf4j
public class GpTest {

	//伊利股票信息获取
	@Test
	public void getYl() {
		GpConvert gpConvert = new GpConvert();
		Map<String, String> date = gpConvert.getGpInfo(GpUrlConstant.GP_BASE_URL.concat("sz000651"), null);
		log.info("测试{}",date.toString());
		log.info(JsonUtils.mapToJson(date).toJSONString());
		//生成对象
		GpVO gpVO = new GpVO();
		//初始化对象参数
		gpVO.init(date);
		log.info(gpVO.toString());
	}

	//美的股票信息获取
	@Test
	public void getMd() {
		GpConvert gpConvert = new GpConvert();
		Map<String, String> date = gpConvert.getGpInfo(GpUrlConstant.MD_URL, null);
		log.info(JsonUtils.mapToJson(date).toJSONString());
	}
}