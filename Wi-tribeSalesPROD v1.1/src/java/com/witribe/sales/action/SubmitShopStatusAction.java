/*
 * SubmitShopStatusAction.java
 *
 * Created on October 12, 2009, 11:39 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;
import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ShopDetailsForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.LogUtil;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author ss73096
 */
public class SubmitShopStatusAction extends BaseAction{
    
    /** Creates a new instance of SubmitShopStatusAction */
    public SubmitShopStatusAction() {
   }
 
 public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {    
    // SalesPersonnelForm sform= (SalesPersonnelForm)form;
    ShopDetailsForm shopform= (ShopDetailsForm) form;
    String shopstatus = shopform.getShopStatus();
     
    try{
                                      
            if(!validateSession(req,res)) {
              return mapping.findForward("login");
            }
         if("Active".equalsIgnoreCase(shopstatus)){
            if(activateShop(shopform,req)) {
                    req.setAttribute(WitribeConstants.HEADING, WitribeConstants.getCHANGE_SHOP_STATUS());
                   return mapping.findForward("success");
                } else {
                     req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_ACTIVATE_SHOP);
                     req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_ACTIVATE_SHOP);
                    return mapping.findForward("failure");
               }
        }else if("Inactive".equalsIgnoreCase(shopstatus)){
                if(inactivateShop(shopform,req)) {
                   req.setAttribute(WitribeConstants.HEADING, WitribeConstants.getCHANGE_SHOP_STATUS());
                   return mapping.findForward("success");
               } else {
                     req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_ACTIVATE_SHOP);
                     req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_INACTIVATE_SHOP);
                    return mapping.findForward("failure");
                }
        
        }  
                   
     }catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
             req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
      }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
       }
    req.setAttribute(WitribeConstants.HEADING, WitribeConstants.getCHANGE_SHOP_STATUS());
     return mapping.findForward("success");
}
 

 boolean activateShop( ShopDetailsForm shopform,HttpServletRequest req)throws SQLException,Exception{
       //SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        ShopsVO objShopVO = new ShopsVO();
        String[] check=req.getParameterValues("check");
        objShopVO.setShopId(shopform.getShopId());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.activateShop(objShopVO,check);
        return status;          
    }
           
  boolean inactivateShop( ShopDetailsForm shopform,HttpServletRequest req)throws SQLException,Exception{
       // SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        ShopsVO objShopVO = new ShopsVO();
        String[] check=req.getParameterValues("check");
        objShopVO.setShopId(shopform.getShopId());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.inactivateShop(objShopVO,check);
        return status;
          
   }
 
}

    

