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
public class RsmCityAction extends BaseAction{
    
    /** Creates a new instance of AssignShopToSaleAction */
    public RsmCityAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        SalesPersonnelForm sform= (SalesPersonnelForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        List objRsmList = new ArrayList();
        List objCityList = new ArrayList();
        List objRsmCityList = new ArrayList();
         List objTlList = new ArrayList();
        List objCseList = new ArrayList();      
        try{
            if(!validateSession(req,res)) {
                    return mapping.findForward("login");
                }
            if(sform.getSalesId() == null) {
                objRsmCityList = getRsmCityList(req,res);
                objRsmList =(List) objRsmCityList.get(0);
                objTlList =(List) objRsmCityList.get(1);
                objCseList =(List) objRsmCityList.get(2);
                req.setAttribute("objRsmList",objRsmList);
                req.setAttribute("objTlList",objTlList);
                req.setAttribute("objCseList",objCseList);
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ADD_LOCATION); 
            } else {
                SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalestype(sform.getSalestype());
                String type = sform.getSalestype();
                objSalesVO.setZone(sform.getZone());
                objSalesVO.setSubzone(sform.getSubzone());
                objSalesVO.setCity(sform.getCity());
                objSalesVO.setCountry(WitribeConstants.COUNTRY);
                objSalesVO.setSalesId(sform.getSalesId());
                if(type.equals("2"))
                {                    
                    objSalesVO.setSalesId(req.getParameter("salesIdRSM"));
                }
                else if(type.equals("3")){
                    objSalesVO.setSalesId(req.getParameter("salesIdTL"));
                }
                else if(type.equals("4")){
                    objSalesVO.setSalesId(req.getParameter("salesIdCSE"));
                }
                objSalesVO.setLocation(sform.getLocation());
                objSalesVO.setSalesLocation(req.getParameterValues("location"));
                WitribeSalesBO objsalesBO= new WitribeSalesBO();
                 if(!objsalesBO.addLocation(objSalesVO)) {
                    req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.MSG_ADD_LOCATION);
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
    
     List getRsmCityList(HttpServletRequest req,HttpServletResponse res)throws SQLException, Exception{
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
       List objRsmCityList = objsalesBO.getRsmCity(objSalesVO);
       return objRsmCityList;
       
     }
}
