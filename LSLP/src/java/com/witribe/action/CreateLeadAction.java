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
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import java.util.List;

public class CreateLeadAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public CreateLeadAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LeadEntryForm lform= (LeadEntryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
        List objSouceList = new ArrayList();
        List objISPList = new ArrayList();
        List objReasonList = new ArrayList();
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        WitribeBO leadbo = new WitribeBO();
        objReasonList = leadbo.getProspectReason();
        objSouceList = leadbo.getLeadSource();
        objISPList = leadbo.getISPList();
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CREATE_LEAD);
        req.setAttribute("LeadSourceList",objSouceList);
        req.setAttribute("LeadISPList",objISPList);
        req.setAttribute("objReasonList",objReasonList);
        return mapping.findForward("success");
       
    }
    
    
}
