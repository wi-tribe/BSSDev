/*
 * KiosksToShopsAction.java
 *
 * Created on April 6, 2009, 11:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.sales.actionform.ShopDetailsForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.LogUtil;
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
public class KiosksToShopsAction extends BaseAction {
    
    /** Creates a new instance of KiosksToShopsAction */
    public KiosksToShopsAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        ShopDetailsForm shopform= (ShopDetailsForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS_HIERARCHY);  
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        String forwardName = "";
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
             WitribeSalesBO objBO = new WitribeSalesBO();
             List objShopList = new ArrayList();
             objShopList = objBO.requestShopsId(salesid);
             req.setAttribute("objShopsList",objShopList);
             List kobjList = new ArrayList();
             kobjList = objBO.requestkShopsId(salesid);
             req.setAttribute("objkShopsList",kobjList);
             forwardName = "kiosksToShop";
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_KIOSKS);
        return mapping.findForward(forwardName);
       
    }
}
