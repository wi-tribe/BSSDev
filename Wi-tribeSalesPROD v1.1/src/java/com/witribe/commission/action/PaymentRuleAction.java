/*
 * PaymentRuleAction.java
 *
 * Created on October 27, 2009, 12:15 PM
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
public class PaymentRuleAction extends BaseAction{
    
    /** Creates a new instance of PaymentRuleAction */
    public PaymentRuleAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
    CommissionPlanForm cform = (CommissionPlanForm)form;
    req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_COMMISSION);
    int count = 0;
    String forwardname = null;
    HttpSession userSession =  (HttpSession)req.getSession(true);
     try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            
            CommissionPlanForm comform = (CommissionPlanForm)form;
            String PlanId=req.getParameter("plan_id");
            String rule_ids=req.getParameter("ruleids");
            if(PlanId==null)
            {
                PlanId="";
            }   
            else
            {    
            comform.setPlanId(PlanId);
            comform.setPaymentId(rule_ids);
            //forwardname = "submit";
            }
            userSession.setAttribute("CommissionType",comform.getCommissionType());
            req.setAttribute("CommissionType",comform.getCommissionType());
            String[] payamt = comform.getPaymentAmount();
//        if(payamt!=null)
//        {    
            for(int i = 0; i < payamt.length;i++){
                if(payamt[i] != ""){
                count = count + Integer.parseInt(payamt[i]);
                }
            }
            if(count == 100){
           if(createPaymentRule(comform,req)){
           //req.setAttribute(WitribeConstants.HEADING,WitribeConstants.);
           
            forwardname = "paymentrule";
          }
            } else {
                req.setAttribute(WitribeConstants.ERROR_STRING,"Payment Amount Should not exceed 100 percentage ");
                forwardname = "submit";
            }
//     }     
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
   return mapping.findForward(forwardname);
    }
    boolean createPaymentRule(CommissionPlanForm reqform,HttpServletRequest req)throws SQLException,Exception{
        
       CommissionPlanVO comVO = new CommissionPlanVO();
        CommissionBO objBO= new CommissionBO();
        comVO.setPlanId(reqform.getPlanId());
        comVO.setPaymentId(reqform.getPaymentId());
        comVO.setDurationMeasure(reqform.getDurationMeasure());
        comVO.setDuration(reqform.getDuration());
        comVO.setPaymentAmount(reqform.getPaymentAmount());
        if(reqform.getPaymentId()=="")
        {    
            boolean status=objBO.createPaymentRule(comVO,req);
            return status;
        }
            boolean status=objBO.updatePaymentRule(comVO,req);
            return status;
      //return true;
        
    }
}
