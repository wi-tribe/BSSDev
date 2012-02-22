/*
 * PageAction.java
 *
 * Created on January 20, 2009, 4:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;
import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;

/**
 *
 * @author SC43278
 */
public class PageAction extends BaseAction {
    
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
          /*LeadEntryForm lform= (LeadEntryForm)form; 
          try{
          String pageCount = req.getParameter("page");
          String flag = req.getParameter("flag");
          
           
          List objList = new ArrayList();
          String forwardName = "";
          
          WitribeBO objBO= new WitribeBO();
          if(pageCount != null && flag != null) {
              int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
              if ("P".equalsIgnoreCase(flag)) {
                     req.setAttribute("page",--pageNum+"");
                     //objList =  objBO.getNextLeads(pageNum); 
                 }
              if ("N".equalsIgnoreCase(flag)) {
                   req.setAttribute("page",++pageNum+"");
                   //objList = objBO.getNextLeads(pageNum); 
              }
          } else {
                // objList = objBO.getNextLeads(0);
                 req.setAttribute("page","0");
          }
          int listSize = objList.size();
          
          if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
              req.setAttribute("next",objList.get(listSize-1));
              objList.remove(listSize-1);
          } 
          req.setAttribute("heading",WitribeConstants.VIEW_LEADS);
          req.setAttribute("objLeadList",objList);
          forwardName = "nextviewleads";
            
          return mapping.findForward(forwardName); 
       } catch(SQLException e) {
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }*/
        if(!validateSession(req,res)) {
            return mapping.findForward("login");
        }
       String  forwardName = "nextviewleads";
     
            return mapping.findForward(forwardName);  
        }
       
    /** Creates a new instance of PageAction */
    public PageAction() {
    }
    
}
