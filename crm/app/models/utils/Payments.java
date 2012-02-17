package models.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payments {
	private String paymentDate = null;
	private String paymentMethod = null;
	private BigDecimal amountPayed = null;
	private BigDecimal amountAllocated = null;
	private BigDecimal amountUnAllocated = null;
	private String paymentNo = null;
	NumberFormat numFormatter = new DecimalFormat("#0.00");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public Payments(String paymentDate, String paymentMethod, BigDecimal amountPayed, BigDecimal amountAllocated,BigDecimal amountUnAllocated, String paymentNo) {
		this.paymentDate = paymentDate;
		this.amountPayed = amountPayed;
		this.amountAllocated = amountAllocated;
		this.paymentMethod = paymentMethod;
		this.amountUnAllocated = amountUnAllocated;
		this.paymentNo = paymentNo;
	}
	
	public String getPaymentDate() {
		return paymentDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getAmountPayed() {
		return numFormatter.format(amountPayed);
	}

	public String getAmountAllocated() {
		return numFormatter.format(amountAllocated);
	}

	public String getAmountUnAllocated() {
		return numFormatter.format(amountUnAllocated);
	}

	public String getPaymentNo() {
		return paymentNo;
	}
}
