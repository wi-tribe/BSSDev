/*
 * SubmitLeadAction.java
 *
 * Created on January 7, 2009, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

/**
 *
 * @author SC43278
 */
import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
import com.witribe.vo.ProspectVO;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

public class UpdateJobStatusAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public UpdateJobStatusAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LeadEntryForm lform= (LeadEntryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_ORDER_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(false);
        String salesID = (String)userSession.getAttribute(WitribeConstants.SALES_ID);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
             if(lform.getLeadStatushidden().equalsIgnoreCase(WitribeConstants.CUSTOMER_INITIATED)){
                 if(updateLeadStatus(lform,salesID)){
                   req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_SUCCESS);
                   return mapping.findForward("success");  
                 }
                 
             } else if(updateReason(lform,salesID)){
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_SUCCESS);
                return mapping.findForward("success");
             }
            
        }catch(SQLException e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        
       return mapping.findForward("failure");
    }
    
    boolean updateLeadStatus(LeadEntryForm lform,String SalesId)throws SQLException,Exception{
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setLeadId(lform.getLeadId());
        objLeadVO.setSalesId(SalesId);
        objLeadVO.setLeadStatus(lform.getLeadStatus());
        objLeadVO.setAmount(lform.getAmount());
        objLeadVO.setReasonCode(WitribeConstants.JOB_STATUS_INITIATED);
        objLeadVO.setTransitionState(WitribeConstants.LEAD_TAG_INITIATED);
        objLeadVO.setReasonComment(WitribeConstants.COMMENT_CREATION_COMPLETED);
        objLeadVO.setFollowUpDate(lform.getFollowUpDate());
        WitribeBO objBO= new WitribeBO();
        boolean status = objBO.updateLeadStatus(objLeadVO);
        return status;
              
    }
     boolean updateReason(LeadEntryForm lform,String salesID)throws SQLException,Exception{
        ProspectVO objProspectVO = new ProspectVO();
        objProspectVO.setLeadId(lform.getLeadId());
        objProspectVO.setSalesId(salesID);
        objProspectVO.setReasonId(lform.getReasonId());
        objProspectVO.setReasonCode(lform.getReasonCode());
        objProspectVO.setTransitionState(lform.getTransitionState());
        //bhawna 15th sep 2009
        objProspectVO.setFollowUpDate(lform.getFollowUpDate());
         //bhawna 15th sep 2009
        objProspectVO.setReasonComment(lform.getReasonComment());
        WitribeBO objBO= new WitribeBO();
        boolean status = objBO.createLeadHistory(objProspectVO);
        return status;
              
    }
}
