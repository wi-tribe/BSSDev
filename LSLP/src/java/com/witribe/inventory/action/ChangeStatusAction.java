/*
 * ChangeStatusAction.java
 *
 * Created on March 3, 2009, 4:33 PM
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
public class ChangeStatusAction extends BaseAction {
    
    /** Creates a new instance of ChangeStatusAction */
    public ChangeStatusAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        DistributeInventoryForm dform= (DistributeInventoryForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(true);
        
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              WitribeInventoryBO objbO= new WitribeInventoryBO();
              DistributeInventoryVO objInventoryVO= new DistributeInventoryVO();
              objInventoryVO.setSalesId(salesid);
              objInventoryVO.setChangeStatus(dform.getChangeStatus());
              objInventoryVO.setInventorytype(dform.getInventorytype());
              objInventoryVO.setSubtype(dform.getSubtype());
              objInventoryVO.setSubtypecpe(dform.getSubtypecpe());
              objInventoryVO.setSubtyperouter(dform.getSubtyperouter());
              objInventoryVO.setShopId(dform.getShopId());
              objInventoryVO.setMake(dform.getMake());
              objInventoryVO.setModel(dform.getModel());
              objInventoryVO.setInventoryIdArray(dform.getInventoryId());
               String shopId = objInventoryVO.getShopId();
               List objShopList = objbO.requestShopsId(salesid);
               List objList = null;
               List objsalesList = null;
               if(objShopList.size()>0){
              if(objInventoryVO.getShopId() == null) {
                   
                    ShopsVO shopvo=(ShopsVO)objShopList.get(0);
                    shopId=shopvo.getShopId();
                                       
                   }
                   if(objInventoryVO.getInventoryIdArray() != null){
               objList = objbO.fetchInventory(objInventoryVO,shopId);
                   }
              //objList = objbO.fetchShopInventory(objInventoryVO,shopId);
              objsalesList = objbO.fetchAssignedSales(shopId,salesid);
              
              }
               
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CHANGE_INVENTORY_STATUS);
              req.setAttribute("objShopList",objShopList);
              req.setAttribute("objInventoryList",objList);
              req.setAttribute("objsalesList",objsalesList);
              req.setAttribute("shopId",shopId);
              req.setAttribute("invtype",objInventoryVO.getInventorytype());
              req.setAttribute("invsubtype",objInventoryVO.getSubtype());
              String[] invArray = objInventoryVO.getInventoryIdArray();
              if(invArray != null){
               for(int i=0;i<invArray.length;i++){
                 req.setAttribute("invId",invArray[i]);  
               }
              } else {
                 req.setAttribute("invId",objInventoryVO.getInventoryIdArray());  
              }  
              
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
