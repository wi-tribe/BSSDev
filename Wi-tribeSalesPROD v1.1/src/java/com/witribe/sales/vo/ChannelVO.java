/*
 * ChannelVO.java
 *
 * Created on May 19, 2009, 4:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.vo;

/**
 *
 * @author SC43278
 */
public class ChannelVO {
    
    /** Creates a new instance of ChannelVO */
    public ChannelVO() {
    }
     private String salesId;
     private String salesIdChannel;
    private String salestype;
    private String channelId;
    private String channelName;
    private String channeltype;
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
    private String userId;
    private String password;
    private String createDate;
    private String modifiedDate;
    private String shop_id;
    
    public String getSalestype() {
            return salestype;
    }
    public void setSalestype(String salestype) {
            this.salestype = salestype;
    }
   public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
    public String getSalesId() {
            return salesId;
    }
    public void setSalesId(String salesId) {
            this.salesId = salesId;
    }
    
    public String getChanneltype() {
             return channeltype;
    }
    public void setChanneltype(String channeltype) {
            this.channeltype = channeltype;
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
   
   public void setFullname() {
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
    public String getSalesIdChannel() {
            return salesIdChannel;
    }
    public void setSalesIdChannel(String salesIdChannel) {
            this.salesIdChannel = salesIdChannel;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
