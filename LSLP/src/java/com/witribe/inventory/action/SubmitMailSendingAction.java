/*
 * SubmitMailSendingAction.java
 *
 * Created on October 22, 2009, 3:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.util.LogUtil;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SC43278
 */
public class SubmitMailSendingAction extends BaseAction {
    
    /** Creates a new instance of SubmitMailSendingAction */
    public SubmitMailSendingAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        RaiseRequestForm dform= (RaiseRequestForm)form; 
        
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(mailSendingRequest(dform,req)) {
                 req.setAttribute(WitribeConstants.HEADING,WitribeConstants.INVENTORY_MAIL);
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_MAIL_SEND);
       return mapping.findForward("failure") ;
    }
    boolean mailSendingRequest(RaiseRequestForm dform,HttpServletRequest req)throws SQLException,Exception{
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        RaiseRequestVO reqVO = new RaiseRequestVO();
        reqVO.setSalesId(salesid);
        reqVO.setMailSending(dform.getMailSending());
        WitribeInventoryBO objBO= new WitribeInventoryBO();
         boolean status = objBO.mailSendingRequest(reqVO);
        return status;
    }
}
