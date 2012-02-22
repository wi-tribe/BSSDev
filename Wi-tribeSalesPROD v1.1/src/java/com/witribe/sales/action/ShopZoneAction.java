/*
 * ShopZoneAction.java
 *
 * Created on February 24, 2009, 5:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ShopDetailsForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ShopsVO;
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
 * @author HY27465
 */
public class ShopZoneAction extends BaseAction {
    
    /** Creates a new instance of ShopZoneAction */
    public ShopZoneAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws SQLException, Exception{
        ShopDetailsForm sform = (ShopDetailsForm)form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS);
        HttpSession userSession = (HttpSession)req.getSession(false);
        String role =(String)userSession.getAttribute(WitribeConstants.ROLE);
        String country = (String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY);
        String province = (String)userSession.getAttribute(WitribeConstants.ADDR_PROVINCE);
        
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(sform.getShopId()==null) {
                List objshopslist = new ArrayList();
                WitribeSalesBO wsbo = new WitribeSalesBO();
                objshopslist = wsbo.getShopsforAddZone();
                req.setAttribute("objshopslist",objshopslist);
                return mapping.findForward("success");
            }
            else
            {
                ShopsVO objshopsvo = new ShopsVO();
                WitribeSalesBO wsbo = new WitribeSalesBO();
                objshopsvo.setShopId(sform.getShopId());
                objshopsvo.setZone(sform.getZone());
                if(!wsbo.addLocationforShop(objshopsvo))
                {
                    req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.DUPLICATE_ADDRESS);
                    return mapping.findForward("duplicate");
                }
            }
        }catch(SQLException e) {
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
    
}
