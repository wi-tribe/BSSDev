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
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
import java.sql.SQLException;
import java.util.List;
public class SubmitLeadAction extends BaseAction{
    
    /** Creates a new instance of SubmitLeadAction */
    public SubmitLeadAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        LeadEntryForm lform= (LeadEntryForm)form; 
        List statusList = null;
        String cse = null;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.LEAD_MANAGEMENT);
        String salesId = (String)req.getSession().getAttribute(WitribeConstants.SALES_ID);
        LeadVO duplicateLeadVo = null;
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(req.getSession().getAttribute(WitribeConstants.ROLE).equals(WitribeConstants.TYPE_CSE))
            cse=(String)req.getSession().getAttribute(WitribeConstants.SALES_ID);
            //WitribeBO wbo = new WitribeBO();
            duplicateLeadVo = checkDuplicateLead(lform);
            if(duplicateLeadVo == null){
            statusList = createLead(lform,cse,salesId);
            } else if(duplicateLeadVo.getSalesId()!= null && duplicateLeadVo.getSalesId() != " " && !("-1".equalsIgnoreCase(duplicateLeadVo.getSalesId()))){
                req.setAttribute(WitribeConstants.ERROR_STRING,"Lead ("+duplicateLeadVo.getLeadId()+") has already been captured on  ("+duplicateLeadVo.getCreateDate()+") by (created by "+duplicateLeadVo.getSalesId()+"-"+duplicateLeadVo.getFullname()+").");
                return mapping.findForward("duplicate") ;
                } else if("-1".equalsIgnoreCase(duplicateLeadVo.getSalesId())){
                    req.setAttribute(WitribeConstants.ERROR_STRING,"Lead ("+duplicateLeadVo.getLeadId()+") has already been captured on  ("+duplicateLeadVo.getCreateDate()+") by (created by "+duplicateLeadVo.getSalesId()+").");
                    return mapping.findForward("duplicate") ;    
                } else {
               //req.setAttribute(WitribeConstants.ERROR_STRING,"Lead ("+duplicateLeadVo.getLeadId()+") has already been captured on  ("+duplicateLeadVo.getCreateDate()+") by (created by "+duplicateLeadVo.getSalesId()+"-"+duplicateLeadVo.getFullname()+").");
                req.setAttribute(WitribeConstants.ERROR_STRING,"Lead ("+duplicateLeadVo.getLeadId()+") has already been captured on  ("+duplicateLeadVo.getCreateDate()+").");
                return mapping.findForward("duplicate") ; 
            }
            req.setAttribute("statusList",statusList);
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CREATE_LEAD);
            return mapping.findForward("success");
           
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
     
    }
    
    List createLead(LeadEntryForm lform, String cse,String salesId)throws SQLException,Exception{
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setBroadbandtype(lform.getBroadbandtype());
        objLeadVO.setCNICid(lform.getCNICid());
        objLeadVO.setChanneltype(lform.getChanneltype());
        objLeadVO.setContactnumber(lform.getContactnumber());
        objLeadVO.setWillingPay(lform.getDialup());
        objLeadVO.setEmail(lform.getEmail());
        objLeadVO.setFirstname(lform.getFirstname());
        objLeadVO.setJobtitle(lform.getJobtitle());
        objLeadVO.setLastname(lform.getLastname());
        objLeadVO.setLeadsource(lform.getLeadsource());
        objLeadVO.setMonthlyspend(lform.getMonthlyspend());
        objLeadVO.setNameofISP(lform.getNameofISP());
        //objLeadVO.setReqISP(lform.getReqISP());
        objLeadVO.setOccupation(lform.getOccupation());
        objLeadVO.setPackageinfo(lform.getPackageinfo());
        objLeadVO.setPriority(lform.getPriority());
        objLeadVO.setQuery(lform.getQuery());
        objLeadVO.setSalutation(lform.getSalutation());
        objLeadVO.setServicetype(lform.getServicetype());
        objLeadVO.setCurrentSpeed(lform.getSpeed());
        //objLeadVO.setPlot(lform.getPlot());
        //objLeadVO.setStreet(lform.getStreet());
        objLeadVO.setSubzone(lform.getSubzone());
        objLeadVO.setOthersubzone(lform.getOthersubzone());  
        objLeadVO.setZone(lform.getZone());
        objLeadVO.setOtherzone(lform.getOtherzone());
        objLeadVO.setCity(lform.getCity());
        objLeadVO.setProvince(lform.getProvince());
        objLeadVO.setCountry(lform.getCountry());
        objLeadVO.setZip(lform.getZip());
        objLeadVO.setVolumelimit(lform.getVolumelimit());
        objLeadVO.setLeadaddress(lform.getLeadaddress());
        objLeadVO.setReasonId(lform.getReasonId());
        objLeadVO.setTransitionState(lform.getTransitionState());
        objLeadVO.setReasonCode(lform.getReasonCode());
        objLeadVO.setReasonComment("Created");
        objLeadVO.setReferredBy(lform.getReferredBy());
        WitribeBO objBO= new WitribeBO();
        List status = objBO.createLead(objLeadVO,cse,salesId);                   
        return status;
      //return true;
        
    }
    LeadVO checkDuplicateLead(LeadEntryForm lform)throws SQLException,Exception{
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setBroadbandtype(lform.getBroadbandtype());
        objLeadVO.setCNICid(lform.getCNICid());
        objLeadVO.setChanneltype(lform.getChanneltype());
        objLeadVO.setContactnumber(lform.getContactnumber());
        objLeadVO.setWillingPay(lform.getDialup());
        objLeadVO.setEmail(lform.getEmail());
        objLeadVO.setFirstname(lform.getFirstname());
        objLeadVO.setJobtitle(lform.getJobtitle());
        objLeadVO.setLastname(lform.getLastname());
        objLeadVO.setLeadsource(lform.getLeadsource());
        objLeadVO.setMonthlyspend(lform.getMonthlyspend());
        objLeadVO.setNameofISP(lform.getNameofISP());
        //objLeadVO.setReqISP(lform.getReqISP());
        objLeadVO.setOccupation(lform.getOccupation());
        objLeadVO.setPackageinfo(lform.getPackageinfo());
        objLeadVO.setPriority(lform.getPriority());
        objLeadVO.setQuery(lform.getQuery());
        objLeadVO.setSalutation(lform.getSalutation());
        objLeadVO.setServicetype(lform.getServicetype());
        objLeadVO.setCurrentSpeed(lform.getSpeed());
        //objLeadVO.setPlot(lform.getPlot());
        //objLeadVO.setStreet(lform.getStreet());
        objLeadVO.setSubzone(lform.getSubzone());
        objLeadVO.setOthersubzone(lform.getOthersubzone());  
        objLeadVO.setZone(lform.getZone());
        objLeadVO.setOtherzone(lform.getOtherzone());
        objLeadVO.setCity(lform.getCity());
        objLeadVO.setProvince(lform.getProvince());
        objLeadVO.setCountry(lform.getCountry());
        objLeadVO.setZip(lform.getZip());
        objLeadVO.setVolumelimit(lform.getVolumelimit());
        objLeadVO.setLeadaddress(lform.getLeadaddress());
        WitribeBO objBO= new WitribeBO();
        LeadVO duplicateLeadVo = objBO.checkDuplicateLead(objLeadVO);                   
        return duplicateLeadVo;
      //return true;
        
    }
    
}
