/*
 * ShopDataDeleteAction.java
 *
 * Created on February 6, 2009, 11:29 AM
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
 * @author SC43278
 */
public class ShopDataDeleteAction extends BaseAction{
    
    /** Creates a new instance of ShopDataDeleteAction */
    public ShopDataDeleteAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        ShopDetailsForm shopform= (ShopDetailsForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(deleteShop(shopform,req)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.DELETE_SHOP);
                return mapping.findForward("success");
            }
            else
            {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_DELETE_SHOP);
                req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_DELETE_SHOP);
                return mapping.findForward("failure");
            }
        } catch(SQLException e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
            if(e.getErrorCode() == WitribeConstants.ORA_REF_ERR) {
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_DELETE_SHOP); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_SHOP_MAPPED); 
               return mapping.findForward("failure") ;
           }
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
   //    req.setAttribute("heading","Failed Deliting Shop Data");
     //  return mapping.findForward("failure") ;
    }
    boolean deleteShop(ShopDetailsForm shopform,HttpServletRequest req)throws SQLException,Exception{
        ShopsVO objShopVO = new ShopsVO();
        String[] check=req.getParameterValues("check");
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.deleteShops(objShopVO,check);
        return status;
          
    }
}
