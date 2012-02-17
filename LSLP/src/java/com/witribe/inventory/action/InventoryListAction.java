/*
 * InventoryListAction.java
 *
 * Created on June 22, 2009, 10:26 AM
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
public class InventoryListAction extends BaseAction{
    
    /** Creates a new instance of InventoryListAction */
    public InventoryListAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        DistributeInventoryForm dform= (DistributeInventoryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        String InvStatus = dform.getSearchbystatus();
        
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              WitribeInventoryBO objbO= new WitribeInventoryBO();
              DistributeInventoryVO objInventoryVO= new DistributeInventoryVO();
              objInventoryVO.setSalesId(salesid);
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
               List objList = null;
               //List objNameList = null;
              String name = null;
               if(objShopList.size()>0){
              if(objInventoryVO.getShopId() == null) {
                   
                    ShopsVO shopvo=(ShopsVO)objShopList.get(0);
                    shopId=shopvo.getShopId();
                    objInventoryVO.setInventorytype(WitribeConstants.CPE);
                    objInventoryVO.setSubtype(WitribeConstants.SUBTYPE_INDOOR);
                    
                   }
                   String personid = null;
               if(InvStatus != null){
              objList = objbO.fetchInventoryList(objInventoryVO,shopId);
               } else {
                  objInventoryVO.setSearchbystatus(WitribeConstants.INV_STATUS_AT_SHOP);
                  objList = objbO.fetchInventoryList(objInventoryVO,shopId);          
               }
              //objsalesList = objbO.fetchAssignedSales(shopId,salesid);
              
              }
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.INVENTORY_LIST);
              req.setAttribute("objShopList",objShopList);
              req.setAttribute("objInventoryList",objList);
              
              req.setAttribute("shopId",shopId);
              req.setAttribute("searchbystatus",InvStatus);
        }catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        //req.setAttribute("heading","Distribute Inventory");
        return mapping.findForward("success");
       
    }
}
