/*
 * SubmitChannelTypeAction.java
 *
 * Created on May 19, 2009, 6:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.sales.actionform.ChannelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ChannelVO;
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
public class SubmitChannelTypeAction extends BaseAction {
    
    /** Creates a new instance of SubmitChannelTypeAction */
    public SubmitChannelTypeAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        ChannelForm cform= (ChannelForm)form; 
        
         req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(modifyChannelType(cform,req)){
            req.setAttribute(WitribeConstants.HEADING,"Modified ChannelType To CSE");
            return mapping.findForward("success");
            } else {
                return mapping.findForward("failure");
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
    boolean modifyChannelType(ChannelForm cform,HttpServletRequest req)throws SQLException,Exception{
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        ChannelVO objChlVO = new ChannelVO();
        objChlVO.setSalesIdChannel(cform.getSalesIdChannel());
        objChlVO.setChanneltype(cform.getChanneltype());
        WitribeSalesBO objBO= new WitribeSalesBO();
        String sales[] = objChlVO.getSalesIdChannel().split("-");
        objChlVO.setSalesIdChannel(sales[0]);
        boolean status = objBO.modifyChannelType(objChlVO);
        return status;
         
        
    }
}

