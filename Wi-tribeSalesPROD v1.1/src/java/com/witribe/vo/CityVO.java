/*
 * CityVO.java
 *
 * Created on February 4, 2009, 8:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author HY27465
 */
public class CityVO {
    
    /** Creates a new instance of CityVO */
    public CityVO() {
    }

    private int addr_city_code;

    private String addr_city;

    private String addr_province;

    public int getAddr_city_code() {
        return addr_city_code;
    }

    public void setAddr_city_code(int addr_city_code) {
        this.addr_city_code = addr_city_code;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public void setAddr_city(String addr_city) {
        this.addr_city = addr_city;
    }

    public String getAddr_province() {
        return addr_province;
    }

    public void setAddr_province(String addr_province) {
        this.addr_province = addr_province;
    }
    
}
