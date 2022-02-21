import com.hzl.hadoop.gp.convert.GpPercentConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description
 *
 * @author hzl 2022/01/24 9:18 AM
 */
@Slf4j
public class GpPercentConvertTest {


	@Test
	public void getPercent() {
 		GpPercentConvert.parse("sh600597","1");
	}
}
