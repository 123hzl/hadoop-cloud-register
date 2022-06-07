package com.hzl.hadoop.app.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * description
 * 付款单实体类
 * @author hzl 2020/01/03 7:49 PM
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentVO {

	private int id;

	private String barcodeImg;

	private String barcode;

	private String userName;

	private LocalDate submitDate;

	private String deptName;

	private String reimReason;

	private List<HashMap> receiptList;

	private BigDecimal amount;

	//private BigDecimal money;


	public PaymentVO(String barcodeImg) {
		this.barcodeImg = barcodeImg;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PaymentVO)) return false;
		PaymentVO paymentVO = (PaymentVO) o;
		return getId() == paymentVO.getId() &&
				Objects.equals(getBarcodeImg(), paymentVO.getBarcodeImg()) &&
				Objects.equals(getBarcode(), paymentVO.getBarcode()) &&
				Objects.equals(getUserName(), paymentVO.getUserName()) &&
				Objects.equals(getSubmitDate(), paymentVO.getSubmitDate()) &&
				Objects.equals(getDeptName(), paymentVO.getDeptName()) &&
				Objects.equals(getReimReason(), paymentVO.getReimReason()) &&
				Objects.equals(getReceiptList(), paymentVO.getReceiptList()) &&
				Objects.equals(getAmount(), paymentVO.getAmount());
	}

	@Override
	public int hashCode() {

		return Objects.hash(getId(),getAmount());
	}
}
