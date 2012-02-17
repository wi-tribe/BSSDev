/*
 * LeadVO.java
 *
 * Created on January 15, 2009, 10:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;
//import com.witribe.actionform.LeadEntryForm;
/**
 *
 * @author SC43278
 */
public class HotspotVO {
    
    /** Creates a new instance of LeadVO */
    public HotspotVO() {
    }
    // private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String userId;
    private String password;
    private String country;
    private String city;
    private String province;
    private String reasonCode;
    private String transitionState;
    
    
    /*public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }*/
     public String getEmail() {
            return email;
    }
    public void setEmail(String email) {
            this.email = email;
    }
     public String getMobile() {
            return mobile;
    }
    public void setMobile(String mobile) {
            this.mobile = mobile;
    }
    public String getUserId() {
            return userId;
    }
    public void setUserId(String userId) {
            this.userId = userId;
    }
    public String getPassword() {
            return password;
    }
    public void setPassword(String password) {
            this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getTransitionState() {
        return transitionState;
    }

    public void setTransitionState(String transitionState) {
        this.transitionState = transitionState;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
       
}
