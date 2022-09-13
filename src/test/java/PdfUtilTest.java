import com.hzl.hadoop.util.PdfUtil;
import com.hzl.hadoop.util.textCompare.diff_match_patch;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.hzl.hadoop.util.PdfUtil.*;

/**
 * description
 *
 * @author hzl 2020/01/02 5:52 PM
 */
@Slf4j
public class PdfUtilTest {

	@Test
	public void concatPDFsNewTest() throws Exception {
		try {

			String file1 = "http://localhost:8080/hfle/v1/0/files/download-by-key?fileKey=0/1a1d2260dc9a4e5281a0617f92c712ba@%E6%B0%B8%E5%8D%87sql%E6%96%87%E6%A1%A3.pdf&access_token=ad8d22d7-4ec6-4aa7-94de-a6b524fdea4d";
			String file2 = "http://localhost:8080/hfle/v1/0/files/download-by-key?fileKey=0/1a1d2260dc9a4e5281a0617f92c712ba@%E6%B0%B8%E5%8D%87sql%E6%96%87%E6%A1%A3.pdf&access_token=ad8d22d7-4ec6-4aa7-94de-a6b524fdea4d";
			String savePath = "/Users/hzl/Desktop/测试.pdf";
			List<String> streamOfPDFFiles = new ArrayList<>();
			streamOfPDFFiles.add(file1);
			streamOfPDFFiles.add(file2);
			OutputStream outputStream = new FileOutputStream(savePath);
			concatPdf(streamOfPDFFiles, outputStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void concatPDFsAndAddPageTest() throws Exception {
		try {

			String file1 = "http://localhost:8080/hfle/v1/0/files/download-by-key?fileKey=0/1a1d2260dc9a4e5281a0617f92c712ba@%E6%B0%B8%E5%8D%87sql%E6%96%87%E6%A1%A3.pdf&access_token=ad8d22d7-4ec6-4aa7-94de-a6b524fdea4d";
			String file2 = "http://localhost:8080/hfle/v1/0/files/download-by-key?fileKey=0/1a1d2260dc9a4e5281a0617f92c712ba@%E6%B0%B8%E5%8D%87sql%E6%96%87%E6%A1%A3.pdf&access_token=ad8d22d7-4ec6-4aa7-94de-a6b524fdea4d";
			String savePath = "/Users/hzl/Desktop/测试.pdf";
			List<String> streamOfPDFFiles = new ArrayList<>();
			streamOfPDFFiles.add(file1);
			streamOfPDFFiles.add(file2);
			OutputStream outputStream = new FileOutputStream(savePath);
			concatPdfsAndAddPage(streamOfPDFFiles, outputStream, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 文件添加水印二维码
	 * </p>
	 *
	 * @author hzl 2020/01/02 7:25 PM
	 */
	@Test
	public void addWaterCode() {
		OutputStream outputStream = null;

		ByteArrayOutputStream tempOutputStream = new ByteArrayOutputStream();

		String url = "http://ekpapp.ysservice.com.cn/km/review/km_review_main/kmReviewMain.do?method=view&fdId=17338879f9e9254e33fd69249f9a710e";
		String content = "SRDC2020000531";
		String water = "就我一个人";

		try {
			FileSystemResource tempInputStream = new FileSystemResource("/Users/hzl/Desktop/机考攻略.pdf");
			//FileInputStream fileInputStream =new FileInputStream(new File("/Users/hzl/Desktop/pdf.pdf"));
			outputStream = new FileOutputStream("/Users/hzl/Desktop/机考攻略1.pdf");
			addPdfTextMark(tempInputStream.getInputStream(), tempOutputStream, water, 200, 200, url, content);
			outputStream.write(tempOutputStream.toByteArray());
			tempOutputStream.flush();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 移除pdf涂层
	 *
	 * @param null
	 * @return
	 * @author hzl 2021-10-21 7:50 PM
	 */
	@Test
	public void removeWatermark() {
		PdfUtil.removeWatermark("/Users/hzl/Desktop/机考攻略1.pdf", "/Users/hzl/Desktop/机考攻略2.pdf");
	}

	/**
	 * <p>
	 * 文件合并测试传文件流
	 * </p>
	 *
	 * @author hzl 2020/01/06 2:37 PM
	 */
	@Test
	public void pdfAdd() throws IOException {
		FileSystemResource tempInputStream1 = new FileSystemResource("/Users/hzl/Desktop/1.pdf");
		FileSystemResource tempInputStream2 = new FileSystemResource("/Users/hzl/Desktop/11.pdf");
		List<InputStream> streamOfPDFFiles = new ArrayList<>();
		OutputStream outputStream = new FileOutputStream("/Users/hzl/Desktop/tt.pdf");

		streamOfPDFFiles.add(tempInputStream2.getInputStream());
		streamOfPDFFiles.add(tempInputStream1.getInputStream());
		concatPDFsNew1(streamOfPDFFiles, outputStream);

	}

	public static void concatPDFsNew1(List<InputStream> streamOfPDFFiles,
									  OutputStream outputStream) {
		Document document = new Document();
		try {
			List<InputStream> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<InputStream> iteratorPDFs = pdfs.iterator();

			InputStream pdf = null;
			PdfReader pdfReader = null;
			while (iteratorPDFs.hasNext()) {
				pdf = iteratorPDFs.next();
				if (null != pdf) {
					pdfReader = new PdfReader(pdf);

					if (null != pdfReader) {
						readers.add(pdfReader);
						totalPages += pdfReader.getNumberOfPages();
					}
				}
			}
			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			BaseFont bf = BaseFont.createFont(PdfUtil.FONTS_PATH,
					"Identity-H", true);
			PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			while (iteratorPDFReader.hasNext()) {
				pdfReader = iteratorPDFReader.next();

				if (null != pdfReader) {
					while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
						document.setPageSize(pdfReader.getPageSize(pageOfCurrentReaderPDF + 1));
						document.newPage();
						pageOfCurrentReaderPDF++;
						currentPageNumber++;

						page = writer.getImportedPage(pdfReader,
								pageOfCurrentReaderPDF);

						cb.addTemplate(page, 0, 0);
					}
				}
				pageOfCurrentReaderPDF = 0;
			}
			outputStream.flush();
			if (document.isOpen()) {
				document.close();
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document.isOpen()) {
				document.close();
			}
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}


	/**
	 * <p>
	 * 获取pdf文字内容测试
	 * </p>
	 *
	 * @author hzl 2020/01/10 2:43 PM
	 */
	@Test
	public void getPdfFileStr() throws IOException {
		File source = new File("/Users/hzl/Desktop/电子.pdf");
		FileInputStream fileInputStream = new FileInputStream(source);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffers = new byte[1024 * 4];
		int n = 0;
		while (-1 != (n = fileInputStream.read(buffers))) {
			output.write(buffers, 0, n);
		}
		log.info(PdfUtil.getPdfFileStr(output.toByteArray()));
		fileInputStream.close();
		output.flush();
		output.close();
	}


	/**
	 * <p>
	 * 获取pdf文字内容测试
	 * </p>
	 *
	 * @author hzl 2020/01/10 2:43 PM
	 */
	public String getPdfFileStrP(File source) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(source);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffers = new byte[1024 * 4];
		int n = 0;
		while (-1 != (n = fileInputStream.read(buffers))) {
			output.write(buffers, 0, n);
		}
		log.info(PdfUtil.getPdfFileStr(output.toByteArray()));
		String text = PdfUtil.getPdfFileStr(output.toByteArray());
		output.flush();
		output.close();
		return text;
	}

	/**
	 * <p>
	 * 获取pdf文字内容测试
	 * </p>
	 *
	 * @author hzl 2020/01/10 2:43 PM
	 */
	@Test
	public void getPdfFileStrAndCompare() throws IOException {
		File source = new File("/Users/hzl/Desktop/11.pdf");
		File target = new File("/Users/hzl/Desktop/22.pdf");
		String sourceText = getPdfFileStrP(source);
		String targetText = getPdfFileStrP(target);

		diff_match_patch dmp = new diff_match_patch();
		//参数text2相对text1的变动
		LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(sourceText, targetText, true);
		dmp.diff_cleanupSemantic(diff);
		System.out.println(diff);

	}
}
