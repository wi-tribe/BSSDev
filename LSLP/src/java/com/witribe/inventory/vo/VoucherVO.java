/*
 * VoucherVO.java
 *
 * Created on October 20, 2009, 3:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.vo;

/**
 *
 * @author BS68483
 */
public class VoucherVO {
    
    /** Creates a new instance of VoucherVO */
    public VoucherVO() {
    }
    private String voucherInfo;
    private String voucherType;
    private String resType;
    private String quantity;
    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
   public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public String getResType() {
        return resType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getVoucherType() {
        return voucherType;
    }
    public String getVoucherInfo() {
        return voucherInfo;
    }

    public void setVoucherInfo(String voucherInfo) {
        this.voucherInfo = voucherInfo;
    }

    
    
}
