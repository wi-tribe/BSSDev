/*
 * ShopSaleMappingForm.java
 *
 * Created on February 12, 2009, 3:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.actionform;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author SC43278
 */
public class ShopSaleMappingForm  extends ActionForm{
    
    /** Creates a new instance of ShopSaleMappingForm */
    public ShopSaleMappingForm() {
    }
     private String salespersonnel_id;

    private String shop_id;

    private String create_date;

    private String modified_date;
   private String salesId;
    private String salestype;
    private String firstname;
    private String lastname;
    private String contactnumber;
    private String email;
    private String plot;
    private String street;
    private String subzone;
    private String zone;
    private String city;
    private String province;
    private String country;
    private String zip;
    private String fullname;
    private String typecode;
    private String typevalue;
    private String userId;
    private String password;
    private String createDate;
    private String modifiedDate;
     private String assignedTL;
  
    
    public String getSalesId() {
            return salesId;
    }
    public void setSalesId(String salesId) {
            this.salesId = salesId;
    }
     public String getAssignedTL() {
        return assignedTL;
    }
   
    public void setAssignedTL(String assignedTL) {
        this.assignedTL = assignedTL;
    }
     
    public String getTypecode() {
            return typecode;
    }
    public void setTypecode(String typecode) {
            this.typecode = typecode;
    }
    public String getTypevalue() {
            return typevalue;
    }
    public void setTypevalue(String typevalue) {
            this.typevalue = typevalue;
    }
    public String getSalestype() {
            return salestype;
    }
    public void setSalestype(String salestype) {
            this.salestype = salestype;
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
    public String getContactnumber() {
            return contactnumber;
    }
    public void setContactnumber(String contactnumber) {
            this.contactnumber = contactnumber;
    }
     public String getLastname() {
            return lastname;
    }
    public void setLastname(String lastname) {
            this.lastname = lastname;
    }
    public String getStreet() {
            return street;
    }
    public void setStreet(String street) {
            this.street = street;
    }
           
   public void setFullname(String fullname) {
            this.fullname = fullname;
    }
    public String getFullname() {
        return fullname;
    }
    
    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
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
   public String getCreateDate() {
        return createDate;
    }
     public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getModifiedDate() {
        return modifiedDate;
    }
     public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }  
    
    public String getSalespersonnel_id() {
        return salespersonnel_id;
    }

    public void setSalespersonnel_id(String salespersonnel_id) {
        this.salespersonnel_id = salespersonnel_id;
    }
    

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
}
