/*
 * AssignShopToSaleAction.java
 *
 * Created on February 12, 2009, 4:34 PM
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
public class TlZoneAction extends BaseAction{
    
    /** Creates a new instance of AssignShopToSaleAction */
    public TlZoneAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        SalesPersonnelForm sform= (SalesPersonnelForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        List objTlList = new ArrayList();
        
              
        try{
            if(!validateSession(req,res)) {
                    return mapping.findForward("login");
                }
            if(sform.getSalesId() == null || req.getAttribute("errorString") != null) {
                objTlList = getTlList(req,res);
                req.setAttribute("objTlList",objTlList);
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ADD_ZONE); 
            } else {
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(sform.getSalesId());
                objSalesVO.setZone(sform.getZone());
                WitribeSalesBO objsalesBO= new WitribeSalesBO();
                if(!objsalesBO.addLocation(objSalesVO)) {
                    req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.MSG_ADD_ZONE);
                    return mapping.findForward("duplicate");
                }
                
            }
            }    catch(SQLException e) {
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
      return mapping.findForward("success");
    }
    
     List getTlList(HttpServletRequest req,HttpServletResponse res)throws SQLException, Exception{
       SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
       HttpSession userSession = (HttpSession)req.getSession(false);
       String role =(String)userSession.getAttribute(WitribeConstants.ROLE);
       String country = (String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY);
       String province = (String)userSession.getAttribute(WitribeConstants.ADDR_PROVINCE);
       String city = (String)userSession.getAttribute(WitribeConstants.ADDR_CITY);
       String zone = (String)userSession.getAttribute(WitribeConstants.ADDR_ZONE);
       String salesId = (String)userSession.getAttribute(WitribeConstants.SALES_ID);
       objSalesVO.setSalesId(salesId);
       objSalesVO.setSalestype(role);
       objSalesVO.setCountry(country);
       objSalesVO.setProvince(province);
       objSalesVO.setCity(city);
       objSalesVO.setZone(zone);
       WitribeSalesBO objsalesBO= new WitribeSalesBO();
       List objTlList = objsalesBO.getTL(objSalesVO);
       return objTlList;
       
     }
}
