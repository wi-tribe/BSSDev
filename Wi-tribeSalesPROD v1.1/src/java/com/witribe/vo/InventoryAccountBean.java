/*
 * InventoryAccountBean.java
 *
 * Created on March 30, 2011, 1:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author MK64021
 */
public class InventoryAccountBean {
    
   private String deviceId;
    private String isSell;
    private String deviceMac;
    private String mainCpe;
    private String ownCpe;
    private String status;
    private String serialItemName;
    
    /** Creates a new instance of InventoryAccountBean */
    public InventoryAccountBean() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIsSell() {
        return isSell;
    }

    public void setIsSell(String isSell) {
        this.isSell = isSell;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getMainCpe() {
        return mainCpe;
    }

    public void setMainCpe(String mainCpe) {
        this.mainCpe = mainCpe;
    }

    public String getOwnCpe() {
        return ownCpe;
    }

    public void setOwnCpe(String ownCpe) {
        this.ownCpe = ownCpe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerialItemName() {
        return serialItemName;
    }

    public void setSerialItemName(String serialItemName) {
        this.serialItemName = serialItemName;
    }
    
}

