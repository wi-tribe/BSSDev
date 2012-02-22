/*
 * DeleteShopHierarchyAction.java
 *
 * Created on April 6, 2009, 6:49 PM
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
public class DeleteShopHierarchyAction extends BaseAction{
    
    /** Creates a new instance of DeleteShopHierarchyAction */
    public DeleteShopHierarchyAction() {
    }
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest req, HttpServletResponse res ){
        ShopDetailsForm sform = (ShopDetailsForm)form;
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS_HIERARCHY);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(deleteShopHierarchybySubshop(sform,req)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SALES_HIERARCHY_DELETED);
                return mapping.findForward("success");
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
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.DELETE_SALES_HIERARCHY);
        return mapping.findForward("success");
    }
    
    boolean deleteShopHierarchybySubshop(ShopDetailsForm sform, HttpServletRequest req)throws SQLException,Exception{
        ShopsVO objShopsVO = new ShopsVO();
        String[] check=req.getParameterValues("check");
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.deleteShopHierarchybySubshop(objShopsVO, check);
        return status;
    }
}
