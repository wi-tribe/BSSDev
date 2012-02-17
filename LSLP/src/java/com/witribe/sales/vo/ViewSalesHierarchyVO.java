/*
 * ViewSalesHierarchyVO.java
 *
 * Created on February 7, 2009, 2:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.vo;

import com.witribe.bo.WitribeBO;

/**
 *
 * @author HY27465
 */
public class ViewSalesHierarchyVO {
    
    /** Creates a new instance of ViewSalesHierarchyVO */
    public ViewSalesHierarchyVO() {
    }

    private String parentname;

    private String childname;

    private String parentsalesid;

    private String childsalesid;

    private String zone;

    private String subzone;

    private String city;

    private String province;

    private String country;
    
    private String salestype;
    
    private String idandtype;
    
    private String shopid;

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public String getParentsalesid() {
        return parentsalesid;
    }

    public void setParentsalesid(String parentsalesid) {
        this.parentsalesid = parentsalesid;
    }

    public String getChildsalesid() {
        return childsalesid;
    }

    public void setChildsalesid(String childsalesid) {
        this.childsalesid = childsalesid;
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
        return childsalesid+"/"+wb.SalesType(salestype);
    }

    public void setIdandtype(String idandtype) {
        this.idandtype = idandtype;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
    

    
    
}
