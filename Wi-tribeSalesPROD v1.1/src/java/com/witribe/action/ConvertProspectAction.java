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
import com.witribe.vo.ProspectVO;
import java.sql.SQLException;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
public class ConvertProspectAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public ConvertProspectAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        LeadEntryForm lform= (LeadEntryForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(createProspect(lform,req)) {
                req.setAttribute("heading",WitribeConstants.CONVERTED_TO_PROSPECT);
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_CREATING_PROSPECT);
       return mapping.findForward("failure") ;
    }
    boolean createProspect(LeadEntryForm lform,HttpServletRequest req)throws SQLException,Exception{
        ProspectVO objProspectVO = new ProspectVO();
        objProspectVO.setLeadId(lform.getLeadId());
        objProspectVO.setReasonId(lform.getReasonId());
        objProspectVO.setReasonCode(lform.getReasonCode());
        objProspectVO.setTransitionState(lform.getTransitionState());
        objProspectVO.setReasonComment(lform.getReasonComment());
        /*if("other".equalsIgnoreCase(objProspectVO.getReason())) {
            objProspectVO.setReason(req.getParameter("reason1"));
        }*/
        WitribeBO objBO= new WitribeBO();
        boolean status = objBO.createProspect(objProspectVO);
        return status;
          
    }
    
}
