/*
 * SubmitDistributeInventoryAction.java
 *
 * Created on February 24, 2009, 6:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.DistributeInventoryForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.DistributeInventoryVO;
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
public class SubmitDistributeInventoryAction extends BaseAction {
    
    /** Creates a new instance of SubmitDistributeInventoryAction */
    public SubmitDistributeInventoryAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        DistributeInventoryForm dform= (DistributeInventoryForm)form; 
        
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(distributeRequest(dform,req)) {
                 req.setAttribute(WitribeConstants.HEADING,WitribeConstants.DISTRIBUTE_INVENTORY);
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAILED_INSERT_DISTRIBUTE_INVENTORY);
       return mapping.findForward("failure") ;
    }
    /*
     * Changed By:PKAasimN
     *
     */
    boolean distributeRequest(DistributeInventoryForm dform,HttpServletRequest req)throws SQLException,Exception{
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        DistributeInventoryVO objInventoryVO = new DistributeInventoryVO();
        objInventoryVO.setInventorytype(dform.getInventorytype());
        objInventoryVO.setPoidArray(dform.getPoidArray());
        objInventoryVO.setShopId(dform.getShopId());
        objInventoryVO.setSubtype(dform.getSubtype());
        objInventoryVO.setInventoryIdArray(dform.getInventoryId());
        objInventoryVO.setSalesId(salesid);
        objInventoryVO.setAssignedTo(dform.getAssignedTo());
        objInventoryVO.setChangeStatus(dform.getChangeStatus());
        WitribeInventoryBO objBO= new WitribeInventoryBO();
         boolean status = objBO.distributeRequest(objInventoryVO);
        return status;
         
        
    }
}
