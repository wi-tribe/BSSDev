/*
 * CoverageCheckAction.java
 *
 * Created on June 11, 2009, 12:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

import com.witribe.constants.WitribeConstants;
import com.witribe.sales.bo.WitribeSalesBO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author HY27465
 */
public class CoverageCheckAction extends BaseAction{
    
    /** Creates a new instance of CoverageCheckAction */
    public CoverageCheckAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
         //Code for populating the cities list ** MURALIDHAR**
            List CitylList = new ArrayList();
            WitribeSalesBO objSalesBO = new WitribeSalesBO();            
            CitylList = objSalesBO.getCityList();  
           // System.out.println("CitylList size====>"+CitylList.size());
            req.setAttribute("CitylList",CitylList);
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_ORDER_MANAGEMENT);
         return mapping.findForward("success");
    }
}
