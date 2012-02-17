/*
 * RaiseRequestAction.java
 *
 * Created on February 18, 2009, 4:18 PM
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
import com.witribe.util.LogUtil;
import java.sql.SQLException;
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
public class RaiseRequestAction extends BaseAction{
    
    /** Creates a new instance of RaiseRequestAction */
    public RaiseRequestAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        RaiseRequestForm lform= (RaiseRequestForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
         HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        String reqType = lform.getRequesttype();
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(reqType == null ||
                     "1".equals(reqType)) {
                WitribeInventoryBO objbO= new WitribeInventoryBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                List objShopList = objbO.requestShopsId(salesid);
                objInventoryVO.setInventorytype("CPE");
                objInventoryVO.setSubtype("INDOOR");
                //List objInvTypeList = objbO.getTypeInv(objInventoryVO);
                List objTypeList = objbO.getInvList();
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.RAISE_REQUEST);
                req.setAttribute("objShopList",objShopList);
                req.setAttribute("objInvTypeList",objTypeList);
                return mapping.findForward("raiserequest");
            }else if(reqType != null && reqType.equals("2")){
                 getReturnReqData(req,res,lform);
                 req.setAttribute(WitribeConstants.HEADING,WitribeConstants.RAISE_REQUEST);
                 return mapping.findForward("raiserequest2");
            }else if(reqType != null && reqType.equals("3")){
                  getTransferReqData(req,res,lform);
                  req.setAttribute(WitribeConstants.HEADING,WitribeConstants.RAISE_REQUEST);
                  return mapping.findForward("raiserequest3");
                 
            }
             
        }catch(SQLException ex) {
              LogUtil.error("Exception "+ex.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
        
       return mapping.findForward("failure") ;
       
    }
    
    private void getReturnReqData(HttpServletRequest req,HttpServletResponse res, RaiseRequestForm dform) throws SQLException,Exception {
     
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(true);
        String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
       
              WitribeInventoryBO objbO= new WitribeInventoryBO();
              RaiseRequestVO objInventoryVO= new RaiseRequestVO();
              objInventoryVO.setSalesId(dform.getSalesId());
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
               List objsalesList = null;
               if(objShopList.size()>0){
              if(objInventoryVO.getShopId() == null) {
                   
                    ShopsVO shopvo=(ShopsVO)objShopList.get(0);
                    shopId=shopvo.getShopId();
                    objInventoryVO.setInventorytype(WitribeConstants.CPE);
                    objInventoryVO.setSubtype(WitribeConstants.SUBTYPE_INDOOR);
                    
                   }
     
               
              objList = objbO.fetchReturnInventory(objInventoryVO,shopId);
              
             // objsalesList = objbO.fetchAssignedSales(shopId,salesid);
              
              }
              
              req.setAttribute("objShopList",objShopList);
              req.setAttribute("objInventoryList",objList);
              //req.setAttribute("objsalesList",objsalesList);
              req.setAttribute("shopId",shopId);
              req.setAttribute("invtype",objInventoryVO.getInventorytype());
              req.setAttribute("invsubtype",objInventoryVO.getSubtype());
        
        
        //req.setAttribute("heading","Distribute Inventory");
        //return mapping.findForward("success");
         
    }
    private void getTransferReqData(HttpServletRequest req,HttpServletResponse res,RaiseRequestForm dform) throws SQLException,Exception {
        
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
     
               
              objList = objbO.fetchTransInventory(objInventoryVO,shopId);
              objToShopList =  objbO.requestShopsId(salesid);
              
              }
              
              req.setAttribute("objShopList",objShopList);
              req.setAttribute("objInventoryList",objList);
              req.setAttribute("objToShopList",objToShopList);
              req.setAttribute("shopId",shopId);
              req.setAttribute("invtype",objInventoryVO.getInventorytype());
              req.setAttribute("invsubtype",objInventoryVO.getSubtype());
        
    }
}
