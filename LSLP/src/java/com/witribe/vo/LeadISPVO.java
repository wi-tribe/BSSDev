/*
 * LeadISPVO.java
 *
 * Created on September 2, 2009, 5:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author SC43278
 */
public class LeadISPVO {
    
    /** Creates a new instance of LeadISPVO */
    public LeadISPVO() {
    }
    private int ISP_Id;
    private String reqISP;
    private int displayStatus;

    public int getISP_Id() {
        return ISP_Id;
    }

    public void setISP_Id(int ISP_Id) {
        this.ISP_Id = ISP_Id;
    }

    

    public int getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(int displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getReqISP() {
        return reqISP;
    }

    public void setReqISP(String reqISP) {
        this.reqISP = reqISP;
    }
}
