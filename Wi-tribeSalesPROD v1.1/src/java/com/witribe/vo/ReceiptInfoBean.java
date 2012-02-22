/*
 * ReceiptInfoBean.java
 *
 * Created on April 12, 2011, 1:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;
import java.util.*;
import java.math.BigDecimal;


/**
 *
 * @author mk64021
 */
public class ReceiptInfoBean {
    
        private String accountno = null;
        private BigDecimal amount = null;
        private String city = null;
        private String descr = null;
        private String name = null;
        private String itemNo = null;
        private String title = null;
        private String transId = null;
        private String payDate = null;
        
    /** Creates a new instance of ReceiptInfoBean */
    public ReceiptInfoBean() {
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

   
}
