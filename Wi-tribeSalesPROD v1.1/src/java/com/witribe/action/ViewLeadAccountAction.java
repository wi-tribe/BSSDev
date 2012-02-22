/*
 * ViewLeadAccountAction.java
 *
 * Created on June 10, 2009, 11:15 AM
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
public class ViewLeadAccountAction extends BaseAction{
    
    /** Creates a new instance of ViewLeadAccountAction */
    public ViewLeadAccountAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
          LeadEntryForm lform= (LeadEntryForm)form; 
          req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
          //String pageCount = req.getParameter("page");
         // String flag = req.getParameter("flag");
          HttpSession userSession =  (HttpSession)req.getSession(false);
          String fdate = lform.getFromDate();
          String tdate = lform.getToDate();
          try{
               if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              String forwardName = "";
               WitribeBO objBO= new WitribeBO();
              
              /*if(lform.getLeadId() != null) {
                   List objSalesList = new ArrayList();
                if(Integer.parseInt((String)userSession.getAttribute(WitribeConstants.ROLE)) < 4){
                   SalesPersonnelVO objSales = new SalesPersonnelVO();
                   req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ASSIGN_LEAD); 
                   objSales.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                   objSales.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                   objSalesList =  objBO.GetChildSalesList(objSales); 
                    if(objSalesList != null && objSalesList.size() >1) {
                      req.setAttribute("salesObj",objSalesList.get(0));
                      req.setAttribute("salesList",objSalesList.get(1)); 
                   } else {
                       req.setAttribute("salesList",objSalesList.get(0));
                   }
                   req.setAttribute("leadId",lform.getLeadId()); 
                   forwardName = "viewcse";
                } else {
                  LeadVO objLeadVO = objBO.getLead(lform.getLeadId());
                  req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_LEAD);
                  req.setAttribute("objLead",objLeadVO);
                  forwardName = "showlead";
                }
              } else {*/
                    
                    LeadVO objLeadVO = new LeadVO();
                    objLeadVO.setFromDate(fdate);
                    objLeadVO.setToDate(tdate);
                   /* objSalesVO.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                    objSalesVO.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));*/
                   List objList = new ArrayList();
                   SalesPersonnelVO objSales = new SalesPersonnelVO();
                   req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ASSIGN_LEAD); 
                   objSales.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                   objSales.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                   objSales.setCity((String)userSession.getAttribute(WitribeConstants.ADDR_CITY));
                   objSales.setZone((String)userSession.getAttribute(WitribeConstants.ADDR_ZONE));
                   objSales.setCountry((String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY));
                   /*if(pageCount != null && flag != null) {
                        int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
                        if ("P".equalsIgnoreCase(flag)) {
                             req.setAttribute("page",--pageNum+"");
                             objList =  objBO.getNextAccountLeads(objSalesVO,pageNum); 
                        }
                          if ("N".equalsIgnoreCase(flag)) {
                               req.setAttribute("page",++pageNum+"");
                               objList = objBO.getNextAccountLeads(objSalesVO,pageNum); 
                          }
                    } else {*/
                   if(fdate != null && tdate != null){
                        objList = objBO.getNextAccountLeads(objLeadVO,objSales);
                   }
                        /*req.setAttribute("page","0");
                       }
                    int listSize = objList.size();
          
                    if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                        req.setAttribute("next",objList.get(listSize-1));
                        objList.remove(listSize-1);
                    } */
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_LEADS);
                    req.setAttribute("objLeadList",objList);
                    req.setAttribute("fromDate",fdate);
                    req.setAttribute("toDate",tdate);
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
