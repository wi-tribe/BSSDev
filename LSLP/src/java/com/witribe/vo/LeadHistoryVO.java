/*
 * LeadHistoryVO.java
 *
 * Created on September 7, 2009, 2:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author BS68483
 */
public class LeadHistoryVO {
    
    /** Creates a new instance of LeadHistoryVO */
    public LeadHistoryVO() {
    }
     private String leadID;
  private String leadHistoryID;  
 
  private String salesPersonID;
  private String modifiedDate;
  private String status;
 
  private String comments;
  private String transitionState;

    public String getLeadHistoryID() {
        return leadHistoryID;
    }

    public void setLeadHistoryID(String LeadHistoryID) {
        this.leadHistoryID = LeadHistoryID;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String ModifiedDate) {
        this.modifiedDate = ModifiedDate;
    }

  

    public String getLeadID() {
        return leadID;
    }

    public void setLeadID(String LeadID) {
        this.leadID = LeadID;
    }

    public String getSalesPersonID() {
        return salesPersonID;
    }

    public void setSalesPersonID(String SalesPersonID) {
        this.salesPersonID = SalesPersonID;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String Comments) {
        this.comments = Comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    public String getTransitionState() {
        return transitionState;
    }

    public void setTransitionState(String transitionState) {
        this.transitionState = transitionState;
    }


}
