/*
 * CheckShopToSaleAction.java
 *
 * Created on February 13, 2009, 4:31 PM
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
public class CheckShopToSaleAction extends BaseAction {
    
    /** Creates a new instance of CheckShopToSaleAction */
    public CheckShopToSaleAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
    ShopSaleMappingForm ssform= (ShopSaleMappingForm) form;
    ShopSaleVO saleVO= new ShopSaleVO();
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.ASSOCIATE_TL_SHOP);
        HttpSession userSession =  (HttpSession)req.getSession(false);
        WitribeSalesBO objBO = new WitribeSalesBO();
        String forwardName = "";
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
             
             List objList = new ArrayList();
        //     objList = objBO.checkAssignedTlToShop(saleVO);
             req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP);
             req.setAttribute("objShopSalesList",objList);
             forwardName = "viewshoptosales";
            
       /* }catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);*/
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        return mapping.findForward("login");
      }
    
    }

