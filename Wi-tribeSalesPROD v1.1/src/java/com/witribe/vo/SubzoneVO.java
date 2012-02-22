/*
 * SubzoneVO.java
 *
 * Created on February 4, 2009, 8:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author HY27465
 */
public class SubzoneVO {
    
    /** Creates a new instance of SubzoneVO */
    public SubzoneVO() {
    }

    private int addr_subzone_code;

    private String addr_subzone;

    private int addr_zone_code;

    public int getAddr_subzone_code() {
        return addr_subzone_code;
    }

    public void setAddr_subzone_code(int addr_subzone_code) {
        this.addr_subzone_code = addr_subzone_code;
    }

    public String getAddr_subzone() {
        return addr_subzone;
    }

    public void setAddr_subzone(String addr_subzone) {
        this.addr_subzone = addr_subzone;
    }

    public int getAddr_zone_code() {
        return addr_zone_code;
    }

    public void setAddr_zone_code(int addr_zone_code) {
        this.addr_zone_code = addr_zone_code;
    }
    
}
