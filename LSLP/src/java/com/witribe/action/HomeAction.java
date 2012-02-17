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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;

public class HomeAction extends Action{
    
    /** Creates a new instance of SubmitLeadAction */
    public HomeAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
     
      req.setAttribute("breadcrumb",WitribeConstants.BREADCRUMB);
      req.setAttribute(WitribeConstants.HEADING,WitribeConstants.WEB_PORTAL_LOGIN);
      return mapping.findForward("login");
       
    }
    
    
}
