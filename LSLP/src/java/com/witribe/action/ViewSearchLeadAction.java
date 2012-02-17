/*
 * ViewSearchLeadAction.java
 *
 * Created on June 10, 2009, 3:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
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
public class ViewSearchLeadAction extends BaseAction{
    
    /** Creates a new instance of ViewSearchLeadAction */
    public ViewSearchLeadAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
          LeadEntryForm lform= (LeadEntryForm)form; 
          req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
          //String pageCount = req.getParameter("page");
         // String flag = req.getParameter("flag");
          HttpSession userSession =  (HttpSession)req.getSession(false);
          String fdate = lform.getFromDate();
          String tdate = lform.getToDate();
          String searchlead = lform.getSearchLead();
          String searchBy = lform.getSearchby();
          try{
               if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              String forwardName = "";
               WitribeBO objBO= new WitribeBO();
               LeadVO objLeadVo = new LeadVO();
               objLeadVo.setSearchby(lform.getSearchby());
               objLeadVo.setFromDate(lform.getFromDate());
               objLeadVo.setToDate(lform.getToDate());
                   List objList = new ArrayList();
                   SalesPersonnelVO objSales = new SalesPersonnelVO();
                   req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ASSIGN_LEAD); 
                   objSales.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                   objSales.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                   objSales.setCity((String)userSession.getAttribute(WitribeConstants.ADDR_CITY));
                   objSales.setZone((String)userSession.getAttribute(WitribeConstants.ADDR_ZONE));
                   objSales.setCountry((String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY));
                  /* if((pageCount != null && pageCount != "") && flag != null) {
                        int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
                        if ("P".equalsIgnoreCase(flag)) {
                             req.setAttribute("page",--pageNum+"");*/
                   if(("DateRange".equalsIgnoreCase(searchBy))) {
                             objList =  objBO.getSearchedLeads(objLeadVo,objSales); 
                             
                   }     else {
                        objList =  objBO.getSearchedLeadsBy(objLeadVo,searchlead,objSales); 
                       
                   }
                       /* }
                          if ("N".equalsIgnoreCase(flag)) {
                               req.setAttribute("page",++pageNum+"");
                               objList = objBO.getSearchedLeads(objSalesVO,pageNum,serachlead); 
                          }*/
                    /*} else {
                        objList = objBO.getSearchedLeads(objSalesVO,0,serachlead);
                        req.setAttribute("page","0");
                       }*/
                    int listSize = objList.size();
          
                    /*if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                        req.setAttribute("next",objList.get(listSize-1));
                        objList.remove(listSize-1);
                    } */
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SEARCH_LEADS);
                    req.setAttribute("objLeadList",objList);
                    req.setAttribute("fromDate",fdate);
                    req.setAttribute("toDate",tdate);
                    req.setAttribute("searchLead",searchlead);
                    req.setAttribute("searchBy",searchBy);
                    forwardName = "viewaccountleads";
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
