/*
 * ShopSalesMappingAction.java
 *
 * Created on January 29, 2009, 5:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ShopSaleMappingForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopSaleVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.LogUtil;
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
public class ShopSalesMappingAction extends BaseAction {
    
    /** Creates a new instance of ShopSalesMappingAction */
    public ShopSalesMappingAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
         ShopSaleMappingForm  ssform= (ShopSaleMappingForm) form;
         
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.ASSOCIATE_TL_SHOP);
         HttpSession userSession =  (HttpSession)req.getSession(false);
         SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
         String SalesPersonId = (String)userSession.getAttribute(WitribeConstants.SALES_ID);
         String Zone=(String)userSession.getAttribute(WitribeConstants.ADDR_ZONE);
         String City =(String)userSession.getAttribute(WitribeConstants.ADDR_CITY);
         String Province =(String)userSession.getAttribute(WitribeConstants.ADDR_PROVINCE);
         String type =(String)userSession.getAttribute(WitribeConstants.ROLE);
         WitribeSalesBO objBO = new WitribeSalesBO();
         String forwardName = "";
        try{
             if(!validateSession(req,res)) {
                     return mapping.findForward("login");
            }
           
             List objList = new ArrayList();
            
             List shopsobjList = new ArrayList();

             objSalesVO.setSalesId(SalesPersonId);
              //objSalesVO.setZone(Zone);
             objSalesVO.setCity(City);
             objSalesVO.setProvince(Province);
             objSalesVO.setCountry(WitribeConstants.COUNTRY);
             objSalesVO.setSalestype(type);

             ShopsVO objSPVO = new ShopsVO();
             shopsobjList = objBO.getShopsToMap(objSPVO,objSalesVO);
                          
             //objList = objBO.getUnAssignedTLCSE((ShopsVO) shopsobjList.get(0));
             req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP);
             //req.setAttribute("objShopSalesList",objList);
             req.setAttribute("objShopsList",shopsobjList);
             forwardName = "shoptosales";
            
        }catch(Exception e) {
               e.printStackTrace();
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP);
        
        return mapping.findForward(forwardName);
       
    }
}
