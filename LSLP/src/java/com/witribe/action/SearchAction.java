/*
 * SearchAction.java
 *
 * Created on June 11, 2009, 10:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

import com.witribe.actionform.LeadEntryForm;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SC43278
 */
public class SearchAction extends BaseAction{
    
    /** Creates a new instance of SearchAction */
    public SearchAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LeadEntryForm lform= (LeadEntryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
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
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SEARCH_LEAD);
        return mapping.findForward("search");
       
    }
}
