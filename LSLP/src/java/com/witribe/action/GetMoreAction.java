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
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetMoreAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public GetMoreAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        LeadEntryForm lform= (LeadEntryForm)form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_ORDER_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            WitribeBO objBO= new WitribeBO();
            LeadVO objLeadVO = new LeadVO();
            objLeadVO.setLeadId(req.getParameter("leadId"));
            objLeadVO.setPlot(req.getParameter("plot"));
            objLeadVO.setStreet(req.getParameter("street"));
            objLeadVO.setSubzone("-");
            objLeadVO.setZone(req.getParameter("zone"));
            objLeadVO.setCity(req.getParameter("city"));
            objLeadVO.setProvince(req.getParameter("state"));
            objBO.updateLeadAddress(objLeadVO);
           // LeadVO objLeadVO = objBO.getLead(lform.getLeadId());
            objLeadVO = objBO.getLead(req.getParameter("leadId"));
            objLeadVO.setPlot(req.getParameter("plot"));
            objLeadVO.setStreet(req.getParameter("street"));
            objLeadVO.setSubzone("-");
            objLeadVO.setZone(req.getParameter("zone"));
            objLeadVO.setCity(req.getParameter("city"));
            objLeadVO.setProvince(req.getParameter("state"));
            objLeadVO.setReferredBy(req.getParameter("referredBy"));
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_LEAD);
            req.setAttribute("objLead",objLeadVO);
            req.setAttribute(WitribeConstants.COVERAGE,req.getParameter("coverage_type"));
            req.setAttribute(WitribeConstants.SLA,req.getParameter("sla"));
            req.setAttribute(WitribeConstants.LONGITUDE,req.getParameter("longitude"));
            req.setAttribute(WitribeConstants.LATITUDE,req.getParameter("latitude"));
            
            
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
         req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ENTER_MORE_INFO);
         
         return mapping.findForward("success"); 
           }
    
    
}
