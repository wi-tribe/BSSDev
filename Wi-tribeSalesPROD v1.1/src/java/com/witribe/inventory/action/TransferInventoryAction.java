/*
 * TransferInventoryAction.java
 *
 * Created on October 13, 2009, 9:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.sales.vo.SalesPersonnelVO;
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
public class TransferInventoryAction extends BaseAction {
    /** Creates a new instance of TransferInventoryAction */
    public TransferInventoryAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
         RaiseRequestForm rform= (RaiseRequestForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.TRAN_INVENTORY_MGMT);  
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
                List objList = new ArrayList();
                List objToShopList = new ArrayList();
                List objProvList = new ArrayList();
                List objShopList = new ArrayList();
                WitribeBO wbo = new WitribeBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                HttpSession userSession =  (HttpSession)req.getSession(false);
                SalesPersonnelVO objSales = new SalesPersonnelVO();
                objSales.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                objSales.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                objSales.setCity((String)userSession.getAttribute(WitribeConstants.ADDR_CITY));
                objSales.setZone((String)userSession.getAttribute(WitribeConstants.ADDR_ZONE));
                objSales.setCountry((String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY)); 
                int role = Integer.parseInt(objSales.getSalestype());
               if( role == Integer.parseInt(WitribeConstants.TYPE_ADMIN)){
                  objProvList = wbo.getAdminSalesList(objSales) ; 
               } else {
                  objProvList = wbo.getTransferProvList(objSales);  
               }
                WitribeInventoryBO objbO= new WitribeInventoryBO();
                String shopId = objInventoryVO.getShopId();
                for(int i = 0;i<objProvList.size();i++) {
                    SalesPersonnelVO salesVO = (SalesPersonnelVO)objProvList.get(i);
                       objShopList = objbO.requestShopsId(salesVO.getChildSalesId());  
                       objToShopList =  objbO.requestShopsId(salesVO.getChildSalesId()); 
                if(objShopList.size()>0){
                if(objInventoryVO.getShopId() == null) {
                    ShopsVO shopvo=(ShopsVO)objShopList.get(0);
                    shopId=shopvo.getShopId();
                    objInventoryVO.setInventorytype(WitribeConstants.CPE);
                    objInventoryVO.setSubtype(WitribeConstants.SUBTYPE_INDOOR);
                   }
              objList = objbO.fetchTransInventory(objInventoryVO,shopId);
              }
                  }
               //AddressMappingVO addr = (AddressMappingVO)objList1.get(0);
                 //String prov = addr.getProvince();              
                //CityList = wbo.getSubAddress("2",prov,"0");
                req.setAttribute("objProvList",objProvList);
              req.setAttribute("objShopList",objShopList);
              req.setAttribute("objInventoryList",objList);
              req.setAttribute("objToShopList",objToShopList);
              req.setAttribute("shopId",shopId);
              req.setAttribute("invtype",objInventoryVO.getInventorytype());
              req.setAttribute("invsubtype",objInventoryVO.getSubtype()); 
               
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TRANSFER_INVENTORY);
        return mapping.findForward("success");
     }
}
