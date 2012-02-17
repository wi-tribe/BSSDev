/*
 * ProvinceVO.java
 *
 * Created on February 4, 2009, 3:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author HY27465
 */
public class ProvinceVO {
    
    /** Creates a new instance of ProvinceVO */
    public ProvinceVO()  throws Exception{
    }

    private String addr_province;

    private String addr_country;
    
  

    public String getAddr_province() {
        return addr_province;
    }

    public void setAddr_province(String addr_province) {
        this.addr_province = addr_province;
    }

    public String getAddr_country() {
        return addr_country;
    }

    public void setAddr_country(String addr_country) {
        this.addr_country = addr_country;
    }
  
}
