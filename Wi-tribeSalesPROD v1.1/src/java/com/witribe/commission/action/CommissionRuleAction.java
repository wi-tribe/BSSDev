/*
 * CommissionRuleAction.java
 *
 * Created on October 27, 2009, 4:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.commission.action;

import com.witribe.action.BaseAction;
import com.witribe.commission.actionform.CommissionPlanForm;
import com.witribe.commission.bo.CommissionBO;
import com.witribe.commission.vo.CommissionPlanVO;
import com.witribe.constants.WitribeConstants;
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
public class CommissionRuleAction extends BaseAction {
    
    /** Creates a new instance of CommissionRuleAction */
    public CommissionRuleAction() {

    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
    CommissionPlanForm cform = (CommissionPlanForm)form;
    req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_COMMISSION);
     HttpSession userSession =  (HttpSession)req.getSession(true);
     String planId = userSession.getAttribute("PlanId").toString();
     String paymentRuleId = null;
     String forwardname = null;
     if(!(cform.getCommissionType().equalsIgnoreCase("Deduction"))){
        paymentRuleId = userSession.getAttribute("paymentRuleId").toString();
     } else {
        paymentRuleId = "-1";  
     }
     try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            CommissionPlanForm comform = (CommissionPlanForm)form;
            String ruleids=req.getParameter("ruleids");
            String rule_detail_ids=req.getParameter("rule_detail_ids");            
            if(rule_detail_ids==null)
            {
               rule_detail_ids=""; 
            }
            
            comform.setRuleId(ruleids);
            comform.setRule_Detail_Id(rule_detail_ids);
            req.setAttribute("CommissionType",userSession.getAttribute("CommissionType").toString());
            if(comform.getCommissionType().equalsIgnoreCase("Deduction")){
                 if(createCommissionRule(comform,planId,paymentRuleId,rule_detail_ids)){
                      forwardname = "success";
                }
            } else {
         if(checkSorting(comform)){
           if(createCommissionRule(comform,planId,paymentRuleId,rule_detail_ids)){
           //req.setAttribute(WitribeConstants.HEADING,WitribeConstants.);
           //req.setAttribute("CommissionType",comform.getCommissionType());
           forwardname = "success";
           }
         } else {
               if(rule_detail_ids.length()>0)
               {
                   req.setAttribute(WitribeConstants.ERROR_STRING,"Eligibility From and To values should be in increasing order,Please follow EditProcess again");
                   forwardname = "paymentrule";     
               }  
               else{
               req.setAttribute(WitribeConstants.ERROR_STRING,"Eligibility From and To values should be in increasing order");
               forwardname = "paymentrule";     
         }      }
            }
            
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
    return mapping.findForward(forwardname);
    }
     boolean checkSorting(CommissionPlanForm comform)throws SQLException,Exception{
         boolean flag = false;
          String[] elgfrom = comform.getEligibilityFrom();
          String[] elgTo = comform.getEligibilityTo();
          for(int i=0;i < elgfrom.length-1; i++){
              if(elgfrom[i] != "" && elgTo[i] != ""){
                  if(elgfrom[i+1] != ""){
                  if((Integer.parseInt(elgfrom[i]) < Integer.parseInt(elgTo[i])) && (Integer.parseInt(elgTo[i]) <= Integer.parseInt(elgfrom[i+1]))){
                      flag = true;
                  } else {
                      flag = false;
                     return flag;
                  }
                  if(Integer.parseInt(elgfrom[i+1])<=Integer.parseInt(elgTo[i]))
                  {
                      flag=false;
                      return flag;
                      
                  }    
                  } else if((Integer.parseInt(elgfrom[i]) < Integer.parseInt(elgTo[i]))){
                      flag = true;
                  } else {
                      flag = false;
                      return flag;
              }
                  
                  } else {
                      flag = false;
                      return flag;
          }
              }
          
          int j = elgfrom.length-1;
          if(elgfrom[j] != "" && elgTo[j] != ""){
          if((Integer.parseInt(elgfrom[j]) < Integer.parseInt(elgTo[j]))){
                      flag = true;
                  } else {
                      flag = false;
                  }
          }
        return flag;
     }
    boolean createCommissionRule(CommissionPlanForm reqform,String planId,String paymentRuleId,String rule_detail_ids)throws SQLException,Exception{
        
       CommissionPlanVO comVO = new CommissionPlanVO();
        CommissionBO objBO= new CommissionBO();
        comVO.setCommissionType(reqform.getCommissionType());
        comVO.setTargetMeasure(reqform.getTargetMeasure());
        comVO.setCommType(reqform.getCommType());
        comVO.setStepId(reqform.getStepId());
        comVO.setEligibilityFrom(reqform.getEligibilityFrom());
        comVO.setEligibilityTo(reqform.getEligibilityTo());
        comVO.setEligibilityTarget(reqform.getEligibilityTarget());
        comVO.setEligibilityMeasure(reqform.getEligibilityMeasure());
        comVO.setPlanId(planId);
        comVO.setPaymentId(paymentRuleId);
        comVO.setEligibilityPeriod(reqform.getEligibilityPeriod());
        comVO.setMinEligibility(reqform.getMinEligibility());
        comVO.setAmount(reqform.getAmount());
        comVO.setEligibilityMeasurePer(reqform.getEligibilityMeasurePer());
        comVO.setRuleId(reqform.getRuleId());
        comVO.setRule_Detail_Id(reqform.getRule_Detail_Id());
        if(rule_detail_ids.length()>0)
        {
            boolean status=objBO.updateCommissionRule(comVO); 
            return status;
        }   
        else
        {
           boolean status=objBO.createCommissionRule(comVO);  
           return status;
        }

            
 
      //return true;
        
    }
}
