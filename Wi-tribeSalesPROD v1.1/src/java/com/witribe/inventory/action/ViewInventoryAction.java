/*
 * ViewInventoryAction.java
 *
 * Created on October 8, 2009, 12:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.DistributeInventoryForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.util.LogUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SC43278
 */
public class ViewInventoryAction extends BaseAction {
    
    /** Creates a new instance of ViewInventoryAction */
    public ViewInventoryAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
          DistributeInventoryForm dform= (DistributeInventoryForm) form;
          HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
          req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.VIEW_INVENTORY_MGMT);
           String pageCount = req.getParameter("page");
           String flag = req.getParameter("flag");
          try{
             
              if(!validateSession(req,res)) {
                    return mapping.findForward("login");
                }
              
               List objList = new ArrayList();
              String forwardName = "";
             WitribeInventoryBO objBO= new WitribeInventoryBO();
             if(pageCount != null && flag != null) {
                 int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
                  if (WitribeConstants.PAGE_PREVIOUS.equalsIgnoreCase(flag)) {
                     req.setAttribute("page",--pageNum+"");
                     if(pageNum != 0) {
                        objList =  objBO.getCSEInventory(pageNum,salesid); 
                     } else {
                        objList = objBO.getCSEInventory(pageNum,salesid);
                     }
              }
              if (WitribeConstants.PAGE_NEXT.equalsIgnoreCase(flag)) {
                   req.setAttribute("page",++pageNum+"");
                  objList = objBO.getCSEInventory(pageNum,salesid); 
              }
             } else {
                objList = objBO.getCSEInventory(0,salesid);
                req.setAttribute("page","0");
            }

              int listSize = objList.size();

              if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                  req.setAttribute("next",objList.get(listSize-1));
                  objList.remove(listSize-1);
              } 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_INVENTORY);
              req.setAttribute("objCSEInvList",objList);
              forwardName = "viewcseinvstatus";

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
