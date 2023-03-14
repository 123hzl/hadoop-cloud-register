package com.hzl.hadoop.util;

import com.hzl.hadoop.app.vo.PaymentVO;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * description
 * 单纯用来进行测试
 *
 * @author hzl 2021/10/20 10:15 AM
 */
public class TestUtils {

	public static final Integer INTEGER_ONE = 1;

	public static void main(String args[]) {
		Map<PaymentVO, BigDecimal> map = new LinkedHashMap<>();
		PaymentVO paymentVO1 = new PaymentVO();
		paymentVO1.setAmount(new BigDecimal("1"));
		paymentVO1.setId(1);

		PaymentVO paymentVO2 = new PaymentVO();
		paymentVO2.setAmount(new BigDecimal(1));
		paymentVO2.setId(2);

		PaymentVO paymentVO3 = new PaymentVO();
		paymentVO3.setAmount(new BigDecimal(4));
		paymentVO3.setId(3);

		map.put(paymentVO1, BigDecimal.valueOf(1));
		map.put(paymentVO2, BigDecimal.valueOf(2));
		map.put(paymentVO3, BigDecimal.valueOf(3));

		map=map.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getAmount()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		System.out.println(map.toString());


//		String s="11dede-3434dfff-2fff";
//		System.out.println("字符串替换1"+s.replace('-','%'));
//		System.out.println("字符串替换2"+s);
//
//		System.out.println("字符串替换3"+s.replaceAll("-","%"));
//
//		System.out.println("sql拼接"+String.format("%s = {0}", "name"));
//		System.out.println("sql拼接"+String.format("{%s}", "name"));
		Predicate<Integer> predOdd = integer -> integer % 2 == 1;
		System.out.println(predOdd.test(5));

		//System.out.println(APPLICATION_JSON_UTF8.getSubtype());
	}

}
