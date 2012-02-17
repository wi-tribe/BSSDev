package models.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Invoices {
	private Date dueDate = null;
	private Date startDate = null;
	private Date endDate = null;
	private String billingCycle = null;
	private String m_InvNumber = null;
	private BigDecimal m_InvAmount = null;
	private BigDecimal recAmount = null;
	private BigDecimal balance = null;
	private BigDecimal adjustments = null;
	private BigDecimal transfered = null;
	private String m_InvRef = null;
	
	NumberFormat numFormatter = new DecimalFormat("#0.00");
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public Invoices(Date dueDate, Date m_StartDate,
			Date m_EndDate, String invNumber, BigDecimal invAmount,
			 BigDecimal m_RecAmount,
			BigDecimal adjustments, BigDecimal transfered) {
		this.dueDate = dueDate;
		this.m_InvNumber = invNumber;
		this.m_InvAmount = invAmount;
		this.startDate = m_StartDate;
		this.endDate = m_EndDate;
		this.recAmount = m_RecAmount;
		this.adjustments = adjustments;
		this.transfered = transfered;
	}

	public String getDueDate() {
		return formatter.format(dueDate);
	}

	public String getInvNumber() {
		return this.m_InvNumber;
	}

	public String getInvAmount() {
		return numFormatter.format(m_InvAmount);
	}

	public String getInvRef() {
		return this.m_InvRef;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getBillingCycle() {
		billingCycle = formatter.format(startDate) + "-" + formatter.format(endDate);
		return "( " + billingCycle + " )";
	}

	public String getRecAmount() {
		return numFormatter.format(recAmount);
	}

	public String getBalance() {
		System.out.println(m_InvAmount + " " + recAmount + " " + adjustments + " " + transfered);
		return numFormatter.format(m_InvAmount.add(recAmount).add(adjustments).add(transfered));
	}

	public String getAdjustments() {
		return numFormatter.format(adjustments);
	}

	public BigDecimal getTransfered() {
		return transfered;
	}

}
