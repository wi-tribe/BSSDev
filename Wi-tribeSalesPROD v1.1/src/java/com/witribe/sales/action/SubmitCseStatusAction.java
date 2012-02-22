/*
 * SubmitCseStatusAction.java
 *
 * Created on September 16, 2009, 7:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;


import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.SalesPersonnelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.SalesPersonnelVO;
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
 * @author SS73096
 */
public class SubmitCseStatusAction extends BaseAction{
    
    /** Creates a new instance of SubmitCseStatusAction */
    public SubmitCseStatusAction() {
    
    }
 public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {    
     SalesPersonnelForm sform= (SalesPersonnelForm)form;
     String csestatus = sform.getCseStatus();
     try{
                                      
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
         if("Active".equalsIgnoreCase(csestatus)){
            if(activateSalesPersonnel(sform,req)) {
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_CSE_STATUS);
                    return mapping.findForward("success");
                } else {
                     req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_ACTIVATE_CSE);
                     req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_ACTIVATE_CSE);
                     return mapping.findForward("failure");
                }
        }else if("Inactive".equalsIgnoreCase(csestatus)){
                if(inactivateSalesPersonnel(sform,req)) {
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_CSE_STATUS);
                    return mapping.findForward("success");
                } else {
                     req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_INACTIVATE_CSE);
                     req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_INACTIVATE_CSE);
                     return mapping.findForward("failure");
                }
        }else if("Closed".equalsIgnoreCase(csestatus)){
               if(closeSalesPersonnel(sform,req)) {
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_CSE_STATUS);
                    return mapping.findForward("success");
                } else {
                     req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_CLOSE_CSE);
                     req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_FAILED_CLOSE_CSE);
                     return mapping.findForward("failure");
                }  
        }  
                   
      }catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
     req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_CSE_STATUS);
     return mapping.findForward("success");
}
 

   boolean activateSalesPersonnel(SalesPersonnelForm sform,HttpServletRequest req)throws SQLException,Exception{
        SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        String[] check=req.getParameterValues("check");
        objSalesVO.setSalesId(sform.getSalesId());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.activateSalesPersonnel(objSalesVO,check);
        return status;          
    }
           
   boolean inactivateSalesPersonnel(SalesPersonnelForm sform,HttpServletRequest req)throws SQLException,Exception{
        SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        String[] check=req.getParameterValues("check");
        objSalesVO.setSalesId(sform.getSalesId());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.inactivateSalesPersonnel(objSalesVO,check);
        return status;
          
    }
    boolean closeSalesPersonnel(SalesPersonnelForm sform,HttpServletRequest req)throws SQLException,Exception{
        SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        String[] check=req.getParameterValues("check");
        objSalesVO.setSalesId(sform.getSalesId());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.closeSalesPersonnel(objSalesVO,check);
        return status;
          
    }  
 
}
