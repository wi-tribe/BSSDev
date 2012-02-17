package models.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BalanceDetails {
	List<Payments> payments = null;
	List<Invoices> invoices = null;
	BigDecimal dueAmount = null;
	BigDecimal payOrAdjust = null;
	BigDecimal dueNow = null;
	BigDecimal currentBill = null;
	BigDecimal totalAmount = null;
	BigDecimal creditLimit = null;
	BigDecimal lastPaymentAmount = null;
	Date lastPaymentDate = null;
	
	public BalanceDetails() {
		
	}

	public List<Payments> getPayments() {
		return payments;
	}

	public void setPayments(List<Payments> payments) {
		this.payments = payments;
	}

	public List<Invoices> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoices> invoices) {
		this.invoices = invoices;
	}

	public BigDecimal getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(BigDecimal dueAmount) {
		this.dueAmount = dueAmount;
	}

	public BigDecimal getPayOrAdjust() {
		return payOrAdjust;
	}

	public void setPayOrAdjust(BigDecimal payOrAdjust) {
		this.payOrAdjust = payOrAdjust;
	}

	public BigDecimal getDueNow() {
		return dueNow;
	}

	public void setDueNow(BigDecimal dueNow) {
		this.dueNow = dueNow;
	}

	public BigDecimal getCurrentBill() {
		return currentBill;
	}

	public void setCurrentBill(BigDecimal currentBill) {
		this.currentBill = currentBill;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public BigDecimal getLastPaymentAmount() {
		return lastPaymentAmount;
	}

	public void setLastPaymentAmount(BigDecimal lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}

	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}
	
	
}
