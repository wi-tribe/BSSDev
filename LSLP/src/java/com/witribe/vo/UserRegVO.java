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
public class UserRegVO {
    
    /** Creates a new instance of LeadVO */
    public UserRegVO() {
    }
    private String name;
    private String email;
    private String mobile;
    private String area;
    private String role;
    private String userId;
    private String password;
    private String createdate;
    
    
    public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }
    public String getCreateDate() {
            return createdate;
    }
    public void setCreateDate(String createdate) {
            this.createdate = createdate;
    }
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
     public String getArea() {
            return area;
    }
    public void setArea(String area) {
            this.area = area;
    }
     public String getRole() {
            return role;
    }
    public void setRole(String role) {
            this.role = role;
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
       
}
