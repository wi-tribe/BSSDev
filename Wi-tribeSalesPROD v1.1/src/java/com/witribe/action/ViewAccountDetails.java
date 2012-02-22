/*
 * ViewAccountDetails.java
 *
 * Created on September 30, 2009, 12:14 PM
 */

package com.witribe.action;


import com.portal.pcm.EBufException;
import com.witribe.dao.AccountInfoClass;
import com.witribe.actionform.LeadEntryForm;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadAccntVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
/**
 *
 * @author BS68483
 * @version
 */

public class ViewAccountDetails extends BaseAction {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
     public ViewAccountDetails() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws EBufException ,Exception{
        LeadEntryForm leadform= (LeadEntryForm) form;
        
        LeadAccntVO obj = new LeadAccntVO();
        AccountInfoClass accnt = new AccountInfoClass();
        String searchByValue =leadform.getSearchByValue();
        String searchby= leadform.getSearchby();
        //req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
         //HttpSession userSession =  (HttpSession)req.getSession(false);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
           if(searchByValue != null)
           {
            
            obj=accnt.loaddata(searchby,searchByValue);
            //System.out.println(obj.getBillAmount());
            if(obj == null){
            req.setAttribute("objleads",obj);
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.MSG_CHECK_ACCOUNT);
            } else {
            req.setAttribute("objleads",obj);
            }
           }
          //System.out.println("hiii"+req.getAttribute("objleads"));
        }catch(EBufException e)
        {
            LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ACCOUNT_DETAILS);
        return mapping.findForward("viewAccount");
       
    }
}
