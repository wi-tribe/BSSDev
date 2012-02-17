package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.RaiseRequestVO;
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



public class InventoryLevelSettingAction extends BaseAction{
    

    public InventoryLevelSettingAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        RaiseRequestForm lform= (RaiseRequestForm) form;
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        HttpSession userSession =  (HttpSession)req.getSession(true);
         String salesid =(String)userSession.getAttribute(WitribeConstants.SALES_ID);
        List minmaxvalue = new ArrayList();
        String shopId = null;
        String minval = null;
        String maxval = null;
        RaiseRequestVO RequestVo = null;
        ShopsVO objShopVO = null;
        List objInvTypeList = null;
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
                }
                
               WitribeInventoryBO objbO= new WitribeInventoryBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                List objShopList = objbO.requestShopsId(salesid);
                if(lform.getShopId() == null || lform.getShopId() == ""){
                 objShopVO = (ShopsVO)objShopList.get(0);
                 shopId = objShopVO.getShopId();
                } else {
                    shopId = lform.getShopId();
                }
                objInventoryVO.setInventorytype("CPE");
                objInventoryVO.setSubtype("INDOOR");
                //objInvTypeList = objbO.getTypeInv(objInventoryVO);
                List objTypeList = objbO.getInvList();
                req.setAttribute("objShopList",objShopList);
                req.setAttribute("objInvTypeList",objTypeList);
                
              if(shopId!= null && !"".equals(shopId))
              {
               
               objInventoryVO.setInventorytype(lform.getInventorytype());
               objInventoryVO.setSubtype(lform.getSubtype());
               objInventoryVO.setSubtypecpe(lform.getSubtypecpe());
               objInventoryVO.setSubtyperouter(lform.getSubtyperouter());
               objInventoryVO.setMake(lform.getMake());
               objInventoryVO.setModel(lform.getModel());
               //objInventoryVO.setModelcpe(lform.getModelcpe());
               //objInventoryVO.setModelrouter(lform.getModelrouter());
               minmaxvalue = objbO.getMinMaxLevel(objInventoryVO);
               if(minmaxvalue != null && minmaxvalue.size() != 0 ){
                 for(int i=0;i<minmaxvalue.size();i++){
                        RequestVo = (RaiseRequestVO)minmaxvalue.get(i);
                        minval = RequestVo.getMinLevel();
                        maxval = RequestVo.getMaxLevel();
                        break;
                 }
               req.setAttribute("minval",minval);
               req.setAttribute("maxval",maxval);
              } else {
               req.setAttribute("minval","");
               req.setAttribute("maxval","");    
              }
              } else {
                  req.setAttribute("minval","");
                    req.setAttribute("maxval","");  
              }  
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.INVENTORY_LEVEL_SETTING);
             return mapping.findForward("inventorylevel");
            
             
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
        
    
    }
}