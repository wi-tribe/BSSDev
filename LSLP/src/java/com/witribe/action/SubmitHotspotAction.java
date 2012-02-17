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
import com.witribe.actionform.HotspotForm;
import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadVO;
import java.util.List;

public class SubmitHotspotAction extends Action{
    
    /** Creates a new instance of SubmitLeadAction */
    public SubmitHotspotAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
     
       HotspotForm hform= (HotspotForm)form; 
       LeadVO duplicateLeadVo = null;
               
       try{
           duplicateLeadVo = checkDuplicateLead(hform);
            if(duplicateLeadVo == null){
            if(registerHotspot(hform)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HOTSPOT_LOGIN_SUCCESS);
                return mapping.findForward("success");
            }
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HOTSPOT_LOGIN_FAILED);
       return mapping.findForward("failure") ;
       
    }
     boolean registerHotspot(HotspotForm hform)throws SQLException,Exception{
         boolean status = false;
         String cse = null;
         String salesId = "";
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setFirstname(hform.getFirstname());
        objLeadVO.setLastname(hform.getLastname());
        objLeadVO.setEmail(hform.getEmail());
        objLeadVO.setContactnumber(hform.getMobile());
        objLeadVO.setUserId(hform.getUserId());
        //objHotspotVO.setPassword(hform.getPassword());
        objLeadVO.setCountry(hform.getCountry());
        objLeadVO.setProvince(hform.getProvince());
        objLeadVO.setCity(hform.getCity());
        //objLeadVO.setReasonCode("Within AOI not Covered");
        objLeadVO.setTransitionState("Prospect");
        
        
        objLeadVO.setBroadbandtype("");
        objLeadVO.setCNICid("");
        objLeadVO.setChanneltype("");
        objLeadVO.setWillingPay("0");
        objLeadVO.setJobtitle("");
        objLeadVO.setLeadsource("");
        objLeadVO.setMonthlyspend("");
        objLeadVO.setNameofISP("");
        //objLeadVO.setReqISP(lform.getReqISP());
        objLeadVO.setOccupation("");
        objLeadVO.setPackageinfo("");
        objLeadVO.setPriority("");
        objLeadVO.setQuery("");
        objLeadVO.setSalutation("");
        objLeadVO.setServicetype("");
        objLeadVO.setCurrentSpeed("");
        //objLeadVO.setPlot(lform.getPlot());
        //objLeadVO.setStreet(lform.getStreet());
        objLeadVO.setSubzone("");
        objLeadVO.setOthersubzone("");  
        objLeadVO.setZone("");
        objLeadVO.setOtherzone("");
        objLeadVO.setZip("");
        objLeadVO.setVolumelimit("");
        objLeadVO.setLeadaddress("");
        objLeadVO.setReasonId(5);
        objLeadVO.setReasonComment("Hotspot Lead Created");
        WitribeBO objBO= new WitribeBO();
        List statusList = objBO.createLead(objLeadVO,cse,salesId); 
        if(statusList == null){
        status = true;
        } else {
            status = false;
        }
      return status;
        
    }
     
   LeadVO checkDuplicateLead(HotspotForm hform)throws SQLException,Exception{
          
        LeadVO objLeadVO = new LeadVO();
        objLeadVO.setContactnumber(hform.getMobile());
        objLeadVO.setEmail(hform.getEmail());
        WitribeBO objBO= new WitribeBO();
        LeadVO duplicateLeadVo = objBO.checkDuplicateLead(objLeadVO);                   
        return duplicateLeadVo;
      //return true;
        
    }
    
    
}
