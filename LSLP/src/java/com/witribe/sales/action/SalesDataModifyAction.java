/*
 * SalesDataModifyAction.java
 *
 * Created on February 4, 2009, 1:16 PM
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SC43278
 */
public class SalesDataModifyAction extends BaseAction {
    
    /** Creates a new instance of SalesDataModifyAction */
    public SalesDataModifyAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        SalesPersonnelForm sform= (SalesPersonnelForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(modifySalesPerson(sform)) {
                if(sform.getUserId() == null) {
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.MODIFY_SALES_PERSONNEL);
                    return mapping.findForward("success");
                } else {
                    req.setAttribute(WitribeConstants.INFO_VAR,WitribeConstants.MSG_PROFILE_SUC); 
                    return mapping.findForward("login");
                }
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_MODIFY_SALES_PERSONNEL_HEAD);
       req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.FAILED_MODIFY_SALES_PERSONNEL); 
       return mapping.findForward("failure") ;
    }
    boolean modifySalesPerson(SalesPersonnelForm sform)throws SQLException,Exception{
        SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        objSalesVO.setSalesId(sform.getSalesId());
        objSalesVO.setSalestype(sform.getSalestype());
        objSalesVO.setFirstname(sform.getFirstname());
        objSalesVO.setLastname(sform.getLastname());
        objSalesVO.setContactnumber(sform.getContactnumber());
        objSalesVO.setEmail(sform.getEmail());
        objSalesVO.setUserId(sform.getUserId());
        objSalesVO.setPassword(sform.getPassword());
        objSalesVO.setPlot(sform.getPlot());
        objSalesVO.setStreet(sform.getStreet());
        objSalesVO.setSubzone(sform.getSubzone());
        objSalesVO.setZone(sform.getZone());
        objSalesVO.setCity(sform.getCity());
        objSalesVO.setProvince(sform.getProvince());
        objSalesVO.setCountry(sform.getCountry());
        objSalesVO.setZip(sform.getZip());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.modifySalesPersonnel(objSalesVO);
        return status;
          
    }
}
