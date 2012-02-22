/*
 * SearchInventoryListAction.java
 *
 * Created on July 3, 2009, 12:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.DistributeInventoryForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.DistributeInventoryVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.LogUtil;
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
public class SearchInventoryListAction extends BaseAction{
    
    /** Creates a new instance of SearchInventoryListAction */
    public SearchInventoryListAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        DistributeInventoryForm dform= (DistributeInventoryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        String Searchinv = dform.getSearchinv();
        String searchby = dform.getSearchby();
        
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              WitribeInventoryBO objbO= new WitribeInventoryBO();
              DistributeInventoryVO objInventoryVO= new DistributeInventoryVO();
              objInventoryVO.setSearchby(dform.getSearchby());
              /*objInventoryVO.setSalesId(salesid);
              objInventoryVO.setInventorytype(dform.getInventorytype());
              objInventoryVO.setSubtype(dform.getSubtype());
              objInventoryVO.setSubtypecpe(dform.getSubtypecpe());
              objInventoryVO.setSubtyperouter(dform.getSubtyperouter());
              objInventoryVO.setShopId(dform.getShopId());
              objInventoryVO.setMake(dform.getMake());
              objInventoryVO.setModel(dform.getModel());
              objInventoryVO.setSearchbystatus(dform.getSearchbystatus());
               String shopId = objInventoryVO.getShopId();
               List objShopList = objbO.requestShopsId(salesid);
              
               //List objNameList = null;
              String name = null;
               if(objShopList.size()>0){
              if(objInventoryVO.getShopId() == null) {
                   
                    ShopsVO shopvo=(ShopsVO)objShopList.get(0);
                    shopId=shopvo.getShopId();
                    objInventoryVO.setInventorytype(WitribeConstants.CPE);
                    objInventoryVO.setSubtype(WitribeConstants.SUBTYPE_INDOOR);
                    
                   }
                   String personid = null;*/
              List objList = null;
              objList = objbO.searchInventoryList(objInventoryVO,Searchinv);
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SEARCH_INVENTORY);
              req.setAttribute("objInventoryList",objList);
               req.setAttribute("searchby",searchby);
               req.setAttribute("searchinv",Searchinv);
              //objsalesList = objbO.fetchAssignedSales(shopId,salesid);
              
              
              
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        //req.setAttribute("heading","Distribute Inventory");
        return mapping.findForward("Viewinv");
       
    }
}
