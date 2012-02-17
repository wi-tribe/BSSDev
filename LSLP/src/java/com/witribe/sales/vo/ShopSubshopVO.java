/*
 * ShopSubshopVO.java
 *
 * Created on April 6, 2009, 2:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.vo;

/**
 *
 * @author SC43278
 */
public class ShopSubshopVO {
    
    /** Creates a new instance of ShopSubshopVO */
    public ShopSubshopVO() {
    }
     private String shopId;
    private String shopname;
    private String shopType;
    private String subShopId;
    private String plot;
    private String street;
    private String subzone;
    private String zone;
    private String city;
    private String province;
    private String country;
    private String zip;
    //private String fullname;
    
     public String getShopId() {
            return shopId;
    }
    public void setShopId(String shopId) {
            this.shopId = shopId;
    }
     public String getSubShopId() {
            return subShopId;
    }
    public void setSubShopId(String subShopId) {
            this.subShopId = subShopId;
    }
     public String getShopname() {
            return shopname;
    }
    public void setShopname(String shopname) {
            this.shopname = shopname;
    }
     public String getShopType() {
            return shopType;
    }
    public void setShopType(String shopType) {
            this.shopType = shopType;
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
}
