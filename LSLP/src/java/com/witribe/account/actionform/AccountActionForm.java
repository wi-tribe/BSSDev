/*
 * AccountActionForm.java
 *
 * Created on September 8, 2009, 3:09 PM
 */

package com.witribe.account.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author DP54063
 * @version
 */

public class AccountActionForm extends org.apache.struts.action.ActionForm {
    
 
private String salesId;
private String leadId;
private String salute;
private String firstname;
private String lastname;
private String identity;
private String title;
private String email;
private String company;
private String cnotype1;
private String cnotype2;
private String cnotype3;
private String cnotype5;
private String city;
private String state;
private String zip;
private String sHNo;
private String sStreet;
private String sRoad;
private String country;
private String sSector;
private String sCoverage;
private String sla;
private String sLongitude;
private String sLatitude;

private String referrer;
private String brand;
private String Deliver_method;
private String currency;
private String aacaccess;

private String val;
private String ipAddress;
private String ParentPoid;
private String PayType;
private String locale;
private String bill_zip;
private String bill_state;
private String bill_country;

    
    
    public AccountActionForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getSalute() {
        return salute;
    }

    public void setSalute(String salute) {
        this.salute = salute;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCnotype1() {
        return cnotype1;
    }

    public void setCnotype1(String cnotype1) {
        this.cnotype1 = cnotype1;
    }

    public String getCnotype2() {
        return cnotype2;
    }

    public void setCnotype2(String cnotype2) {
        this.cnotype2 = cnotype2;
    }

    public String getCnotype3() {
        return cnotype3;
    }

    public void setCnotype3(String cnotype3) {
        this.cnotype3 = cnotype3;
    }

    public String getCnotype5() {
        return cnotype5;
    }

    public void setCnotype5(String cnotype5) {
        this.cnotype5 = cnotype5;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSHNo() {
        return sHNo;
    }

    public void setSHNo(String sHNo) {
        this.sHNo = sHNo;
    }

    public String getSStreet() {
        return sStreet;
    }

    public void setSStreet(String sStreet) {
        this.sStreet = sStreet;
    }

    public String getSRoad() {
        return sRoad;
    }

    public void setSRoad(String sRoad) {
        this.sRoad = sRoad;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSSector() {
        return sSector;
    }

    public void setSSector(String sSector) {
        this.sSector = sSector;
    }

    public String getSCoverage() {
        return sCoverage;
    }

    public void setSCoverage(String sCoverage) {
        this.sCoverage = sCoverage;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public String getSLongitude() {
        return sLongitude;
    }

    public void setSLongitude(String sLongitude) {
        this.sLongitude = sLongitude;
    }

    public String getSLatitude() {
        return sLatitude;
    }

    public void setSLatitude(String sLatitude) {
        this.sLatitude = sLatitude;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeliver_method() {
        return Deliver_method;
    }

    public void setDeliver_method(String Deliver_method) {
        this.Deliver_method = Deliver_method;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAacaccess() {
        return aacaccess;
    }

    public void setAacaccess(String aacaccess) {
        this.aacaccess = aacaccess;
    }
    
    
}
