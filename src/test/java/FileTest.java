import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.hzl.hadoop.util.IoUtils.testRead;

/**
 * description
 *
 * @author hzl 2020/01/14 5:04 PM
 */
public class FileTest {

	@Test
	public void fileContentRead() {
		try {
			File file=new File("//Users/hzl/Desktop/DN202207_A_001120220811105512/成本票/pi号/");
			file.mkdirs();
			Path basePath = Paths.get("/Users/hzl/Desktop/DN202207_A_001120220811105512/成本票/pi号");
			Files.createDirectory(basePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
