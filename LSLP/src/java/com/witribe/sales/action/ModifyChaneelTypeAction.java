/*
 * ModifyChaneelTypeAction.java
 *
 * Created on May 15, 2009, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ChannelForm;
import com.witribe.sales.actionform.SalesPersonnelForm;
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
public class ModifyChaneelTypeAction extends BaseAction {
    
    /** Creates a new instance of ModifyChaneelTypeAction */
    public ModifyChaneelTypeAction() {
    }
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res){
        ChannelForm sform= (ChannelForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);
        List objCSEList = new ArrayList();
        List objChannelList = new ArrayList();
        
              
        try{
            if(!validateSession(req,res)) {
                    return mapping.findForward("login");
                }
               ChannelVO objchannelVO = new ChannelVO();
               HttpSession userSession = (HttpSession)req.getSession(false);
               String role =(String)userSession.getAttribute(WitribeConstants.ROLE);
               String country = (String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY);
               String province = (String)userSession.getAttribute(WitribeConstants.ADDR_PROVINCE);
               String city = (String)userSession.getAttribute(WitribeConstants.ADDR_CITY);
               String zone = (String)userSession.getAttribute(WitribeConstants.ADDR_ZONE);
               String salesId = (String)userSession.getAttribute(WitribeConstants.SALES_ID);
               String channelType = (String)userSession.getAttribute(WitribeConstants.CHANNEL_TYPE);
               objchannelVO.setSalesIdChannel(sform.getSalesIdChannel());
               objchannelVO.setSalestype(role);
               objchannelVO.setCountry(country);
               objchannelVO.setProvince(province);
               objchannelVO.setCity(city);
               objchannelVO.setZone(zone);
               
                objCSEList = getCSEList(objchannelVO,salesId,sform);
                req.setAttribute("objCSEList",objCSEList);
               // req.setAttribute(WitribeConstants.HEADING,"Modify Channel To CSE"); 
             if(objchannelVO.getSalesIdChannel() == null || req.getAttribute("errorString") != null) {
                    
                    SalesPersonnelVO salesVo = (SalesPersonnelVO)objCSEList.get(0);
                    String salesid = salesVo.getSalesId();
                    objChannelList = getChannelList(objchannelVO,salesid);
                    req.setAttribute("objChannelList",objChannelList);
             } else {
                 
                objChannelList = getChannelList(objchannelVO,sform.getSalesIdChannel());
                req.setAttribute("objChannelList",objChannelList);
               }
                       
            req.setAttribute(WitribeConstants.HEADING,"Modify Channel To CSE");
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
      return mapping.findForward("success");
    }
    
     List getCSEList(ChannelVO objchannelVO,String salesid,ChannelForm sform)throws SQLException, Exception{
       
       //objSalesVO.setChanneltype(channelType);
       WitribeSalesBO objsalesBO= new WitribeSalesBO();
       List objCSEList = objsalesBO.getCSE(objchannelVO,salesid);
       //objSalesVO.setSalesId(sform.getSalesId());
       return objCSEList;
       
     }
     List getChannelList(ChannelVO objchannelVO,String salesid)throws SQLException, Exception{
       
       //objSalesVO.setChanneltype(channelType);
       WitribeSalesBO objsalesBO= new WitribeSalesBO();
       List objChannelList = objsalesBO.getChannelList(objchannelVO,salesid);
       return objChannelList;
       
     }
}
