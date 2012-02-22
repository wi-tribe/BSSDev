/*
 * SubmitLeadAction.java
 *
 * Created on January 7, 2009, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;

public class LogoutAction extends Action{
    
    /** Creates a new instance of SubmitLeadAction */
    public LogoutAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
      HttpSession userSession =  (HttpSession)req.getSession(false);
      try{
          res.setContentType("text/html");
          res.setHeader("Cache-Control",WitribeConstants.NO_CACHE); 
          res.setHeader("Cache-Control",WitribeConstants.NO_STORE);
          res.setDateHeader("Expires", WitribeConstants.ZERO);
          res.setHeader("Pragma",WitribeConstants.NO_CACHE); 
          if(userSession != null) {
              userSession.removeAttribute("username");
              userSession.invalidate();
          }
      } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
      if(req.getParameter("timeout") != null) {
          req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_TIME_OUT);          
      }
      if(req.getAttribute(WitribeConstants.ERR_VAR) == null &&
              req.getAttribute(WitribeConstants.INFO_VAR) == null) {
          req.setAttribute(WitribeConstants.INFO_VAR,WitribeConstants.MSG_LOGOUT_SUC);
          
      }
      return mapping.findForward("login");
       
    }
    
    
}
