package com.hzl.hadoop.app.controller;

import com.hzl.hadoop.app.vo.PaymentVO;
import com.hzl.hadoop.interfacemanager.annotation.Permission;
import com.hzl.hadoop.util.Zxing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * description
 * 结合freeMarker和条形码生成组件，生成一个付款单
 *
 * @author hzl 2020/01/03 7:37 PM
 * @Controller才会映射到视图上，不然直接返回json给调用方
 */
@Controller
@Slf4j
public class FreeMarkerZxingController {

	/**
	 * <p>
	 *    参考：https://www.cnblogs.com/xcsn/p/4677373.html
	 *data:,文本数据
	 data:text/plain,文本数据
	 data:text/html,HTML代码
	 data:text/html;base64,base64编码的HTML代码
	 data:text/css,CSS代码
	 data:text/css;base64,base64编码的CSS代码
	 data:text/javascript,Javascript代码
	 data:text/javascript;base64,base64编码的Javascript代码
	 data:image/gif;base64,base64编码的gif图片数据
	 data:image/png;base64,base64编码的png图片数据
	 data:image/jpeg;base64,base64编码的jpeg图片数据
	 data:image/x-icon;base64,base64编码的icon图片数据
	 * </p>
	 *
	 * @author hzl 2020/01/03 10:35 PM
	 */
	@GetMapping(value = "/paymentOrder")
	public String paymentOrder(Model model) {

		try {
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
			model.addAttribute(paymentVO);
			log.info("model" + model.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "payZxing";
	}
}
