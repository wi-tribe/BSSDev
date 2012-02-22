/*
 * LeadAccntVO.java
 *
 * Created on October 1, 2009, 5:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author BS68483
 */
public class LeadAccntVO {
    
    /** Creates a new instance of LeadAccntVO */
    public LeadAccntVO() {
    }
     private static final SimpleDateFormat monthDayYearformatter = new SimpleDateFormat("dd/MM/yyyy"); 
   //private Poid macctpoid;
   private BigDecimal currentBal;
   private BigDecimal billAmount;
   private BigDecimal unBillAmount;
   private BigDecimal lastPaidAmt;
   private Date lastPaidDate;
   private Date lastBillDate;
   private BigDecimal overDueAmount;
   private BigDecimal volumeConsumedInPercent;
   private BigDecimal totalBalance;

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }
    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }
    public void setVolumeConsumedInPercent(BigDecimal volumeConsumedInPercent) {
        this.volumeConsumedInPercent = volumeConsumedInPercent;
    }

    public BigDecimal getVolumeConsumedInPercent() {
        return volumeConsumedInPercent;
    }


    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public BigDecimal getCurrentBal() {
        return currentBal;
    }

    public void setCurrentBal(BigDecimal currentBal) {
        this.currentBal = currentBal;
    }

    public Date getLastBillDate() {
       return lastBillDate;
    }

    public void setLastBillDate(Date lastBillDate) {
        this.lastBillDate = lastBillDate;
    }

    public BigDecimal getLastPaidAmt() {
        return lastPaidAmt;
    }

    public void setLastPaidAmt(BigDecimal LastPaidAmt) {
        this.lastPaidAmt = lastPaidAmt;
    }

    public Date getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(Date lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    public BigDecimal getOverDueAmount() {
        return overDueAmount;
    }

    public void setOverDueAmount(BigDecimal overDueAmount) {
        this.overDueAmount = overDueAmount;
    }

    public BigDecimal getUnBillAmount() {
        return unBillAmount;
    }

    public void setUnBillAmount(BigDecimal UnBillAmount) {
        this.unBillAmount = unBillAmount;
    }

    
 }
