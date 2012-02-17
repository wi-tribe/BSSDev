/*
 * CSETransferAction.java
 *
 * Created on June 19, 2009, 12:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.sales.vo.ShopsVO;
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
public class CSETransferAction extends BaseAction{
    
    /** Creates a new instance of CSETransferAction */
    public CSETransferAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
         RaiseRequestForm dform = (RaiseRequestForm)form;
       req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        
      
              WitribeInventoryBO objbO= new WitribeInventoryBO();
              RaiseRequestVO objInventoryVO= new RaiseRequestVO();
              objInventoryVO.setSalesId(salesid);
              objInventoryVO.setInventorytype(dform.getInventorytype());
              objInventoryVO.setSubtype(dform.getSubtype());
              objInventoryVO.setSubtypecpe(dform.getSubtypecpe());
              objInventoryVO.setSubtyperouter(dform.getSubtyperouter());
              objInventoryVO.setShopId(dform.getShopId());
              objInventoryVO.setMake(dform.getMake());
              objInventoryVO.setModel(dform.getModel());
               String shopId = objInventoryVO.getShopId();
               List objShopList = objbO.requestShopsId(salesid);
               List objList = null;
               List objToShopList = null;
               if(objShopList.size()>0){
              if(objInventoryVO.getShopId() == null) {
                   
                    ShopsVO shopvo=(ShopsVO)objShopList.get(0);
                    shopId=shopvo.getShopId();
                    objInventoryVO.setInventorytype(WitribeConstants.CPE);
                    objInventoryVO.setSubtype(WitribeConstants.SUBTYPE_INDOOR);
                    
                   }
     
               
              objList = objbO.fetchCSETOShop(objInventoryVO,shopId);
              objToShopList =  objbO.requestShopsId(salesid);
              
              }
              
              req.setAttribute("objShopList",objShopList);
              req.setAttribute("objCSEList",objList);
              req.setAttribute("objToShopList",objToShopList);
              req.setAttribute("shopId",shopId);
              req.setAttribute("invtype",objInventoryVO.getInventorytype());
              req.setAttribute("invsubtype",objInventoryVO.getSubtype());
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.CSE_TRANSFER);
              return mapping.findForward("success");       
    }
     
}

