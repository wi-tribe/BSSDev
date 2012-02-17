/*
 * PageProspectAction.java
 *
 * Created on January 22, 2009, 5:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;
import com.witribe.actionform.LeadEntryForm;
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
import javax.servlet.http.HttpSession;
/**
 *
 * @author SC43278
 */
public class PageProspectAction extends BaseAction {
    
    /** Creates a new instance of PageProspectAction */
    public PageProspectAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
         /* LeadEntryForm lform= (LeadEntryForm)form; 
          HttpSession userSession =  (HttpSession)req.getSession(true);
          String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
          req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
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
                        objList =  objBO.getPageProspects(pageNum,salesid); 
                     } else {
                        objList = objBO.getPageProspects(pageNum,salesid);
                     }
              }
              if (WitribeConstants.PAGE_NEXT.equalsIgnoreCase(flag)) {
                   req.setAttribute("page",++pageNum+"");
                  objList = objBO.getPageProspects(pageNum,salesid); 
              }

              int listSize = objList.size();

              if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                  req.setAttribute("next",objList.get(listSize-1));
                  objList.remove(listSize-1);
              } else {
                  ++pageNum;
              }
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_PAGE_PROSPECTS);
              req.setAttribute("objProspectList",objList);
              forwardName = "viewpageprospects";

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
        }*/
        return mapping.findForward("success");
          
        }
}
