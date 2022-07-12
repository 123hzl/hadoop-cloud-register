package com.hzl.hadoop.util.email.service.impl;

import com.hzl.hadoop.app.service.FreemarkerService;
import com.hzl.hadoop.app.vo.PaymentVO;
import com.hzl.hadoop.util.Zxing;
import com.hzl.hadoop.util.email.service.MailService;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author hzl 2020/01/05 2:09 PM
 */
@Slf4j
@Component
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	FreemarkerService freemarkerService;

	/**
	 * <p>
	 * 发送简单邮件
	 * </p>
	 *
	 * @author hzl 2020/01/05 2:34 PM
	 */
	@Override
	public Boolean sendSimpleMail(String subject, String text) {
		String[] to=new String[]{"1620516418@qq.com","853804270@qq.com"};

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		//发件人邮箱
		mailMessage.setFrom("hzlvip1994@163.com");
		//收件人邮箱
		mailMessage.setTo(to);
//		//抄送人
//		mailMessage.setCc();
//		//密送人
//		mailMessage.setBcc();

		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		javaMailSender.send(mailMessage);
		return true;
	}

	/**
	 * <p>
	 * 发送html邮件,附件
	 * </p>
	 * subject 主题
	 * text html内容
	 * Map<String, String> attachmentMap key为附件名称，value为附件地址
	 *
	 * @author hzl 2020/01/05 2:34 PM
	 */
	@Override
	public Boolean sendHtmlMail(String subject, String text, Map<String, String> attachmentMap) throws MessagingException {
		String[] to=new String[]{"1620516418@qq.com","853804270@qq.com"};
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		//是否发送的邮件是富文本（附件，图片，html等）
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
		//发件人邮箱
		messageHelper.setFrom("hzlvip1994@163.com");
		//收件人邮箱
		messageHelper.setTo(to);

		//网易邮箱需要抄送自己
		//messageHelper.setCc("hzlvip1994@163.com");
		messageHelper.setSubject(subject);
		messageHelper.setText(text, true);//重点，默认为false，显示原始html代码，无效果
		log.info("开始发送邮件---------------------------");
		if (attachmentMap != null) {
			attachmentMap.entrySet().stream().forEach(entrySet -> {
				try {
					File file = new File(entrySet.getValue());
					if (file.exists()) {
						messageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			});
		}
		javaMailSender.send(mimeMessage);

		return true;
	}

	/**
	 * <p>
	 * 读取freemarker模版后发送邮件，附件
	 * </p>
	 *
	 * @author hzl 2020/01/05 2:34 PM
	 */
	@Override
	public Boolean sendTemplateMail(String subject, Map<String, String> params) {


		try {
			sendHtmlMail(subject, getFreemarkerMailText(), params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	private String getFreemarkerMailText() throws IOException, TemplateException {
		String htmlText = "";

		//FreeMarker通过Map传递动态数据
		byte[] bytes = Zxing.executeForEANBytesReturn();
		String barcodeImg = Base64.encodeBase64String(bytes).toString();
		log.info("条形码字节大小" + bytes.length);
		//费用明细
		HashMap map = new HashMap(8);
		map.put("receiptType", "增值税发票");
		map.put("amount", "100¥");
		List<HashMap> receiptList = new ArrayList<>();
		receiptList.add(map);
		PaymentVO paymentVO = PaymentVO.builder()
				.barcodeImg("data:image/png;base64," + barcodeImg)
				.barcode("RS20198899").deptName("运营事业部")
				.reimReason("餐费")
				.userName("李白")
				.submitDate(LocalDate.now())
				.receiptList(receiptList)
				.build();
		Map value = new HashMap(4);
		value.put("paymentVO", paymentVO);
		htmlText = freemarkerService.getFreemarkerHtml("payZxing.html", value);

		return htmlText;
	}
}
