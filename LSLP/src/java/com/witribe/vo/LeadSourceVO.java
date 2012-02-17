/*
 * LeadSourceVO.java
 *
 * Created on September 2, 2009, 4:27 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author SC43278
 */
public class LeadSourceVO {
    
    /** Creates a new instance of LeadSourceVO */
    public LeadSourceVO() {
    }
    private int leadSourceId;

    private String leadSourceName;

    private int displayStatus;

    public int getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(int leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public String getLeadSourceName() {
        return leadSourceName;
    }

    public void setLeadSourceName(String leadSourceName) {
        this.leadSourceName = leadSourceName;
    }

    public int getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(int displayStatus) {
        this.displayStatus = displayStatus;
    }

   
}
