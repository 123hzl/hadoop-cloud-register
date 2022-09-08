package com.hzl.hadoop.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.security.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * pdf文件加水印，二维码，条形码，页码，文件合并
 *
 * @author hzl 2020/01/02 5:27 PM
 */
@Slf4j
public class PdfUtil {
	//字体文件
	public static final String FONTS_PATH = "/fonts/simsun.ttf";

	public static final BaseColor WATER = new BaseColor(224, 224, 224);

	public static final String KEYSTORE = "F:\\ZzCert\\test.p12";
	public static final char[] PASSWORD = "111111".toCharArray();//keystory密码
	public static final String SRC = "F:\\test\\src.pdf";
	public static final String DEST = "F:\\test\\signed_dest.pdf";

	/**
	 * <p>
	 * streamOfPDFFiles文件地址，
	 * 合并pdf不加页码
	 * </p>
	 *
	 * @author hzl 2020/01/02 5:40 PM
	 */
	public static void concatPdf(List<String> streamOfPDFFiles,
								 OutputStream outputStream) {
		Document document = new Document();
		try {
			List<String> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<String> iteratorPDFs = pdfs.iterator();

			String pdf = null;
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
			BaseFont bf = BaseFont.createFont(FONTS_PATH,
					"Identity-H", true);
			PdfContentByte cb = writer.getDirectContent();

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPdfReader = readers.iterator();

			while (iteratorPdfReader.hasNext()) {
				pdfReader = iteratorPdfReader.next();

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
	 * 合并pdf并添加页码，如果文件地址集合长度为1，则只给文件加页码不合并
	 * streamOfPDFFiles文件地址
	 * paginate 是否添加页码
	 * </p>
	 *
	 * @author hzl 2020/01/02 5:59 PM
	 */
	public static void concatPdfsAndAddPage(List<String> streamOfPdfFiles,
											OutputStream outputStream, boolean paginate) {
		Document document = new Document();
		try {
			List<String> pdfs = streamOfPdfFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<String> iteratorPDFs = pdfs.iterator();
			Rectangle rectangle = PageSize.A4;
			// Create Readers for the pdfs.
			String pdf = null;
			PdfReader pdfReader = null;
			while (iteratorPDFs.hasNext()) {
				pdf = iteratorPDFs.next();
				if (null != pdf) {
					pdfReader = new PdfReader(pdf);
					rectangle = pdfReader.getPageSize(1);

					if (null != pdfReader) {
						readers.add(pdfReader);
						totalPages += pdfReader.getNumberOfPages();
					}
				}
			}
			// Create a writer for the outputstream
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			document.open();
			log.info("文件大小" + rectangle);
			document.setPageSize(rectangle);
			BaseFont bf = BaseFont.createFont(FONTS_PATH,
					"Identity-H", true);
			PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
			// data

			PdfImportedPage page;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPdf = 0;
			Iterator<PdfReader> iteratorPdfReader = readers.iterator();

			while (iteratorPdfReader.hasNext()) {
				pdfReader = iteratorPdfReader.next();
				// Create a new page in the target for each source page.
				if (null != pdfReader) {
					while (pageOfCurrentReaderPdf < pdfReader.getNumberOfPages()) {
						log.info("文件大小" + pdfReader.getPageSize(pageOfCurrentReaderPdf + 1));
						document.setPageSize(pdfReader.getPageSize(pageOfCurrentReaderPdf + 1));
						document.newPage();
						pageOfCurrentReaderPdf++;
						currentPageNumber++;
						page = writer.getImportedPage(pdfReader,
								pageOfCurrentReaderPdf);
						cb.addTemplate(page, 0, 0);

						// Code for pagination.
						if (paginate) {
							cb.beginText();
							cb.setFontAndSize(bf, 9);
							cb.showTextAligned(PdfContentByte.ALIGN_CENTER, ""
											+ "第 " + currentPageNumber + " 页，共 " + totalPages + " 页", 520,
									5, 0);
							cb.endText();
						}
					}
				}
				pageOfCurrentReaderPdf = 0;
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
	 * 添加水印二维码方法
	 *
	 * @param put        输入流
	 * @param out        输出流
	 * @param textMark   水印内容
	 * @param textWidth  水印x
	 * @param textHeight 水印y
	 * @param textHeight 二维码内容
	 *                   contractNumber插入的文字内容
	 */
	public static void addPdfTextMark(InputStream put, OutputStream out, String textMark, int textWidth, int textHeight,
									  String codeStr, String contractNumber) throws Exception {

		PdfReader reader = new PdfReader(put, "PDF".getBytes());
		PdfStamper stamp = new PdfStamper(reader, out);

		if (StringUtils.isNotBlank(contractNumber)) {
			addContent(reader, stamp, contractNumber);
		}
		PdfContentByte under;

		BaseFont font = BaseFont.createFont(FONTS_PATH, "Identity-H", true);// 使用系统字体


		// 加二维码结束
		// 添加水印
		if (!StringUtils.isBlank(textMark)) {

			PdfGState gs = new PdfGState();
			// gs.setFillOpacity(0.2f);
			// 设置笔触字体不透明度为0.4f
			gs.setFillOpacity(0.5f);
			// 原pdf文件的总页数
			int pageSize = reader.getNumberOfPages();


			for (int i = 1; i <= pageSize; i++) {

				log.info("纸张大小" + i + "页" + reader.getPageSize(i));
				// 水印在文本下
				under = stamp.getOverContent(i);
				//水印在文本上
				// under = stamp.getOverContent(i);
				under.beginText();
				// 文字水印 颜色
				under.setColorFill(WATER);
				// 文字水印 字体及字号
				under.setFontAndSize(font, 16);
				// 文字水印 起始位置
				under.setTextMatrix(textWidth, textHeight);
				Rectangle pageSize1 = reader.getPageSize(i);
				float height = pageSize1.getHeight();
				float width = pageSize1.getWidth();

				// 设置透明度
				under.setGState(gs);
				for (int l = 1; l <= 3; ++l) {
					float x = 0f;
					switch (l) {
						case 1:
							x = 50;
							break;
						case 2:
							x = width / 2;
							break;
						case 3:
							x = width - 100;
							break;
						default:

					}
					for (int sect = 1; sect <= 3; ++sect) {
						float y = 0;
						switch (sect) {
							case 1:
								y = 0;
								break;
							case 2:
								y = height / 2;
								break;
							case 3:
								y = height - 100;
								break;
							default:
						}

						under.showTextAligned(Element.ALIGN_LEFT, textMark, x, y, 40);

					}
				}
				// 添加水印结束
				under.endText();
			}
		}

		// 加二维码
		if (!StringUtils.isBlank(codeStr)) {
			addCode(codeStr, stamp, reader);
		}

		stamp.close();// 关闭
		reader.close();
	}

	/**
	 * 移除水印,测试可用
	 *
	 * @param srcPath   带水印pdf
	 * @param buildPath 去除水印pdf
	 * @return
	 */
	public static String removeWatermark(String srcPath, String buildPath) {

		//注意，这将破坏所有层的文档中，只有当你没有额外的层使用
		try {
			PdfReader reader = new PdfReader(srcPath);
			//从文档中彻底删除的OCG组。
			//占位符变量
			reader.removeUnusedObjects();
			int pageCount = reader.getNumberOfPages();
			PRStream prStream = null;
			PdfDictionary curPage;
			PdfArray contentarray;
			//循环遍历每个页面
			for (int i = 1; i <= pageCount; i++) {
				//获取页面
				curPage = reader.getPageN(i);
				//获取原始内容
				contentarray = curPage.getAsArray(PdfName.CONTENTS);
				if (contentarray != null) {
					//循环遍历内容
					//获取涂层数量
					System.out.println("长度" + contentarray.size());
					for (int j = 0; j < contentarray.size(); j++) {
						//获取原始字节流
						prStream = (PRStream) contentarray.getAsStream(j);
						// 去除指定涂层，默认水印为数组下标最大的涂层,替换contentarray.size()-1可以删除指定涂层
						if (j == contentarray.size() - 1) {
							//给它零长度和零数据删除它
							prStream.put(PdfName.LENGTH, new PdfNumber(0));
							prStream.setData(new byte[0]);
						}
					}
				}
			}
			//写出来的内容
			FileOutputStream fos = new FileOutputStream(buildPath);
			Document doc = new Document(prStream.getReader().getPageSize(1));
			PdfCopy copy = new PdfCopy(doc, fos);
			doc.open();
			for (int j = 1; j <= pageCount; j++) {
				doc.newPage();
				PdfImportedPage page = copy.getImportedPage(prStream.getReader(), j);
				copy.addPage(page);
			}
			doc.close();
			return "success";
		} catch (BadPdfFormatException e) {
			e.printStackTrace();
			return "0";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "1";
		} catch (IOException e) {
			e.printStackTrace();
			return "2";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "3";
		}
	}

	/**
	 * <p>
	 * 文件添加二维码工具类
	 * </p>
	 *
	 * @author hzl 2020/01/02 6:10 PM
	 */
	public static void addCode(String codeStr, PdfStamper stamper, PdfReader reader) {
		try {
			PdfContentByte waterMar;

			PdfGState gs = new PdfGState();
			gs.setFillOpacity(1f);
			long startTime = System.currentTimeMillis();
			log.info("PDF加二维码 start" + codeStr);

			// content = stamper.getOverContent(i);// 在内容上方加水印
			waterMar = stamper.getOverContent(1);// 在内容下方加水印
			waterMar.setColorFill(BaseColor.BLACK);
			// 开始水印处理
			waterMar.beginText();
			// 设置透明度
			waterMar.setGState(gs);

			// 创建水印图片
			com.itextpdf.text.Image itextimage = Image.getInstance((addQRCode(codeStr)).toByteArray());
			//A4x595.0F, y842.0F，115，72
			log.info("第一页纸张大小" + reader.getPageSize(1));
			Rectangle rectangle = reader.getPageSize(1);
			// 水印图片位置
			itextimage.setAbsolutePosition(rectangle.getWidth() - 115, rectangle.getHeight() - 72);
			// 边框固定
			itextimage.scaleToFit(60, 60);
			// 设置旋转弧度
			// image.setRotation(30);// 旋转 弧度
			// 设置旋转角度
			// image.setRotationDegrees(45);// 旋转 角度
			// 设置等比缩放
			itextimage.scalePercent(30);
			// 自定义大小
			itextimage.scaleAbsolute(60, 60);
			// 附件加上水印图片
			waterMar.addImage(itextimage);

			// 完成水印添加
			waterMar.endText();
			// stroke
			waterMar.stroke();

			long endTime = System.currentTimeMillis();
			log.info("PDF加二维码 ok 所用时间:" + (endTime - startTime) + "s");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 生成二维码图片
	 *
	 * @param codeStr 二维码内容
	 */
	private static ByteArrayOutputStream addQRCode(String codeStr) {

		try {
			Hashtable<EncodeHintType, String> hints = new Hashtable();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			int codeWeight = 100;
			int codeHeight = 100;
			BitMatrix bitMatrix = (new MultiFormatWriter()).encode(codeStr, BarcodeFormat.QR_CODE, codeWeight,
					codeHeight, hints);
			BufferedImage localBufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
			ByteArrayOutputStream ios = new ByteArrayOutputStream();
			ImageIO.write(localBufferedImage, "png", ios);
			return ios;
		} catch (Exception var10) {
			throw new RuntimeException("error.add.QR_Code");
		}

	}


	/**
	 * <p>
	 * 向pdf中插入文字
	 * </p>
	 *
	 * @author hzl 2020/01/02 6:20 PM
	 */
	public static void addContent(PdfReader reader, PdfStamper stamper, String contractNumber) throws IOException, DocumentException {

		BaseFont baseFont1 = BaseFont.createFont(FONTS_PATH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(baseFont1);


		for (int i = 1; i <= 1; i++) {
			PdfContentByte over = stamper.getOverContent(i);
			PdfGState gs = new PdfGState();
			// 设置非笔划操作不透明度为1f
			gs.setFillOpacity(1f);
			over.setGState(gs);
			over.setColorFill(BaseColor.BLACK);
			ColumnText columnText = new ColumnText(over);
			Rectangle rectangle = reader.getPageSize(1);

			log.info(rectangle.getLeft() + ":" + rectangle.getBottom() + ":" + rectangle.getRight() + ":" + rectangle.getTop());
			// llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离
			//595.0F, 842.0F
			columnText.setSimpleColumn(rectangle.getRight() + 5, rectangle.getTop() - 92, rectangle.getRight() - 195, rectangle.getTop() - 92);
			Paragraph elements = new Paragraph(0, new Chunk("合同编号:" + contractNumber));
			// 设置字体，如果不设置添加的中文将无法显示
			elements.setFont(font);
			columnText.addElement(elements);
			columnText.go();
		}
		//stamper.close();
	}

	/**
	 * <p>
	 * 获取pdf文字内容,部分特殊的pdf无法识别，例如扫描件
	 * </p>
	 *
	 * @author hzl 2020/01/10 2:41 PM
	 */
	public static String getPdfFileStr(byte[] bytes) {
		// 存放读取出的文档内容
		StringBuilder content = new StringBuilder();
		try {
			// 读取pdf所使用的输出流
			PdfReader reader = new PdfReader(bytes);
			// 获得页数
			int pageNum = reader.getNumberOfPages();
             log.info("pdf页数{}",pageNum);
			for (int i = 1; i <= pageNum; i++) {
				// 读取第i页的文档内容
				content.append(PdfTextExtractor.getTextFromPage(reader, i));
			}
		} catch (IOException e) {
			log.info("错误：" + e.getMessage());
			e.printStackTrace();
		}

		return String.valueOf(content);

	}


	/**
	 * <p>
	 * 获取pdf指定页面文字内容,部分特殊的pdf无法识别，例如扫描件
	 * </p>
	 *
	 * @author hzl 2020/01/10 2:41 PM
	 */
	public static String getPdfFileStr(byte[] bytes, int page) {
		// 存放读取出的文档内容
		String content = "";
		try {
			// 读取pdf所使用的输出流
			PdfReader reader = new PdfReader(bytes);
			// 获得页数
			int PageNum = reader.getNumberOfPages();
			content += PdfTextExtractor.getTextFromPage(reader, page);
		} catch (IOException e) {
			log.info("错误：" + e.getMessage());
			e.printStackTrace();
		}

		return content;

	}

	/**
	 * 生成电子签章
	 * 前端可以发送坐标位置到后端实现，签章插入到指定位置
	 *
	 * @param
	 * @return
	 * @author hzl 2020-03-23 9:55 AM
	 */
	public static void sign(String src  //需要签章的pdf文件路径
			, String dest  // 签完章的pdf文件路径
			, Certificate[] chain //证书链
			, PrivateKey pk //签名私钥
			, String digestAlgorithm  //摘要算法名称，例如SHA-1
			, String provider  // 密钥算法提供者，可以为null
			, MakeSignature.CryptoStandard subfilter //数字签名格式，itext有2种
			, String reason  //签名的原因，显示在pdf签名属性中，随便填
			, String location) throws Exception {
		//下边的步骤都是固定的，照着写就行了，没啥要解释的
		// Creating the reader and the stamper，开始pdfreader
		PdfReader reader = new PdfReader(src);
		//目标文件输出流
		FileOutputStream os = new FileOutputStream(dest);
		//创建签章工具PdfStamper ，最后一个boolean参数
		//false的话，pdf文件只允许被签名一次，多次签名，最后一次有效
		//true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
		// 获取数字签章属性对象，设定数字签章的属性
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setReason(reason);
		appearance.setLocation(location);
		//设置签名的位置，页码，签名域名称，多次追加签名的时候，签名预名称不能一样
		//签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
		//四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
		appearance.setVisibleSignature(new Rectangle(200, 200, 300, 300), 1, "sig1");
		//读取图章图片，这个image是itext包的image
		Image image = Image.getInstance("F:\\test\\Dummy1.png");
		appearance.setSignatureGraphic(image);
		appearance.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
		//设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
		appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

		// 这里的itext提供了2个用于签名的接口，可以自己实现，后边着重说这个实现
		// 摘要算法
		ExternalDigest digest = new BouncyCastleDigest();
		// 签名算法
		ExternalSignature signature = new PrivateKeySignature(pk, digestAlgorithm, null);
		// 调用itext签名方法完成pdf签章
		MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, subfilter);

	}

	/**
	 * 生成印章图片
	 *
	 * @param
	 * @return
	 * @author hzl 2020-03-23 10:12 AM
	 */
	public static void stampImage() {

	}
}
