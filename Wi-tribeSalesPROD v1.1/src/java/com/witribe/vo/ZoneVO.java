/*
 * ZoneVO.java
 *
 * Created on February 4, 2009, 8:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author HY27465
 */
public class ZoneVO {
    
    /** Creates a new instance of ZoneVO */
    public ZoneVO() {
    }

    private int addr_zone_code;

    private String addr_zone;

    private int addr_city_code;

    public int getAddr_zone_code() {
        return addr_zone_code;
    }

    public void setAddr_zone_code(int addr_zone_code) {
        this.addr_zone_code = addr_zone_code;
    }

    public String getAddr_zone() {
        return addr_zone;
    }

    public void setAddr_zone(String addr_zone) {
        this.addr_zone = addr_zone;
    }

    public int getAddr_city_code() {
        return addr_city_code;
    }

    public void setAddr_city_code(int addr_city_code) {
        this.addr_city_code = addr_city_code;
    }
    
}
