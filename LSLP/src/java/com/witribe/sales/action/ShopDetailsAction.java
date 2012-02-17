/*
 * ShopDetailsAction.java
 *
 * Created on January 28, 2009, 5:15 PM
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
public class ShopDetailsAction extends BaseAction{
    
    /** Creates a new instance of ShopDetailsAction */
    public ShopDetailsAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
    ShopDetailsForm shopsform= (ShopDetailsForm)form;
    req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            String status = createShop(shopsform);
              if("".equals(status)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CREATE_SHOPS);
                return mapping.findForward("success");
            } else {
                req.setAttribute(WitribeConstants.ERROR_STRING,status);
                //return mapping.findForward("duplicate");
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_CREATE_SHOPS);
       return mapping.findForward("failure") ;
    }
    String createShop(ShopDetailsForm shopsform)throws SQLException,Exception{
        ShopsVO objshopsVO = new ShopsVO();
        WitribeSalesBO objBO = new WitribeSalesBO();
        objshopsVO.setShopId(shopsform.getShopId());
        objshopsVO.setShopname(shopsform.getShopname());
        //objshopsVO.setShopType(shopsform.getShopType());
        objshopsVO.setPlot(shopsform.getPlot());
        objshopsVO.setStreet(shopsform.getStreet());
        objshopsVO.setSubzone(shopsform.getSubzone());
        objshopsVO.setZone(shopsform.getZone());
        objshopsVO.setCity(shopsform.getCity());
        objshopsVO.setProvince(shopsform.getProvince());
        objshopsVO.setCountry(shopsform.getCountry());
        objshopsVO.setZip(shopsform.getZip());
        String status = objBO.createShop(objshopsVO);
        return status;
    
}
}
