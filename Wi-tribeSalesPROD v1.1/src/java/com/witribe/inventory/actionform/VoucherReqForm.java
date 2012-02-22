/*
 * VoucherReqForm.java
 *
 * Created on October 20, 2009, 11:07 AM
 */

package com.witribe.inventory.actionform;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author BS68483
 * @version
 */

public class VoucherReqForm extends org.apache.struts.action.ActionForm {
    
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
