/*
 * AddressMappingVO.java
 *
 * Created on May 27, 2009, 4:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author HY27465
 */
public class AddressMappingVO {
    
    /** Creates a new instance of AddressMappingVO */
    public AddressMappingVO() {        
    }

    private String type;

    private String address;

    private String subAddress;
    
    private String country;
    private String province;
    private String city;
    private String zone;
    private String subzone;
    private String newprovince;
    private String newcity;
    private String newzone;
    private String newsubzone;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubAddress() {
        return subAddress;
    }

    public void setSubAddress(String subAddress) {
        this.subAddress = subAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getNewprovince() {
        return newprovince;
    }

    public void setNewprovince(String newprovince) {
        this.newprovince = newprovince;
    }

    public String getNewcity() {
        return newcity;
    }

    public void setNewcity(String newcity) {
        this.newcity = newcity;
    }

    public String getNewzone() {
        return newzone;
    }

    public void setNewzone(String newzone) {
        this.newzone = newzone;
    }

    public String getNewsubzone() {
        return newsubzone;
    }

    public void setNewsubzone(String newsubzone) {
        this.newsubzone = newsubzone;
    }
    
    
}
