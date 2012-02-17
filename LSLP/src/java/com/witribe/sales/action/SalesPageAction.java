/*
 * SalesPageAction.java
 *
 * Created on January 28, 2009, 7:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.SalesPersonnelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SC43278
 */
public class SalesPageAction extends BaseAction{
    
    /** Creates a new instance of SalesPageAction */
    public SalesPageAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
          /*SalesPersonnelForm spform= (SalesPersonnelForm)form; 
          req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
          try{
          String pageCount = req.getParameter("page");
          String flag = req.getParameter("flag");
          int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
           if(!validateSession(req,res)) {
            return mapping.findForward("login");
        }
          List objList = new ArrayList();
          String forwardName = "";
          WitribeSalesBO objBO= new WitribeSalesBO();
          if ("P".equalsIgnoreCase(flag)) {
                 req.setAttribute("page",--pageNum+"");
                 if(pageNum != 0) {
                    //objList =  objBO.getNextSales(pageNum); 
                 } else {
                     //objList = objBO.getNextSales(pageNum); 
                 }
          }
          if ("N".equalsIgnoreCase(flag)) {
               req.setAttribute("page",++pageNum+"");
               //objList = objBO.getNextSales(pageNum); 
          }
          
          int listSize = objList.size();
          
          if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
              req.setAttribute("next",objList.get(listSize-1));
              objList.remove(listSize-1);
          } else {
              ++pageNum;
          }
          req.setAttribute("heading","VIEW SALES");
          req.setAttribute("objSalesList",objList);
          forwardName = "nextviewsales";
                  
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
          return null; 
        }
        
}
