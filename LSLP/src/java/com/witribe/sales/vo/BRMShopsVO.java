/*
 * BRMShopsVO.java
 *
 * Created on May 18, 2009, 4:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.vo;

/**
 *
 * @author HY27465
 */
public class BRMShopsVO {
    
    /** Creates a new instance of BRMShopsVO */
    public BRMShopsVO() {
    }

    private String subInventoryCode;

    private String subInventoryDesc;

    public String getSubInventoryCode() {
        return subInventoryCode;
    }

    public void setSubInventoryCode(String subInventoryCode) {
        this.subInventoryCode = subInventoryCode;
    }

    public String getSubInventoryDesc() {
        return subInventoryDesc;
    }

    public void setSubInventoryDesc(String subInventoryDesc) {
        this.subInventoryDesc = subInventoryDesc;
    }
    
}
