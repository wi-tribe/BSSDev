/*
 * InvoiceRecord.java
 *
 * Created on March 30, 2011, 1:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;
import com.portal.pcm.Poid;
/**
 *
 * @author MK64021
 */
public class InvoiceRecord {
    
    /** Creates a new instance of InvoiceRecord */
    public InvoiceRecord() {
    }
    private int accountNo;
  
    Poid billPoid;
    private int intBillNo;
    Poid actPoid;
    String billNo;
    double invoiceAmount;
    double dueAmount;
    String billDate;
    String accountNumber;
    
    public Poid getBillPoid() {
        return billPoid;
    }
    
    public void setBillPoid(Poid billPoid) {
        this.billPoid = billPoid;
    }
    
    public String getBillNo() {
        return billNo;
    }
    
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    
    public double getInvoiceAmount() {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
    
    public double getDueAmount() {
        return dueAmount;
    }
    
    public void setDueAmount(double dueAmount) {
        this.dueAmount = dueAmount;
    }
    
    public String getBillDate() {
        return billDate;
    }
    
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public int getIntBillNo() {
        return intBillNo;
    }

    public void setIntBillNo(int intBillNo) {
        this.intBillNo = intBillNo;
    }
}
