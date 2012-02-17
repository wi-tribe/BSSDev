/*
 * AddChannelAction.java
 *
 * Created on January 22, 2010, 5:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.commission.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ChannelForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ChannelVO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.LogUtil;
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
public class AddChannelAction extends BaseAction {
    
    /** Creates a new instance of AddChannelAction */
    public AddChannelAction() {
    }
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res){
        ChannelForm sform= (ChannelForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_COMMISSION);
        //List objCSEList = new ArrayList();
        List objChannelList = new ArrayList();
             
        try{
            if(!validateSession(req,res)) {
                    return mapping.findForward("login");
                }
            
               ChannelVO objchannelVO = new ChannelVO();
               if(sform.getChannelName() != null){
               objchannelVO.setChannelName(sform.getChannelName());
               objchannelVO.setChannelId(sform.getChannelId());
               //WitribeSalesBO objbo = new WitribeSalesBO();
               if(addChannelList(objchannelVO)) {
               //req.setAttribute("objChannelList",objChannelList);
               req.setAttribute(WitribeConstants.HEADING,"Add New Channel Type");
                return mapping.findForward("success");
               }
            } else {
                 objChannelList = getNewChannelList(objchannelVO);
                 req.setAttribute("objChannelList",objChannelList);
                  req.setAttribute(WitribeConstants.HEADING,"Add New Channel Type");
                return mapping.findForward("showChannel");
            }
            
            }    catch(SQLException e) {
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
    
    public boolean addChannelList(ChannelVO objchannelVO)throws SQLException, Exception{
       
       //objSalesVO.setChanneltype(channelType);
       WitribeSalesBO objsalesBO= new WitribeSalesBO();
       boolean status = objsalesBO.addChannelList(objchannelVO);
       return status;
       
     }
    public List getNewChannelList(ChannelVO objchannelVO) throws SQLException, Exception {
        WitribeSalesBO objsalesBO= new WitribeSalesBO();
        List objChannelList = objsalesBO.getNewChannelList(objchannelVO);
        return objChannelList;
    }
}
