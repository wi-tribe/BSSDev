/*
 * LeadVO.java
 *
 * Created on January 15, 2009, 10:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;
//import com.witribe.actionform.LeadEntryForm;
/**
 *
 * @author SC43278
 */
public class ProspectVO {
    
    /** Creates a new instance of LeadVO */
    public ProspectVO() {
    }
    private String leadId;
    private String prospectId;
    private String reason;
    private String createDate;
    private int reasonId;
    private String reasonCode;
    private String transitionState;
    private String reasonComment;
    private String salesId;
     private String followUpDate;
     
     public String getLeadId() {
        return leadId;
    }
    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }
    public String getProspectId() {
        return prospectId;
    }
    public void setProspectId(String prospectId) {
        this.prospectId = prospectId;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
   
     public String getCreateDate() {
        return createDate;
    }
     public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getTransitionState() {
        return transitionState;
    }

    public void setTransitionState(String transitionState) {
        this.transitionState = transitionState;
    }

    public String getReasonComment() {
        return reasonComment;
    }

    public void setReasonComment(String reasonComment) {
        this.reasonComment = reasonComment;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }
       
}
