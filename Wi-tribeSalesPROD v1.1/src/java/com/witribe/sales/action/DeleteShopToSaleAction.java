/*
 * DeleteShopToSaleAction.java
 *
 * Created on February 13, 2009, 12:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ShopSaleMappingForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ShopSaleVO;
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
public class DeleteShopToSaleAction extends BaseAction{
    
    /** Creates a new instance of DeleteShopToSaleAction */
    public DeleteShopToSaleAction() {
    }
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest req, HttpServletResponse res ){
        ShopSaleMappingForm sform = (ShopSaleMappingForm)form;
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.ASSOCIATE_TL_SHOP);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(deleteShopToSales(sform,req)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP_DELETE);
                return mapping.findForward("success");
            }
            else
            {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAIL_TL_TO_SHOP_DELETE);
                req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAIL_TL_TO_SHOP_DELETE);
                return mapping.findForward("failure");
            }
            
        }catch(SQLException e) {
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
       // req.setAttribute("heading","DELETE TL/CSE TO SHOP");
       // return mapping.findForward("success");
    }
    
    boolean deleteShopToSales(ShopSaleMappingForm sform, HttpServletRequest req)throws SQLException,Exception{
        ShopSaleVO objSalesVO = new ShopSaleVO();
        WitribeSalesBO objBO= new WitribeSalesBO();
        String[] check = req.getParameterValues("check");
       //Added by Bhawna on 8th october,2009
         String[] temp=null;  
         for(int i=0;i<check.length;i++)
           {
           temp = check[i].split("~");
           check[i]=temp[i];
           } //end 
        boolean status = objBO.deleteShopToSales(objSalesVO,check);
        return status;
    }
}
