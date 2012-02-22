/*
 * ShopDataModifyAction.java
 *
 * Created on February 6, 2009, 10:59 AM
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
public class ShopDataModifyAction extends BaseAction{
    
    /** Creates a new instance of ShopDataModifyAction */
    public ShopDataModifyAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        ShopDetailsForm shopform= (ShopDetailsForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(modifyShop(shopform)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.MODIFY_SHOP);
                return mapping.findForward("success");
            }
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_MODIFY_SHOP);
       return mapping.findForward("failure") ;
    }
    boolean modifyShop(ShopDetailsForm shopform)throws SQLException,Exception{
        ShopsVO objShopVO = new ShopsVO();
        objShopVO.setShopId(shopform.getShopId());
        objShopVO.setShopname(shopform.getShopname());
        //objShopVO.setShopType(shopform.getShopType());
        objShopVO.setPlot(shopform.getPlot());
        objShopVO.setStreet(shopform.getStreet());
        //objShopVO.setSubzone(shopform.getSubzone());
        //objShopVO.setZone(shopform.getZone());
        //objShopVO.setCity(shopform.getCity());
        //objShopVO.setProvince(shopform.getProvince());
        //objShopVO.setCountry(shopform.getCountry());
        //objShopVO.setZip(shopform.getZip());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.modifyShop(objShopVO);
        return status;
          
    }
    
}
