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
import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;

public class AssignCSEAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public AssignCSEAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LeadEntryForm lform= (LeadEntryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_ORDER_MANAGEMENT);
        List statusList = null;
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            statusList = assignSalesPerson(lform,req);
            req.setAttribute("statusList",statusList);
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_SUCCESS);
            return mapping.findForward("success");
            
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass(),e); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }       
     
    }
    
    List assignSalesPerson(LeadEntryForm lform,HttpServletRequest req)throws SQLException,Exception{
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setLeadId(lform.getLeadId());
        objLeadVO.setAssignTo(lform.getAssignedCSE());
        HttpSession userSession =  (HttpSession)req.getSession(false);
        int salesType = Integer.parseInt((String)userSession.getAttribute(WitribeConstants.ROLE));
        salesType++;
        objLeadVO.setSalesType(String.valueOf(salesType));
        WitribeBO objBO= new WitribeBO();
        List status = objBO.assignSalesPerson(objLeadVO);
        return status;
              
    }
}
