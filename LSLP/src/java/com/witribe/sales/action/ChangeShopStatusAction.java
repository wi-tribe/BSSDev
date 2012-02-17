/*
 * ChangeShopStatusAction.java
 *
 * Created on September 29, 2009, 4:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.action;


import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.sales.actionform.ShopDetailsForm;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.SalesPersonnelVO;
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

 
public class ChangeShopStatusAction extends BaseAction {
    
    /** Creates a new instance of ChangeShopStatusAction */
    public ChangeShopStatusAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        ShopDetailsForm shopform= (ShopDetailsForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SHOPS);
        HttpSession userSession =  (HttpSession)req.getSession(false);
         String salesid = (String)userSession.getAttribute(WitribeConstants.SALES_ID);
        String salesType = (String)userSession.getAttribute(WitribeConstants.ROLE);
        String Province = (String)userSession.getAttribute(WitribeConstants.ADDR_PROVINCE);
       String City = (String)userSession.getAttribute(WitribeConstants.ADDR_CITY);
       String Zone = (String)userSession.getAttribute(WitribeConstants.ADDR_ZONE);
       String SubZone = (String)userSession.getAttribute(WitribeConstants.ADDR_SUBZONE);
        String pageCount = req.getParameter("page");
          String flag = req.getParameter("flag");
          SalesPersonnelVO objSalesVO = new SalesPersonnelVO();
        try{
               if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              String forwardName = "";
               WitribeSalesBO objBO= new WitribeSalesBO();
               String[] check=req.getParameterValues("check");
                 objSalesVO.setSalesId(salesid);
                 objSalesVO.setSalestype(salesType);
                 objSalesVO.setProvince(Province);
                 objSalesVO.setCity(City);
                 objSalesVO.setZone(Zone);
                 objSalesVO.setSubzone(SubZone);
              if(check != null && check.length > 0) {
                   ShopsVO objShopVO = objBO.getShop(check[0]);
                  req.setAttribute(WitribeConstants.HEADING,WitribeConstants.EDIT_SHOP);
                  req.setAttribute("objShop",objShopVO);
                  forwardName = "showshop";
              } else {
                   List objList = new ArrayList();
                   if(pageCount != null && flag != null) {
                        int pageNum = (pageCount != null)? Integer.parseInt(pageCount):0;
                        if (WitribeConstants.PAGE_PREVIOUS.equalsIgnoreCase(flag)) {
                             req.setAttribute("page",--pageNum+"");
                             objList =  objBO.getNextShopes(pageNum,objSalesVO); 
                        }
                          if (WitribeConstants.PAGE_NEXT.equalsIgnoreCase(flag)) {
                               req.setAttribute("page",++pageNum+"");
                               objList = objBO.getNextShopes(pageNum,objSalesVO); 
                          }
                    } else {
                        objList = objBO.getNextShopes(0,objSalesVO);
                        req.setAttribute("page","0");
                       }
                    int listSize = objList.size();
          
                    if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                        req.setAttribute("next",objList.get(listSize-1));
                        objList.remove(listSize-1);
                    } 
                    req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_SHOP_STATUS);
                    req.setAttribute("objShopsList",objList);
                    forwardName = "shopstatus";
                }
   
              return mapping.findForward(forwardName); 
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
}
