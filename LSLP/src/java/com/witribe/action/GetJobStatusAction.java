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
import com.witribe.vo.LeadHistoryVO;
import java.util.ArrayList;
import java.util.List;

public class GetJobStatusAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public GetJobStatusAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        LeadEntryForm lform= (LeadEntryForm)form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_ORDER_MANAGEMENT);
         List objReasonList = new ArrayList();
         List statusList = new ArrayList();
         String transState = null;
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            //if(lform.getLeadStatus().equalsIgnoreCase(WitribeConstants.PROSPECT_CREATED)){
                 WitribeBO Prospectbo = new WitribeBO();
                 objReasonList = Prospectbo.getProspectReason();
                 LeadHistoryVO objLeadStatusVO= new LeadHistoryVO();
                 objLeadStatusVO.setLeadID(lform.getLeadId());
                 if(lform.getReasonCode() == null || "".equals(lform.getReasonCode())){
                    transState = "";
                 } else {
                 objLeadStatusVO = Prospectbo.getTransState(objLeadStatusVO);
                  transState = objLeadStatusVO.getTransitionState();
                 }
                 req.setAttribute("transState",transState);
                 req.setAttribute("objReasonList",objReasonList);
                 
                // }
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
         
         req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_STATUS);
         return mapping.findForward("success"); 
           }
}
