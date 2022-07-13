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
 		System.out.println(GpPercentConvert.parse("sh600138","1"));
	}
}
