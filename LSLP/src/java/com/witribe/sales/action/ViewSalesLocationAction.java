/*
 * ViewSalesAction.java
 *
 * Created on January 28, 2009, 5:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.SalesPersonnelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.SalesPersonnelVO;
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
public class ViewSalesLocationAction extends BaseAction{
    
    /** Creates a new instance of ViewSalesAction */
    public ViewSalesLocationAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
          SalesPersonnelForm spform= (SalesPersonnelForm)form; 
          req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
          String pageCount = req.getParameter("page");
          String flag = req.getParameter("flag");
         
          try{
               if(!validateSession(req,res) ) {
                return mapping.findForward("login");
            }
               String forwardName = "";
               WitribeSalesBO objBO= new WitribeSalesBO();
               String[] check=req.getParameterValues("check");
                        
              if(check != null && check.length > 0 && (flag == "" || flag == null)) {
                  boolean status = objBO.deleteSalesLocation(check);
                  if(status) {
                      req.setAttribute(WitribeConstants.HEADING,WitribeConstants.DELETE_SALES_LOCATION);
                      return mapping.findForward("deletesuccess");
                    } else {
                        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_DELETE_SALES_LOCATION);
                        req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_DELETE_SALES_LOCATION);
                        return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
                      }
                      
              }else {              
                   List objList = new ArrayList();
                   SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                   HttpSession userSession = (HttpSession)req.getSession(false);
                   objSalesVO.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                   objSalesVO.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                   objSalesVO.setCountry((String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY));
                   objSalesVO.setProvince((String)userSession.getAttribute(WitribeConstants.ADDR_PROVINCE));
                   objSalesVO.setCity((String)userSession.getAttribute(WitribeConstants.ADDR_CITY));
                   objSalesVO.setZone((String)userSession.getAttribute(WitribeConstants.ADDR_ZONE));
                   objSalesVO.setSubzone((String)userSession.getAttribute(WitribeConstants.ADDR_SUBZONE));
                   if(pageCount != null && flag != null) {
                        int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
                        if (WitribeConstants.PAGE_PREVIOUS.equalsIgnoreCase(flag)) {
                             req.setAttribute("page",--pageNum+"");
                             objList =  objBO.getSalesLocation(pageNum,objSalesVO); 
                        }
                          if (WitribeConstants.PAGE_NEXT.equalsIgnoreCase(flag)) {
                               req.setAttribute("page",++pageNum+"");
                               objList = objBO.getSalesLocation(pageNum,objSalesVO); 
                          }
                    } else {
                        objList = objBO.getSalesLocation(0,objSalesVO);
                        req.setAttribute("page","0");
                       }
                    int listSize = objList.size();
          
                    if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                        req.setAttribute("next",objList.get(listSize-1));
                        objList.remove(listSize-1);
                    } 
                        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_LOCATION_SALES_PERSONNEL);
                        req.setAttribute("objSalesList",objList);
                        forwardName = "success";
              }
             
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
