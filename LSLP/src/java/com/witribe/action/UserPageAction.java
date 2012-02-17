/*
 * UserPageAction.java
 *
 * Created on January 27, 2009, 4:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;
import com.witribe.actionform.UserRegForm;
import com.witribe.bo.WitribeBO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;

/**
 *
 * @author SC43278
 */
public class UserPageAction extends BaseAction{
    
    /** Creates a new instance of UserPageAction */
    public UserPageAction() {
    }
    
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
          UserRegForm uform= (UserRegForm)form; 
          try{
          String pageCount = req.getParameter("page");
          String flag = req.getParameter("flag");
          int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
           if(!validateSession(req,res)) {
            return mapping.findForward("login");
        }
          List objList = new ArrayList();
          String forwardName = "";
          WitribeBO objBO= new WitribeBO();
          if (WitribeConstants.PAGE_PREVIOUS.equalsIgnoreCase(flag)) {
                 req.setAttribute("page",--pageNum+"");
                 if(pageNum != 0) {
                    objList =  objBO.getNextUsers(pageNum); 
                 } else {
                     objList = objBO.getNextUsers(pageNum); 
                 }
          }
          if (WitribeConstants.PAGE_NEXT.equalsIgnoreCase(flag)) {
               req.setAttribute("page",++pageNum+"");
               objList = objBO.getNextUsers(pageNum); 
          }
          
          int listSize = objList.size();
          
          if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
              req.setAttribute("next",objList.get(listSize-1));
              objList.remove(listSize-1);
          } else {
              ++pageNum;
          }
          req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_USER);
          req.setAttribute("objUserList",objList);
          forwardName = "nextviewusers";
                  
          return mapping.findForward(forwardName); 
          } catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
          
        }
    
}
