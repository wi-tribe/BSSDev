/*
 * PaymentBean.java
 *
 * Created on March 30, 2011, 1:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author MK64021
 */
public class PaymentBean  implements java.io.Serializable {
    
    /**
     * Creates a new instance of PaymentBean
     */
    public PaymentBean() {
    }
    private String paymentId;
    private String paidAmount;
    private String paymentType;
    private String paymentDate;
    
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}

