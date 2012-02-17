/*
 * ViewShopSaleVO.java
 *
 * Created on February 12, 2009, 5:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.vo;

import com.witribe.bo.WitribeBO;

/**
 *
 * @author SC43278
 */
public class ViewShopSaleVO {
    
    /** Creates a new instance of ViewShopSaleVO */
    public ViewShopSaleVO() {
    }
    private String shopname;

    private String salesid;

    private String shopid;

    private String zone;

    private String subzone;

    private String city;

    private String province;

    private String country;
    
    private String salestype;
    
    private String idandtype;
    
    private String fullname;
     //added By Bhawna on 13th october, 2009
    private String shopzone;

    public String getShopzone() {
        return shopzone;
    }

    public void setShopzone(String shopzone) {
        this.shopzone = shopzone;
    }
    //end 13th oct,2009

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getSalesid() {
        return salesid;
    }

    public void setSalesid(String salesid) {
        this.salesid = salesid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSubzone() {
        return subzone;
    }
    public void setSubzone(String subzone) {
        this.subzone = subzone;
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

    public String getSalestype() {
        return salestype;
    }

    public void setSalestype(String salestype) {
        this.salestype = salestype;
    }

    public String getIdandtype() {
        WitribeBO wb = new WitribeBO();
        return salesid+"/"+wb.SalesType(salestype);
    }

    public void setIdandtype(String idandtype) {
        this.idandtype = idandtype;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
