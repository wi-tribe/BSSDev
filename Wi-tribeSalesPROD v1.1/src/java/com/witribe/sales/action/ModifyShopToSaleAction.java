/*
 * ModifyShopToSaleAction.java
 *
 * Created on February 13, 2009, 11:58 AM
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
public class ModifyShopToSaleAction extends BaseAction{
    
    /** Creates a new instance of ModifyShopToSaleAction */
    public ModifyShopToSaleAction() {
    }
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res){
        ShopSaleMappingForm ssform = (ShopSaleMappingForm)form;
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.ASSOCIATE_TL_SHOP);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");                
            }
             
            if(ssform.getSalespersonnel_id()==null)
            {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.MODIFY_TL_TO_SHOP);
                
                return mapping.findForward("success");
            }
            else
            {
                WitribeSalesBO wsbo = new WitribeSalesBO();
                ShopSaleVO salesobj = new ShopSaleVO();
                salesobj.setSalespersonnel_id(ssform.getSalespersonnel_id());
                salesobj.setShop_id(ssform.getShop_id());
                
               
                if(wsbo.updateShopToSales(salesobj))
                {
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP_MODIFIED);
                    return mapping.findForward("save");
                }
                else
                 {
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAIL_TL_TO_SHOP_MODIFY);
                    req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAIL_TL_TO_SHOP_MODIFY);
                    return mapping.findForward("failure");
                }
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
        //req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_TL_TO_SHOP_MODIFY);
       // return mapping.findForward("failure");
    }
    
}
