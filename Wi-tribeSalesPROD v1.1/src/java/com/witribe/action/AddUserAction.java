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
import com.witribe.actionform.UserRegForm;
import com.witribe.constants.WitribeConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;

public class AddUserAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public AddUserAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        UserRegForm uform= (UserRegForm) form; 
        try{
             if(!validateSession(req,res) || 
                    !validateAdminRole(req,res)) {
                return mapping.findForward("login");
            }
        } catch(Exception e) {
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute("heading",WitribeConstants.CREATE_USER);
        return mapping.findForward("success");
       
    }
    
    
}
