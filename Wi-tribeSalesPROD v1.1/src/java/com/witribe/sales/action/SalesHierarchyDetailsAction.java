/*
 * SalesHierarchyDetailsAction.java
 *
 * Created on February 6, 2009, 7:18 PM
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
public class SalesHierarchyDetailsAction extends BaseAction{
    
    /**
     * Creates a new instance of SalesHierarchyDetailsAction
     */
    public SalesHierarchyDetailsAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        SalesHierarchyForm salesform= (SalesHierarchyForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_HIERARCHY);
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
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SALES_HIERARCHY_HEADING);
        
        try{
        if(createHierarchy(salesform,req)){
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SALES_HIERARCHY_HEADING);
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_CREATE_SALES_HIERARCHY);
       return mapping.findForward("Failure") ;
    }
    
     boolean createHierarchy(SalesHierarchyForm salesform, HttpServletRequest req)throws SQLException, Exception{
         WitribeSalesBO objSalesBO = new WitribeSalesBO();
         SalesHierarchyVO objSalesVO = new SalesHierarchyVO();
         /*objSalesVO.setParent_salespersonnel_id(req.getParameter("parentId"));
         objSalesVO.setChild_salespersonnel_id(req.getParameter("childId"));*/
         objSalesVO.setParent_salespersonnel_id(salesform.getParent_salespersonnel_id());
         objSalesVO.setChild_salespersonnel_id(salesform.getChild_salespersonnel_id());
         objSalesVO.setShop_id(salesform.getShop_id());
         boolean status = objSalesBO.createHierarchy(objSalesVO);
         return true;
     }
}
