/*
 * SalesPersonnelAction.java
 *
 * Created on January 28, 2009, 11:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;
import com.witribe.action.BaseAction;
import com.witribe.sales.actionform.SalesPersonnelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.exception.DuplicateUserException;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.LogUtil;
import java.sql.SQLException;
/**
 *
 * @author SC43278
 */
public class SalesPersonnelAction extends BaseAction {
    
    /** Creates a new instance of SalesPersonnelAction */
    public SalesPersonnelAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        SalesPersonnelForm sform= (SalesPersonnelForm)form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              if(createSale(sform)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CREATE_SALES_PERSONNEL);
                return mapping.findForward("success");
            } else {
                req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.DUPLICATE_ADDRESS);
                return mapping.findForward("duplicate") ;
            }
        }catch(DuplicateUserException e) {
           LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
           req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.USER_NAME+": "+sform.getUserId()+" "+WitribeConstants.ALREADY_EXISTS);
           return mapping.findForward("duplicate") ;
        }    catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               e.printStackTrace();
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
      
    }
    boolean createSale(SalesPersonnelForm sform)throws SQLException,Exception{
        SalesPersonnelVO objsalesVO = new SalesPersonnelVO();
        WitribeSalesBO objBO = new WitribeSalesBO();
        objsalesVO.setSalestype(sform.getSalestype());
       // objsalesVO.setFullname();
        objsalesVO.setChanneltype(sform.getChanneltype());
        objsalesVO.setFirstname(sform.getFirstname());
        objsalesVO.setLastname(sform.getLastname());
        objsalesVO.setContactnumber(sform.getContactnumber());
        objsalesVO.setEmail(sform.getEmail());
        objsalesVO.setUserId(sform.getUserId());
        objsalesVO.setPassword(sform.getPassword());
        objsalesVO.setPlot(sform.getPlot());
        objsalesVO.setStreet(sform.getStreet());
        objsalesVO.setSubzone(sform.getSubzone());
        objsalesVO.setZone(sform.getZone());
        objsalesVO.setCity(sform.getCity());
        objsalesVO.setProvince(sform.getProvince());
        objsalesVO.setCountry(sform.getCountry());
        objsalesVO.setZip(sform.getZip());
        objsalesVO.setCreateDate(sform.getCreateDate());
        objsalesVO.setModifiedDate(sform.getModifiedDate());
        
        boolean status = objBO.createSale(objsalesVO);
        return status;
    
}
}
