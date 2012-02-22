/*
 * RaiseRequestVO.java
 *
 * Created on February 18, 2009, 4:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.vo;

/**
 *
 * @author SC43278
 */
public class RaiseRequestVO {
    
    /** Creates a new instance of RaiseRequestVO */
    public RaiseRequestVO() {
    }
    private String requesttype;
    private String inventorytype;
    private String subtypecpe;
    private String subtyperouter;
    private String make;
    private String model;
    private String modelcpe;
    private String modelrouter;
    private String numberofdevices;
    private String shopId;
    private String reqbydate;
    private String requestId;
    private String processedstatus;
    private String requestdate;
    private String inventoryId;
    private String subtype;
    private String changeStatus;
     private String salesId;
     private String[] inventoryIdArray;
     private String poidId;
     private String poidType;
     private String toShop;
     private String macaddrwan;
     private String userdefined4;
     private String quantityprocessed;
     private String csestoshop;
     private String fullname;
    private String minlevel;
    private String maxlevel;
    private String mailSending;
    private String modelnew;
     private String makenew;
     private String subtypenew;
    

    public String getRequesttype() {
            return requesttype;
    }
    public void setRequesttype(String requesttype) {
            this.requesttype = requesttype;
    }
     public String getChangeStatus() {
            return changeStatus;
    }
    public void setChangeStatus(String changeStatus) {
            this.changeStatus = changeStatus;
    }
    public String getInventoryId() {
            return inventoryId;
    }
    public void setInventoryId(String inventoryId) {
            this.inventoryId = inventoryId;
    }
     public String getSubtype() {
            return subtype;
    }
    public void setSubtype(String subtype) {
            this.subtype = subtype;
    }
     public String getInventorytype() {
            return inventorytype;
    }
    public void setInventorytype(String inventorytype) {
            this.inventorytype = inventorytype;
    }
    public String getSubtypecpe() {
            return subtypecpe;
    }
    public void setSubtypecpe(String subtypecpe) {
            this.subtypecpe = subtypecpe;
    }
    public String getSubtyperouter() {
            return subtyperouter;
    }
    public void setSubtyperouter(String subtyperouter) {
            this.subtyperouter = subtyperouter;
    }
    public String getSalesId() {
            return salesId;
    }
    public void setSalesId(String salesId) {
            this.salesId = salesId;
    }
     public String[] getInventoryIdArray() {
            return inventoryIdArray;
    }
    public void setInventoryIdArray(String[] inventoryIdArray) {
            this.inventoryIdArray = inventoryIdArray;
    }
    public String getMake() {
            return make;
    }
    public void setMake(String make) {
            this.make = make;
    }
    public String getModel() {
            return model;
    }
    public void setModel(String model) {
            this.model = model;
    }
     public String getMinLevel() {
            return minlevel;
    }
    public void setMinLevel(String minlevel) {
            this.minlevel = minlevel;
    }
     public String getMaxLevel() {
            return maxlevel;
    }
    public void setMaxLevel(String maxlevel) {
            this.maxlevel = maxlevel;
    }
    
    public String getModelcpe() {
            return modelcpe;
    }
    
    public void setModelcpe(String modelcpe) {
            this.modelcpe = modelcpe;
    }
    public String getModelrouter() {
            return modelrouter;
    }
    
    public void setModelrouter(String modelrouter) {
            this.modelrouter = modelrouter;
    }
    public String getNumberofdevices() {
            return numberofdevices;
    }
    public void setNumberofdevices(String numberofdevices) {
            this.numberofdevices = numberofdevices;
    }
    public String getShopId() {
            return shopId;
    }
    public void setShopId(String shopId) {
            this.shopId = shopId;
    }
    public String getReqbydate() {
            return reqbydate;
    }
    public void setReqbydate(String reqbydate) {
            this.reqbydate = reqbydate;
    }
    public String getRequestId() {
            return requestId;
    }
    public void setRequestId(String requestId) {
            this.requestId = requestId;
    }
    public String getProcessedstatus() {
            return processedstatus;
    }
    public void setProcessedstatus(String processedstatus) {
            this.processedstatus = processedstatus;
    }
    public String getRequestdate() {
            return requestdate;
    }
    public void setRequestdate(String requestdate) {
            this.requestdate = requestdate;
    }
    public String getPoidId() {
            return poidId;
    }
    public void setPoidId(String poidId) {
            this.poidId = poidId;
    }
    public String getPoidType() {
            return poidType;
    }
    public void setPoidType(String poidType) {
            this.poidType = poidType;
    }
     public String getToShop() {
            return toShop;
    }
    public void setToShop(String toShop) {
            this.toShop = toShop;
    }

    public String getMacaddrwan() {
        return macaddrwan;
    }

    public void setMacaddrwan(String macaddrwan) {
        this.macaddrwan = macaddrwan;
    }

    public String getUserdefined4() {
        return userdefined4;
    }

    public void setUserdefined4(String userdefined4) {
        this.userdefined4 = userdefined4;
    }

    public String getQuantityprocessed() {
        return quantityprocessed;
    }

    public void setQuantityprocessed(String quantityprocessed) {
        this.quantityprocessed = quantityprocessed;
    }

    public String getCsestoshop() {
        return csestoshop;
    }

    public void setCsestoshop(String csestoshop) {
        this.csestoshop = csestoshop;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMailSending() {
        return mailSending;
    }

    public void setMailSending(String mailSending) {
        this.mailSending = mailSending;
    }

    public String getModelnew() {
        return modelnew;
    }

    public void setModelnew(String modelnew) {
        this.modelnew = modelnew;
    }

    public String getMakenew() {
        return makenew;
    }

    public void setMakenew(String makenew) {
        this.makenew = makenew;
    }

    public String getSubtypenew() {
        return subtypenew;
    }

    public void setSubtypenew(String subtypenew) {
        this.subtypenew = subtypenew;
    }
}
