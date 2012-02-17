/*
 * CommissionPlanAction.java
 *
 * Created on October 23, 2009, 12:38 PM
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
import java.util.ArrayList;
import java.util.List;
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
public class CommissionPlanAction extends BaseAction{
    
    /** Creates a new instance of CommissionPlanAction */
    public CommissionPlanAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
    CommissionPlanForm cform = (CommissionPlanForm)form;
    req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_COMMISSION);
   // List ChannelList = new ArrayList();
    //List CitylList = new ArrayList();
    String forwardname = null;
    HttpSession userSession =  (HttpSession)req.getSession(true);
    try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            CommissionPlanForm comform = (CommissionPlanForm)form;
            if(comform.getCity()==null)
            {
             getCityChannel(req);
            /*CommissionBO objcbo = new CommissionBO();
            ChannelList = objcbo.getChannelList();
            CitylList = objcbo.getCityList();
            req.setAttribute("ChannelList",ChannelList);
            req.setAttribute("CitylList",CitylList);*/
            return mapping.findForward("select"); 
            }
            else
            {
                comform.setZone("All");// Added by Murali   
                String plantype=req.getParameter("plantype");
                if(plantype==null)//-----In case of Update existing Plan....
                {
                   plantype=req.getParameter("plan_type"); 
                }    
                String valid_from=req.getParameter("valid_from");
                String valid_to=req.getParameter("valid_to");
                String PlanId=req.getParameter("plan_id");
                String PlanName=req.getParameter("planname");
                String planstatus=req.getParameter("planstatus");
                String salestype=req.getParameter("salestype");
                if(comform.getSalesType()==null)
                {
                   comform.setSalesType(salestype) ;
                }    
                if(planstatus==null)
                {
                  planstatus="2";
                }    
                comform.setPlanId(PlanId);
                comform.setValidFrom(valid_from);
                comform.setValidTo(valid_to);
                comform.setplanType(plantype);
                comform.setPlanName(PlanName);
                comform.setStatus(planstatus);
                comform.setZone("All");
           if(PlanId.length()>0)
           {    
                boolean statusList = createPlan(comform,req,2);//--------To Update
                 userSession.setAttribute("CommissionType",comform.getCommissionType());
                req.setAttribute("CommissionType",comform.getCommissionType());
               if(comform.getCommissionType().equalsIgnoreCase("Deduction")){
                   forwardname = "paymentrule";
               } else {
                   forwardname = "submit";
               } 
           }
           else
           {
               if(checkDuplicateRule(comform)){     
                    boolean statusList = createPlan(comform,req,1);//---------To Insert
                    userSession.setAttribute("CommissionType",comform.getCommissionType());
                    req.setAttribute("CommissionType",comform.getCommissionType());
                   if(comform.getCommissionType().equalsIgnoreCase("Deduction")){
                       forwardname = "paymentrule";
                   } else {
                       forwardname = "submit";
                   } 
               } else {
               req.setAttribute(WitribeConstants.ERROR_STRING,"Plan with this Commission Rule Type Already Exist");
               getCityChannel(req);
               forwardname = "select";
           }
           }   
           //req.setAttribute(WitribeConstants.HEADING,WitribeConstants.);


           return mapping.findForward(forwardname);
            }
            
            
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
    
    }
  public void getCityChannel(HttpServletRequest req) throws SQLException,Exception
    {
            List ChannelList = new ArrayList();
            List CitylList = new ArrayList();
            CommissionBO objcbo = new CommissionBO();
            ChannelList = objcbo.getChannelList();
            CitylList = objcbo.getCityList();
            req.setAttribute("ChannelList",ChannelList);
            req.setAttribute("CitylList",CitylList);
    }     
    boolean createPlan(CommissionPlanForm reqform,HttpServletRequest req,int opr)throws SQLException,Exception{
        
       CommissionPlanVO comVO = new CommissionPlanVO();
        CommissionBO objBO= new CommissionBO();
        comVO.setProductId(reqform.getProductId());
        comVO.setCity(reqform.getCity());
        comVO.setZone(reqform.getZone());
        comVO.setSalesType(reqform.getSalesType());
        comVO.setProductName(reqform.getProductName());
        comVO.setChannelId(reqform.getChannelId());
        comVO.setChannelType(reqform.getChannelType());
        comVO.setPlanType(reqform.getplanType());
        comVO.setValidFrom(reqform.getValidFrom());
        comVO.setValidTo(reqform.getValidTo());
        comVO.setPlanName(reqform.getPlanName());  
        comVO.setStatus(reqform.getStatus());
        if(opr==2)
        {
           comVO.setPlanId(reqform.getPlanId());
           boolean statusList=objBO.updatePlan(comVO,req);
           return statusList;
        }    
        else
        {    
            boolean statusList=objBO.createPlan(comVO,req);
            return statusList;
        }   
        
      //return true;
        
    }
     boolean checkDuplicateRule(CommissionPlanForm reqform)throws SQLException,Exception{
        
       CommissionPlanVO comVO = new CommissionPlanVO();
        CommissionBO objBO= new CommissionBO();
        comVO.setProductId(reqform.getProductId());
        comVO.setCity(reqform.getCity());
        comVO.setZone(reqform.getZone());
        comVO.setSalesType(reqform.getSalesType());
        comVO.setProductName(reqform.getProductName());
        comVO.setChannelId(reqform.getChannelId());
        comVO.setChannelType(reqform.getChannelType());
        comVO.setCommType(reqform.getCommType());
        comVO.setCommissionType(reqform.getCommissionType());
        comVO.setPlanType(reqform.getplanType());
        boolean status=objBO.checkDuplicateRule(comVO);
        return status;
      //return true;
        
    }

}
