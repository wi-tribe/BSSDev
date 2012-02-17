/*
 * UpdateSalesHierarchyAction.java
 *
 * Created on February 10, 2009, 6:35 PM
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
public class UpdateSalesHierarchyAction extends BaseAction{
    
    /** Creates a new instance of UpdateSalesHierarchyAction */
    public UpdateSalesHierarchyAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws SQLException, Exception{
        SalesHierarchyForm sform = (SalesHierarchyForm)form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_HIERARCHY);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");                
            }
            WitribeSalesBO wsbo = new WitribeSalesBO();
            SalesHierarchyVO salesobj = new SalesHierarchyVO();
            salesobj.setChild_salespersonnel_id(sform.getChild_salespersonnel_id());
            salesobj.setParent_salespersonnel_id(sform.getParent_salespersonnel_id());
            if(wsbo.updateSalesHierarchybyChild(salesobj))
            {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.MODIFY_SALES_HIERARCHY);
                return mapping.findForward("success");
            }
            
        }catch(Exception e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL);
            req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG);
            return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_MODIFY_SALES_HIERARCHY);
        return mapping.findForward("failure");
    }
}
