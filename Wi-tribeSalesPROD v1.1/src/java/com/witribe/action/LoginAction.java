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
import com.witribe.util.LogUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.actionform.LoginForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.SalesPersonnelVO;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

public class LoginAction extends Action{
    
    /** Creates a new instance of SubmitLeadAction */
 public LoginAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LoginForm lform= (LoginForm)form; 
        try{ 
            
            SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
            WitribeBO objBO= new WitribeBO();
            objSalesVO.setUserId(lform.getUsername());
            objSalesVO.setPassword(lform.getPassword());
            objSalesVO = objBO.validateUser(objSalesVO);
            String role = objSalesVO.getSalestype();
            String channeltype = objSalesVO.getChanneltype();
            if(role != null) {
                HttpSession userSession =  (HttpSession)req.getSession(true);
                userSession.setAttribute("breadcrumb",WitribeConstants.BREADCRUMB);
                userSession.setAttribute("username",lform.getUsername());
                userSession.setAttribute("name",objSalesVO.getFullname());
                userSession.setAttribute(WitribeConstants.ROLE,role);
                userSession.setAttribute("channelType",channeltype);
                userSession.setAttribute(WitribeConstants.SALES_ID,objSalesVO.getSalesId());
                userSession.setAttribute(WitribeConstants.ADDR_PLOT,objSalesVO.getPlot());
                userSession.setAttribute(WitribeConstants.ADDR_STREET,objSalesVO.getStreet());
                userSession.setAttribute(WitribeConstants.ADDR_SUBZONE,objSalesVO.getSubzone());
                userSession.setAttribute(WitribeConstants.ADDR_ZONE,objSalesVO.getZone());
                userSession.setAttribute(WitribeConstants.ADDR_CITY,objSalesVO.getCity());
                userSession.setAttribute(WitribeConstants.ADDR_PROVINCE,objSalesVO.getProvince());
                userSession.setAttribute(WitribeConstants.ADDR_COUNTRY,objSalesVO.getCountry());
                userSession.setAttribute(WitribeConstants.IS_NSM_AVAILABLE,String.valueOf(objSalesVO.isNsmAvailable()));
                userSession.setAttribute(WitribeConstants.CHANNEL_TYPE,objSalesVO.getChanneltype());
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CREATE_LEAD);
                if(role.equals(WitribeConstants.TYPE_ADMIN)) {
                     return mapping.findForward("successAdmin");
                }
                else if(role.equals(WitribeConstants.TYPE_CSR)){
                    return mapping.findForward("successCsr");
                }
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
               
         req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.USERNAME_PWD_CHECK1);
         return mapping.findForward("failure");
       
    }
    
    
}
