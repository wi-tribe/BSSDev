/*
 * AccountDetails.java
 *
 * Created on March 30, 2011, 1:48 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author MK64021
 */
import java.util.List;

/**
 *
 * @author LB85028
 */
public class AccountDetails implements java.io.Serializable {
    
    /** Creates a new instance of AccountDetails */
    public AccountDetails() {
    }
    
    private String serviceAddress;
    private String billingAddress;
    private Address arabicAddress;
    private String mobileNo;
    private String homeNo;
    private String officeNo;
    private String faxNo;
    private String status;
    private String fullName;
    private String fullArabicName;
    private String emailAddress;
    private String emailAddress2;
    private List invoiceDetails;
    private List<Address> billAddress;
    private List<PaymentBean> payment;
    private List inventoryDetails;
    private List serviceDetails;
    private String userName;
    private List phoneNumber;
    private String creditStatus;
    private String agingStatus;
    private String wimaxLogin;
    private String emailLogin;
    private String serviceType;
    private String accountNo;
    private int aacPromoCode;
    
    public String getServiceAddress() {
        return serviceAddress;
    }
    
    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
    
    public String getBillingAddress() {
        return billingAddress;
    }
    
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    public Address getArabicAddress() {
        return arabicAddress;
    }
    
    public void setArabicAddress(Address arabicAddress) {
        this.arabicAddress = arabicAddress;
    }
    
    public String getMobileNo() {
        return mobileNo;
    }
    
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    public String getHomeNo() {
        return homeNo;
    }
    
    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }
    
    public String getOfficeNo() {
        return officeNo;
    }
    
    public void setOfficeNo(String officeNo) {
        this.officeNo = officeNo;
    }
    
    public String getFaxNo() {
        return faxNo;
    }
    
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getFullArabicName() {
        return fullArabicName;
    }
    
    public void setFullArabicName(String fullArabicName) {
        this.fullArabicName = fullArabicName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getEmailAddress2() {
        return emailAddress2;
    }
    
    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }
    
    public List<InvoiceRecord> getInvoiceDetails() {
        return invoiceDetails;
    }
    
    public void setInvoiceDetails(List<InvoiceRecord> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }
    
    public List<Address> getBillAddress() {
        return billAddress;
    }
    
    public void setBillAddress(List<Address> billAddress) {
        this.billAddress = billAddress;        
    }
    
    public String getAddress(int recId){
        String address="";        
        for(int i=0;i<billAddress.size()-1;i++){
            Address obj=(Address)billAddress.get(i);            
            if(obj.getRecId()== recId){
                address="";        
                
                
                if(!obj.getAddress().equalsIgnoreCase("")) {
                    address= address + obj.getAddress() +"\n";
                }
                if(!obj.getCity().equalsIgnoreCase("")) {
                    address= address + obj.getCity() +"\n";
                }
                if(!obj.getState().equalsIgnoreCase("")) {
                    address= address + obj.getState() +"\n";
                }
                if(!obj.getCountry().equalsIgnoreCase("")) {
                    address= address + obj.getCountry() +"\n";
                }
                if(!obj.getZipCode().equalsIgnoreCase("")) {
                    address= address + " - " +obj.getZipCode() ;
                }
                
                
                
                System.out.println("address ............"+address);
                break;
            }
        }
        return address;
    }
    
    public List<PaymentBean> getPayment() {
        return payment;
    }
    
    public void setPayment(List<PaymentBean> payment) {
        this.payment = payment;
    }

    public List<InventoryAccountBean> getInventoryDetails() {
        return inventoryDetails;
    }

    public void setInventoryDetails(List<InventoryAccountBean> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPhoneNumber(int type){
        String phoneNo="";
        String pType=Integer.toString(type);
        for(int i=0;i<phoneNumber.size();i++){
            phoneBean obj=(phoneBean)phoneNumber.get(i);
            if(obj.getPhoneType().equals(pType)){
                phoneNo=obj.getPhoneNo();
                break;
            }
        }
        return phoneNo;
    }

    public List getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(List phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getAgingStatus() {
        return agingStatus;
    }

    public void setAgingStatus(String agingStatus) {
        this.agingStatus = agingStatus;
    }

    public String getWimaxLogin() {
        return wimaxLogin;
    }

    public void setWimaxLogin(String wimaxLogin) {
        this.wimaxLogin = wimaxLogin;
    }

    public String getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(List serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getAacPromoCode() {
        return aacPromoCode;
    }

    public void setAacPromoCode(int aacPromoCode) {
        this.aacPromoCode = aacPromoCode;
    }
}

