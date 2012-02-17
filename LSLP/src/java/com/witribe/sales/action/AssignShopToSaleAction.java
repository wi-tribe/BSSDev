/*
 * AssignShopToSaleAction.java
 *
 * Created on February 12, 2009, 4:34 PM
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
public class AssignShopToSaleAction extends BaseAction{
    
    /** Creates a new instance of AssignShopToSaleAction */
    public AssignShopToSaleAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        ShopSaleMappingForm ssform= (ShopSaleMappingForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.ASSOCIATE_TL_SHOP);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
        }catch(Exception e) {
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP);
        
        try{
        if(assignShop(ssform,req)){
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.TL_TO_SHOP);
        return mapping.findForward("success");
        }
        }    catch(SQLException e) {
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_TL_TO_SHOP);
       return mapping.findForward("Failure") ;
    }
    
     boolean assignShop(ShopSaleMappingForm ssform, HttpServletRequest req)throws SQLException, Exception{
         WitribeSalesBO objSalesBO = new WitribeSalesBO();
         ShopSaleVO objSalesVO = new ShopSaleVO();
         /*objSalesVO.setParent_salespersonnel_id(req.getParameter("parentId"));
         objSalesVO.setChild_salespersonnel_id(req.getParameter("childId"));*/
         objSalesVO.setShop_id(ssform.getShop_id());
         objSalesVO.setSalespersonnel_id(ssform.getSalespersonnel_id());
         boolean status = objSalesBO.assignShop(objSalesVO);
         return true;
     }
}
