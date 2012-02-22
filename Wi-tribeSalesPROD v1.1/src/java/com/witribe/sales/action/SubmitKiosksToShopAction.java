/*
 * SubmitKiosksToShopAction.java
 *
 * Created on April 6, 2009, 3:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ShopToSubshopMappingForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ShopSubshopVO;
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
public class SubmitKiosksToShopAction extends BaseAction{
    
    /** Creates a new instance of SubmitKiosksToShopAction */
    public SubmitKiosksToShopAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        ShopToSubshopMappingForm stosubform= (ShopToSubshopMappingForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS_HIERARCHY);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SHOP_HIERARCHY_HEADING);
        
        try{
        if(createKiosksHierarchy(stosubform,req)){
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_KIOSKS);
        return mapping.findForward("success");
        }
        }    catch(SQLException e) {
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_CREATE_SHOP_HIERARCHY);
       return mapping.findForward("Failure") ;
    }
    
     boolean createKiosksHierarchy(ShopToSubshopMappingForm stosubform, HttpServletRequest req)throws SQLException, Exception{
         WitribeSalesBO objSubBO = new WitribeSalesBO();
         ShopSubshopVO objSubshopVO = new ShopSubshopVO();
         /*objSalesVO.setParent_salespersonnel_id(req.getParameter("parentId"));
         objSalesVO.setChild_salespersonnel_id(req.getParameter("childId"));*/
         objSubshopVO.setShopId(stosubform.getShopId());
         objSubshopVO.setSubShopId(stosubform.getSubShopId());
         boolean status = objSubBO.createKiosksHierarchy(objSubshopVO);
         return true;
     }
}
