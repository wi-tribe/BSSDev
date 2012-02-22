/*
 * DistributeInventoryVO.java
 *
 * Created on February 24, 2009, 12:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.vo;

/**
 *
 * @author SC43278
 */
public class DistributeInventoryVO {
    
    /** Creates a new instance of DistributeInventoryVO */
    public DistributeInventoryVO() {
    }
    private String shopId;
    private String salesId;
    private String[] inventoryIdArray;
    private String inventoryId;
    private String assignedDate;
    private String assignedTo;
    private String inventorytype;
    private String subtypecpe;
    private String subtyperouter; 
     private String make;
    private String model;
    private String subtype;
    private String poidId;
    private String fullname;
    String[] poidArray;
    private String changeStatus;
    private String oldStatus;
    private String macaddrwan;
     private String searchbystatus;
      private String searchby;
       private String searchinv;
     private String itemcode;
      private String shopname;
   
    public String getMake() {
            return make;
    }
    public void setMake(String make) {
            this.make = make;
    }
    public String getPoidId() {
            return poidId;
    }
    public void setPoidId(String poidId) {
            this.poidId = poidId;
    }
    public String getChangeStatus() {
            return changeStatus;
    }
    public void setChangeStatus(String changeStatus) {
            this.changeStatus = changeStatus;
    }
     public String getSubtype() {
            return subtype;
    }
    public void setSubtype(String subtype) {
            this.subtype = subtype;
    }
    public String getModel() {
            return model;
    }
    public void setModel(String model) {
            this.model = model;
    }
    public String getShopId() {
            return shopId;
    }
    public void setShopId(String shopId) {
            this.shopId = shopId;
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
    public String[] getPoidArray() {
            return poidArray;
    }
    public void setPoidArray(String[] poidArray) {
            this.poidArray = poidArray;
    }
    public String getInventoryId() {
            return inventoryId;
    }
    public void setInventoryId(String inventoryId) {
            this.inventoryId = inventoryId;
    }
    public String getAssignedDate() {
            return assignedDate;
    }
    public void setAssignedDate(String assignedDate) {
            this.assignedDate = assignedDate;
    }
    public String getAssignedTo() {
            return assignedTo;
    }
    public void setAssignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMacaddrwan() {
        return macaddrwan;
    }

    public void setMacaddrwan(String macaddrwan) {
        this.macaddrwan = macaddrwan;
    }

    public String getSearchbystatus() {
        return searchbystatus;
    }

    public void setSearchbystatus(String searchbystatus) {
        this.searchbystatus = searchbystatus;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getSearchby() {
        return searchby;
    }

    public void setSearchby(String searchby) {
        this.searchby = searchby;
    }

    public String getSearchinv() {
        return searchinv;
    }

    public void setSearchinv(String searchinv) {
        this.searchinv = searchinv;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }
    
}
