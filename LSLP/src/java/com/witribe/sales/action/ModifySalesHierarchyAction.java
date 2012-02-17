/*
 * ModifySalesHierarchyAction.java
 *
 * Created on February 10, 2009, 3:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.SalesHierarchyForm;
import com.witribe.util.LogUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author HY27465
 */
public class ModifySalesHierarchyAction extends BaseAction{
    
    /** Creates a new instance of ModifySalesHierarchyAction */
    public ModifySalesHierarchyAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res){
        SalesHierarchyForm sform = (SalesHierarchyForm)form;
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_HIERARCHY);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");                
            }
           /* String forwardName = "";
               WitribeSalesBO objBO= new WitribeSalesBO();
               String[] check=req.getParameterValues("check");
            if(check != null && check.length > 0) {
                //  SalesPersonnelVO objSalesVO = objBO.getSale(check[0]);
                  req.setAttribute("heading","EDIT SALES HIERARCHY");
            //      req.setAttribute("objSale",objSalesVO);
                  forwardName = "showsale";
            }*/
            
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.MODIFY_SALES_HIERARCHY);
        return mapping.findForward("success");
    }
    
}
