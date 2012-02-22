/*
 * AddZoneSubzoneAction.java
 *
 * Created on July 14, 2009, 12:07 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;

import com.witribe.action.BaseAction;
import com.witribe.actionform.AddressMappingForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.LogUtil;
import com.witribe.vo.AddressMappingVO;
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
public class AddZoneSubzoneAction extends BaseAction{
    
    /** Creates a new instance of AddZoneSubzoneAction */
    public AddZoneSubzoneAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        AddressMappingForm sform= (AddressMappingForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_PERSONNEL);  
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
                List objCityList = new ArrayList();
                List objZoneList = new ArrayList();
                List objSubzoneList = new ArrayList();
                List objProvList = new ArrayList();
                List CityList = new ArrayList();
                List ZoneList = new ArrayList();
                List ProvList = new ArrayList();
                List objList1 = new ArrayList();
                WitribeBO wbo = new WitribeBO();
                HttpSession userSession =  (HttpSession)req.getSession(false);
                SalesPersonnelVO objSales = new SalesPersonnelVO();
                objSales.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                objSales.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                objSales.setCity((String)userSession.getAttribute(WitribeConstants.ADDR_CITY));
                objSales.setZone((String)userSession.getAttribute(WitribeConstants.ADDR_ZONE));
                objSales.setCountry((String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY)); 
                int role = Integer.parseInt(objSales.getSalestype());
                if(role == Integer.parseInt(WitribeConstants.TYPE_ADMIN) || role == Integer.parseInt(WitribeConstants.TYPE_NSM)){
                objList1 = wbo.getSubAddress("1","Pakistan","0");
               //AddressMappingVO addr = (AddressMappingVO)objList1.get(0);
                 //String prov = addr.getProvince();              
                //CityList = wbo.getSubAddress("2",prov,"0");
                
                } else if(role == Integer.parseInt(WitribeConstants.TYPE_RSM)){
                    
                   CityList = wbo.getCityList(objSales); //select
                   ProvList = wbo.getSubAddressByRole("2","0",CityList,objSales); 
                   
                } else if(role == Integer.parseInt(WitribeConstants.TYPE_TL)){
                    
                    ZoneList = wbo.getZoneList(objSales); 
                    CityList = wbo.getSubAddressByRole("3","0",ZoneList,objSales); 
                    ProvList = wbo.getSubAddressByRole("2","0",CityList,objSales); 
                } /*else {
                    
                    objSubzoneList = wbo.getSubzoneList(objSales); 
                    ZoneList = wbo.getSubAddressByRole("4","0",objSubzoneList,objSales); 
                    CityList = wbo.getSubAddressByRole("3","0",ZoneList,objSales); 
                    ProvList = wbo.getSubAddressByRole("2","0",CityList,objSales); 
                    
                }*/
                int listSize1 = objList1.size();                               
                AddressMappingVO objAddressVO = new AddressMappingVO();
                req.setAttribute("addressList",objList1);
                req.setAttribute("objCityList",CityList);
                req.setAttribute("objProvList",ProvList);
                req.setAttribute("objZoneList",ZoneList);
                //req.setAttribute("objSubzoneList",objSubzoneList);
                
                
                
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        req.setAttribute(WitribeConstants.HEADING,WitribeConstants.ADD_ZONE_SUBZONE);
        return mapping.findForward("success");
       
    }
}
