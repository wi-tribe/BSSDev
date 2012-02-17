/*
 * DeleteSalesHierarchyAction.java
 *
 * Created on February 9, 2009, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.SalesHierarchyForm;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.sales.vo.SalesHierarchyVO;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.util.LogUtil;

/**
 *
 * @author HY27465
 */
public class DeleteSalesHierarchyAction extends BaseAction  {
    
    /** Creates a new instance of DeleteSalesHierarchyAction */
    public DeleteSalesHierarchyAction() {
    }
    public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest req, HttpServletResponse res ){
        SalesHierarchyForm sform = (SalesHierarchyForm)form;
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_HIERARCHY);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(deleteSalesHierarchybyChild(sform,req)) {
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
    
    boolean deleteSalesHierarchybyChild(SalesHierarchyForm sform, HttpServletRequest req)throws SQLException,Exception{
        SalesHierarchyVO objSalesVO = new SalesHierarchyVO();
        String[] check=req.getParameterValues("check");
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.deleteSalesHierarchybyChild(objSalesVO, check);
        return status;
    }
}
