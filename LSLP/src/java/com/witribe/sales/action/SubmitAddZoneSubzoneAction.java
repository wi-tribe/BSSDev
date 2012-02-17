/*
 * SubmitAddZoneSubzoneAction.java
 *
 * Created on July 15, 2009, 10:41 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.actionform.AddressMappingForm;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ChannelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ChannelVO;
import com.witribe.util.LogUtil;
import com.witribe.vo.AddressMappingVO;
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
public class SubmitAddZoneSubzoneAction extends BaseAction{
    
    /** Creates a new instance of SubmitAddZoneSubzoneAction */
    public SubmitAddZoneSubzoneAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        AddressMappingForm addrform= (AddressMappingForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            String newzone = addrform.getNewzone();
            String newsubzone = addrform.getNewsubzone();
            if("".equalsIgnoreCase(newzone) && "".equalsIgnoreCase(newsubzone)){
                req.setAttribute(WitribeConstants.ERROR_STRING,"Address Already Exist");
              return mapping.findForward("duplicate");  
            } else {
            if(!checkDuplicate(addrform)){
            if(createAddressMapping(addrform,req)){
            req.setAttribute(WitribeConstants.HEADING,"Add New Zone and Subzone");
            return mapping.findForward("success");
            } else {
                return mapping.findForward("failure");
            }
            } else {
                req.setAttribute(WitribeConstants.ERROR_STRING,"Address Already Exist");
                return mapping.findForward("duplicate");
            }
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
       
     }
    boolean checkDuplicate(AddressMappingForm addrform)throws SQLException,Exception{
        AddressMappingVO addressVo = new AddressMappingVO();
        addressVo.setCountry(addrform.getCountry());
        addressVo.setProvince(addrform.getProvince());
        addressVo.setCity(addrform.getCity());
        addressVo.setZone(addrform.getZone());
        addressVo.setSubzone(addrform.getSubzone());
        addressVo.setNewprovince(addrform.getNewprovince());
        addressVo.setNewcity(addrform.getNewcity());
        addressVo.setNewzone(addrform.getNewzone());
        addressVo.setNewsubzone(addrform.getNewsubzone());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.checkDuplicate(addressVo);
        return status;
         
        
    }
    boolean createAddressMapping(AddressMappingForm addrform,HttpServletRequest req)throws SQLException,Exception{
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        AddressMappingVO addressVo = new AddressMappingVO();
        addressVo.setCountry(addrform.getCountry());
        addressVo.setProvince(addrform.getProvince());
        addressVo.setCity(addrform.getCity());
        addressVo.setZone(addrform.getZone());
        addressVo.setSubzone(addrform.getSubzone());
        addressVo.setNewprovince(addrform.getNewprovince());
        addressVo.setNewcity(addrform.getNewcity());
        addressVo.setNewzone(addrform.getNewzone());
        addressVo.setNewsubzone(addrform.getNewsubzone());
        WitribeSalesBO objBO= new WitribeSalesBO();
        boolean status = objBO.createAddressMapping(addressVo);
        return status;
         
        
    }
}
