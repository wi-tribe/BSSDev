/*
 * SubmitLeadAction.java
 *
 * Created on January 7, 2009, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

/**
 *
 * @author SC43278
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import com.witribe.vo.LeadVO;
import java.sql.SQLException;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
public class ConvertAccountAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public ConvertAccountAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        LeadEntryForm lform= (LeadEntryForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
        try{
            if(!checkGuest(req,res)) {
                return mapping.findForward("login");
            }
            if(updateAccountInfo(req,lform)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CONVERTED_TO_ACCOUNT);
                req.setAttribute(WitribeConstants.SC_REPLY_MESSAGE,req.getParameter(WitribeConstants.SC_REPLY_MESSAGE));
                return mapping.findForward("success");
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CONVERTED_TO_ACCOUNT);
       req.setAttribute(WitribeConstants.SC_REPLY_MESSAGE,req.getParameter(WitribeConstants.SC_REPLY_MESSAGE));
       
       return mapping.findForward("success") ;
    }
    boolean updateAccountInfo(HttpServletRequest req,LeadEntryForm lform)throws SQLException,Exception{
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setReasonCode(WitribeConstants.JOB_STATUS_INITIATED);
        objLeadVO.setTransitionState(WitribeConstants.LEAD_TAG_INITIATED);
        //objLeadVO.setReasonComment("Account Number"++"Created");
        boolean status = false;
        String leadId = req.getParameter(WitribeConstants.SC_LEAD_ID);
        String accountNo = req.getParameter(WitribeConstants.SC_ACCOUNT_NO);
        String salesId = req.getParameter(WitribeConstants.SC_SALES_REP_ID);
        String CnicId = req.getParameter(WitribeConstants.SC_CNIC);
        String acctType = req.getParameter(WitribeConstants.SC_ACCOUNT_TYPE);
        String orderId = req.getParameter(WitribeConstants.SC_ORDER_ID);
        
        if(leadId != null &&
                accountNo !=null
                && salesId != null
                && CnicId != null
                && acctType != null
                && orderId != null
                ) {
            if(leadId != "") {
                objLeadVO.setLeadId(leadId);
                objLeadVO.setAccountNo(accountNo);
                objLeadVO.setSalesId(salesId);
                objLeadVO.setCNICid(CnicId);
                objLeadVO.setAcctType(acctType);
                objLeadVO.setOrderId(orderId);
                WitribeBO objBO= new WitribeBO();
                
                status = objBO.updateAccountInfo(objLeadVO);
            }
        }
        return status;
          
    }
    
}
