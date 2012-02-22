/*
 * SubmitForgotPasswordAction.java
 *
 * Created on May 13, 2009, 11:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

import com.witribe.actionform.LoginForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.LogUtil;
import com.witribe.util.MailUtil;
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
public class SubmitForgotPasswordAction extends BaseAction {
    
    /** Creates a new instance of SubmitForgotPasswordAction */
    public SubmitForgotPasswordAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LoginForm lform= (LoginForm)form; 
        try{ 
            
            SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
            WitribeBO objBO= new WitribeBO();
            objSalesVO.setUserId(lform.getUsername());
            //objSalesVO.setPassword(lform.getPassword());
            objSalesVO = objBO.validateForgotpwd(objSalesVO);
            String role = objSalesVO.getSalestype();
            if(role != null) {
                HttpSession userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("breadcrumb",WitribeConstants.BREADCRUMB);
                userSession.setAttribute("username",lform.getUsername());
                userSession.setAttribute("password",objSalesVO.getPassword());
                userSession.setAttribute("name",objSalesVO.getFullname());
                userSession.setAttribute(WitribeConstants.ROLE,role);
                userSession.setAttribute(WitribeConstants.SALES_ID,objSalesVO.getSalesId());
                userSession.setAttribute(WitribeConstants.ADDR_PLOT,objSalesVO.getPlot());
                userSession.setAttribute(WitribeConstants.ADDR_STREET,objSalesVO.getStreet());
                userSession.setAttribute(WitribeConstants.ADDR_SUBZONE,objSalesVO.getSubzone());
                userSession.setAttribute(WitribeConstants.ADDR_ZONE,objSalesVO.getZone());
                userSession.setAttribute(WitribeConstants.ADDR_CITY,objSalesVO.getCity());
                userSession.setAttribute(WitribeConstants.ADDR_PROVINCE,objSalesVO.getProvince());
                userSession.setAttribute(WitribeConstants.ADDR_COUNTRY,objSalesVO.getCountry());
                //userSession.setAttribute(WitribeConstants.ADDR_EMAIL,objSalesVO.getEmail());
                userSession.setAttribute("emailid",objSalesVO.getEmail());
                //userSession.setAttribute(WitribeConstants.IS_NSM_AVAILABLE,String.valueOf(objSalesVO.isNsmAvailable()));
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FORGOT_PASSWORD);
                /*if(role.equals(WitribeConstants.TYPE_ADMIN)) {
                     return mapping.findForward("successAdmin");
                }
                else if(role.equals(WitribeConstants.TYPE_CSR)){
                    return mapping.findForward("successCsr");
                }*/
                List statusList = new ArrayList();
                try {
                WitribeBO bo = new WitribeBO();    
                bo.sendmail("satyam@wi-tribe.pk",objSalesVO.getEmail(),WitribeConstants.MAIL_SUBJECT_PWD,"Your Password for Sales Portal is     :"+objSalesVO.getPassword()+WitribeConstants.MAIL_FROM);
                
                 } catch (Exception e) {
                     LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
                     statusList.add(WitribeConstants.MAIL_FAILED);
                }
                System.out.print("Mail sent to"+objSalesVO.getEmail());
                //req.setAttribute("statusString",statusList);
                req.setAttribute(WitribeConstants.ERROR_STRING,"Password sent to "+objSalesVO.getEmail());
                return mapping.findForward("success");


            }
        }catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_LOGIN);
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_LOGIN);
        }
               
         req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.USERNAME_CHECK);
         return mapping.findForward("failure");
       
    }
}
