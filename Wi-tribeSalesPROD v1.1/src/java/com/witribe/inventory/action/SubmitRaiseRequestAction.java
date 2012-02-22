/*
 * SubmitRaiseRequestAction.java
 *
 * Created on February 18, 2009, 5:46 PM
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
public class SubmitRaiseRequestAction extends BaseAction{
    
    /** Creates a new instance of SubmitRaiseRequestAction */
    public SubmitRaiseRequestAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        RaiseRequestForm requestform= (RaiseRequestForm)form; 
        
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
           List statusList = createRequest(requestform,req);
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
   List createRequest(RaiseRequestForm requestform,HttpServletRequest req)throws SQLException,Exception{
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        RaiseRequestVO objrequestVO = new RaiseRequestVO();
        objrequestVO.setInventorytype(requestform.getInventorytype());
       /* if(requestform.getMake() == "" || requestform.getMakenew() != null){
           objrequestVO.setMakenew(requestform.getMakenew()); 
        } else {*/
        objrequestVO.setMake(requestform.getMake());
        //}
        
        /*if(requestform.getModel() == "" || requestform.getModelnew() != null){
           objrequestVO.setModelnew(requestform.getModelnew());
        } else {*/
           objrequestVO.setModel(requestform.getModel()); 
        //}
       // objrequestVO.setModelrouter(requestform.getModelrouter());
        objrequestVO.setNumberofdevices(requestform.getNumberofdevices());
        objrequestVO.setRequesttype(requestform.getRequesttype());
        objrequestVO.setShopId(requestform.getShopId());
        //objrequestVO.setSubtypecpe(requestform.getSubtypecpe());
        /*if(requestform.getSubtype() == "" || requestform.getSubtypenew() != null){
           objrequestVO.setSubtypenew(requestform.getSubtypenew());
        } else {*/
           objrequestVO.setSubtype(requestform.getSubtype());
        //}
        
        objrequestVO.setReqbydate(requestform.getReqbydate());
        WitribeInventoryBO objBO= new WitribeInventoryBO();
        List statusList = objBO.createRequest(objrequestVO,salesid);
        return statusList;
      //return true;
        
    }
    
}
