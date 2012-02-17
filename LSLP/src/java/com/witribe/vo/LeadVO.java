/*
 * LeadVO.java
 *
 * Created on January 8, 2009, 10:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

import com.portal.pcm.Poid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
//import com.witribe.actionform.LeadEntryForm;
/**
 *
 * @author SC43278
 */
public class LeadVO {
    
    /** Creates a new instance of LeadVO */
    public LeadVO() {
    }
    private String leadId;
    private String channeltype;
    private String salutation;
    private String firstname;
    private String lastname;
    private String CNICid;
    private String jobtitle;
    private String occupation;
    private String email;
    private String contactnumber;
    private String plot;
    private String street;
    private String subzone;
    private String zone;
    private String otherzone;
    private String othersubzone;
    private String city;
    private String province;
    private String country;
    private String zip;
    private String leadsource;
    private String packageinfo;
    private String nameofISP;
    //Added by Swapna on sep 10th
    private String reqISP;
    private String servicetype;
    private String willingPay;
    private String broadbandtype;
    private String currentspeed;
    private String volumelimit;
    private String monthlyspend;
    private String query;
    private String priority;
    private String address;
    private String createDate;
    private String leadaddress;
    private String accountNo;
    private String reason;
    private String assignedTL;
    private String assignedCSE;
    private String salesId;
    private String salesEmail;
    private String acctType;
    private String acctDate;
    private String leadStatus;
    private String orderId;
    public String amount;
    public String salesType;
    public String assignTo;
    public String salesContact;
    private String searchby;
    private String fullname;
    private String AccountDate;
    private String fromDate;
    private String toDate; 
    private String csename;
    private String tlname;
    private int reasonId;
    private String transitionState;
    private String reasonCode;
    private String reasonComment;
    private String leadStatusNew;
   private String followUpDate;
   private String userId;
   
   private String referredBy;
    
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getLeadStatus() {
        return leadStatus;
    }
    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }
    public String getAcctDate() {
        return acctDate;
    }
    public void setAcctDate(String acctDate) {
        this.acctDate = acctDate;
    }
    public String getAcctType() {
        return acctType;
    }
    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }
     public String getLeadId() {
        return leadId;
    }
    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }
     public String getSalesEmail() {
        return salesEmail;
    }
    public void setSalesEmail(String salesEmail) {
        this.salesEmail = salesEmail;
    }
    public String getSalesId() {
        return salesId;
    }
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }
   
    public String getBroadbandtype() {
        return broadbandtype;
    }
    public void setBroadbandtype(String broadbandtype) {
        this.broadbandtype = broadbandtype;
    }
    public String getChanneltype() {
        return channeltype;
    }
    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }
    
    public String getCNICid() {
        return CNICid;
    }
    public void setCNICid(String cid) {
        CNICid = cid;
    }
    public String getContactnumber() {
        return contactnumber;
    }
    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }
    public String getWillingPay() {
        return willingPay;
    }
    public void setWillingPay(String willingPay) {
        this.willingPay = willingPay;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
     
    public String getJobtitle() {
        return jobtitle;
    }
    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getLeadsource() {
        return leadsource;
    }
    public void setLeadsource(String leadsource) {
        this.leadsource = leadsource;
    }
    public String getMonthlyspend() {
        return monthlyspend;
    }
    public void setMonthlyspend(String monthlyspend) {
        this.monthlyspend = monthlyspend;
    }
    public String getNameofISP() {
        return nameofISP;
    }
    public void setNameofISP(String nameofISP) {
        this.nameofISP = nameofISP;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getPackageinfo() {
        return packageinfo;
    }
    public void setPackageinfo(String packageinfo) {
        this.packageinfo = packageinfo;
    }
    
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getQuery() {
        return query;
    }
    public void setQuery(String query) {
        this.query = query;
    }
   
    public String getSalutation() {
        return salutation;
    }
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }
    public String getServicetype() {
        return servicetype;
    }
    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }
    public String getCurrentSpeed() {
        return currentspeed;
    }
    public void setCurrentSpeed(String currentspeed) {
        this.currentspeed = currentspeed;
    }
       
    public String getVolumelimit() {
        return volumelimit;
    }
    public void setVolumelimit(String volumelimit) {
        this.volumelimit = volumelimit;
    }
     public String getCreateDate() {
        return createDate;
    }
     public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public void setAddress() {
       StringBuffer address = new StringBuffer(getPlot());
       address.append(getStreet());
     
       this.address = address.toString();
    }
    public String getAddress() {
        return address;
    }
    
      public String getLeadaddress() {
        return leadaddress;
    }
     public void setLeadaddress(String leadaddress) {
        this.leadaddress = leadaddress;
    }
     public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    
     public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
   
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getSubzone() {
        return subzone;
    }
    public void setSubzone(String subzone) {
        this.subzone = subzone;
    }
    public String getZone() {
        return zone;
    }
    public void setZone(String zone) {
        this.zone = zone;
    }    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
         return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
         return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getZip() {
         return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public String getAssignedTL() {
         return assignedTL;
    }
    public void setAssignedTL(String assignedTL) {
        this.assignedTL = assignedTL;
    }
    public String getAssignedCSE() {
         return assignedCSE;
    }
    public void setAssignedCSE(String assignedCSE) {
        this.assignedCSE = assignedCSE;
    }
     public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getSalesContact() {
        return salesContact;
    }

    public void setSalesContact(String salesContact) {
        this.salesContact = salesContact;
    }

    public String getOtherzone() {
        return otherzone;
    }

    public void setOtherzone(String otherzone) {
        this.otherzone = otherzone;
    }

    public String getOthersubzone() {
        return othersubzone;
    }

    public void setOthersubzone(String othersubzone) {
        this.othersubzone = othersubzone;
    }

    public String getSearchby() {
        return searchby;
    }

    public void setSearchby(String searchby) {
        this.searchby = searchby;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAccountDate() {
        return AccountDate;
    }

    public void setAccountDate(String AccountDate) {
        this.AccountDate = AccountDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getCsename() {
        return csename;
    }

    public void setCsename(String csename) {
        this.csename = csename;
    }

    public String getTlname() {
        return tlname;
    }

    public void setTlname(String tlname) {
        this.tlname = tlname;
    }
    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getTransitionState() {
        return transitionState;
    }

    public void setTransitionState(String transitionState) {
        this.transitionState = transitionState;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }


    public String getReqISP() {
        return reqISP;
    }

    public void setReqISP(String reqISP) {
        this.reqISP = reqISP;
    }

public String getLeadStatusNew() {
        return leadStatusNew;
    }

    public void setLeadStatusNew(String leadStatusNew) {
        this.leadStatusNew = leadStatusNew;
    }

    public String getReasonComment() {
        return reasonComment;
    }

    public void setReasonComment(String reasonComment) {
        this.reasonComment = reasonComment;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

   
}
