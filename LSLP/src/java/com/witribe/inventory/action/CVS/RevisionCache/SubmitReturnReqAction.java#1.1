/*
 * SubmitReturnReqAction.java
 *
 * Created on March 10, 2009, 12:57 PM
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
 * @author SC43278
 */
public class SubmitReturnReqAction extends BaseAction {
    
    /** Creates a new instance of SubmitReturnReqAction */
    public SubmitReturnReqAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        RaiseRequestForm dform= (RaiseRequestForm)form; 
         HttpSession userSession =  (HttpSession)req.getSession(false);
         String SalesPersonId = (String)userSession.getAttribute(WitribeConstants.SALES_ID);
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            List statusList = returnRequest(dform,req,SalesPersonId); 
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.RAISE_REQUEST);
            req.setAttribute("statusList",statusList);
            return mapping.findForward("success");
            
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
    List returnRequest(RaiseRequestForm dform,HttpServletRequest req,String SalesPersonId)throws SQLException,Exception{
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        RaiseRequestVO objInventoryVO = new RaiseRequestVO();
        objInventoryVO.setInventorytype(dform.getInventorytype());
        objInventoryVO.setInventoryIdArray(dform.getInventoryIdArray());
        objInventoryVO.setShopId(dform.getShopId());
        objInventoryVO.setSubtype(dform.getSubtype());
        //objInventoryVO.setInventoryIdArray(dform.getInventoryId());
        objInventoryVO.setSalesId(dform.getSalesId());
        objInventoryVO.setToShop(dform.getToShop());
        objInventoryVO.setChangeStatus(dform.getChangeStatus());
        WitribeInventoryBO objBO= new WitribeInventoryBO();
        List statusList = objBO.ReturnRequest(objInventoryVO,SalesPersonId);
        return statusList;
         
        
    }
}
