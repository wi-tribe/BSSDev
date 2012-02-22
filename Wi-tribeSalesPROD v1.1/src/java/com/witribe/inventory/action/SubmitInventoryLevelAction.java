/*
 * SubmitInventoryLevelAction.java
 *
 * Created on October 1, 2009, 9:50 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.util.LogUtil;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author SS73096
 */
public class SubmitInventoryLevelAction extends BaseAction{
    
    /** Creates a new instance of SubmitInventoryLevelAction */
    public SubmitInventoryLevelAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        RaiseRequestForm requestform= (RaiseRequestForm)form; 
        
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
           if(inventoryLevelRequest(requestform,req)){
           req.setAttribute(WitribeConstants.HEADING,WitribeConstants.INVENTORY_LEVEL_SETTING);
           //req.setAttribute("statusList",statusList);
           return mapping.findForward("success");
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
       return mapping.findForward("failure"); 
    }
   boolean inventoryLevelRequest(RaiseRequestForm requestform,HttpServletRequest req)throws SQLException,Exception{
        boolean statusList = false;
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        RaiseRequestVO objrequestVO = new RaiseRequestVO();
        objrequestVO.setInventorytype(requestform.getInventorytype());
        objrequestVO.setMake(requestform.getMake());
        objrequestVO.setModel(requestform.getModel());
        objrequestVO.setModelcpe(requestform.getModelcpe());
        objrequestVO.setModelrouter(requestform.getModelrouter());
        objrequestVO.setMaxLevel(requestform.getMaxlevel());
        objrequestVO.setMinLevel(requestform.getMinlevel());
        objrequestVO.setShopId(requestform.getShopId());
        objrequestVO.setSubtype(requestform.getSubtype());
        objrequestVO.setSubtypecpe(requestform.getSubtypecpe());
        objrequestVO.setSubtyperouter(requestform.getSubtyperouter());
        WitribeInventoryBO objBO= new WitribeInventoryBO();
        List minmaxvalue = objBO.getMinMaxLevel(objrequestVO);
         if(minmaxvalue != null && minmaxvalue.size() != 0 ){
            statusList = objBO.updateMinMaxLevel(objrequestVO);
         } else {
         statusList = objBO.inventoryLevelRequest(objrequestVO,salesid);
         }
        return statusList;
      //return true;
        
    }
    
}
