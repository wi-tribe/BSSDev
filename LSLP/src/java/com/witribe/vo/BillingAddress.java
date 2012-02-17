/*
 * BillingAddress.java
 *
 * Created on April 1, 2011, 10:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author MK64021
 */
public class BillingAddress {
    
    /** Creates a new instance of BillingAddress */
    public BillingAddress() {
    }
    private int recId;
    private String bAddress;
    private String bCity;
    private String bState;
    private String bCountry;
    private String bZipCode;

    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public String getBAddress() {
        return bAddress;
    }

    public void setBAddress(String bAddress) {
        this.bAddress = bAddress;
    }

    public String getBCity() {
        return bCity;
    }

    public void setBCity(String bCity) {
        this.bCity = bCity;
    }

    public String getBState() {
        return bState;
    }

    public void setBState(String bState) {
        this.bState = bState;
    }

    public String getBCountry() {
        return bCountry;
    }

    public void setBCountry(String bCountry) {
        this.bCountry = bCountry;
    }

    public String getBZipCode() {
        return bZipCode;
    }

    public void setBZipCode(String bZipCode) {
        this.bZipCode = bZipCode;
    }
}
