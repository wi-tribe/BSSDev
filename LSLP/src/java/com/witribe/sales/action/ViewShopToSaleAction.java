/*
 * ViewShopToSaleAction.java
 *
 * Created on February 12, 2009, 5:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.bo.WitribeSalesBO;
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
public class ViewShopToSaleAction extends BaseAction{
    
    /** Creates a new instance of ViewShopToSaleAction */
    public ViewShopToSaleAction() {
    }
     public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws SQLException, Exception{
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.ASSOCIATE_TL_SHOP);
        String pageCount = req.getParameter("page");
        String flag = req.getParameter("flag");
        HttpSession userSession =  (HttpSession)req.getSession(false);
        WitribeSalesBO objBO = new WitribeSalesBO();
        //String forwardName = "";
        
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            
            List objList = new ArrayList();
            if(pageCount != null && flag != null) {
                int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
                if (WitribeConstants.PAGE_PREVIOUS.equalsIgnoreCase(flag)) {
                    req.setAttribute("page",--pageNum+"");
                    objList =  objBO.fetchShopsToSalesId(pageNum,req);
                }
                if (WitribeConstants.PAGE_NEXT.equalsIgnoreCase(flag)) {
                    req.setAttribute("page",++pageNum+"");
                    objList = objBO.fetchShopsToSalesId(pageNum,req);
                }
            } else {
                objList = objBO.fetchShopsToSalesId(0,req);
                req.setAttribute("page","0");
            }
            int listSize = objList.size();
            
            if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                req.setAttribute("next",objList.get(listSize-1));
                objList.remove(listSize-1);
            }
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_MODIFY_DELETE_TL_SHOP);
            req.setAttribute("objShopsToSaleList",objList);
            return mapping.findForward("success");
        }catch(Exception e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL);
            req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG);
            return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        
    }
}
