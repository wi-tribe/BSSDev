/*
 * SubmitVoucherRequest.java
 *
 * Created on October 20, 2009, 3:21 PM
 */

package com.witribe.inventory.action;

import com.witribe.action.BaseAction;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.actionform.RaiseRequestForm;
import com.witribe.inventory.actionform.VoucherReqForm;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.VoucherVO;
import com.witribe.util.LogUtil;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
/**
 *
 * @author BS68483
 * @version
 */

public class VoucherRequest extends BaseAction {
    
    /* forward name="success" path="" */
    //private final static String SUCCESS = "success";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm  form,
            HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        
        VoucherReqForm reqform= (VoucherReqForm)form; 
        req.setAttribute(WitribeConstants.LEAD_MANAGEMENT_ID,WitribeConstants.SALES_INVENTORY_MANAGEMENT);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            HttpSession userSession =  (HttpSession)req.getSession(true);
            String salesid=(String)userSession.getAttribute(WitribeConstants.SALES_ID);
            WitribeInventoryBO objbO= new WitribeInventoryBO();
            List objShopList = objbO.requestShopsId(salesid);
            req.setAttribute("objShopList",objShopList);
            if(reqform.getVoucherType()!=null)
            {
           List statusList = createRequest(reqform,salesid);
           req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VOUCHER_REQUEST);
           req.setAttribute("statusList",statusList);
           return mapping.findForward("submit");
            }
            else
            {
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VOUCHER_REQUEST);
              return mapping.findForward("request");  
            }
            
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
   List createRequest(VoucherReqForm reqform,String salesid)throws SQLException,Exception{
        
       
        VoucherVO objVouchVO = new VoucherVO();
        WitribeInventoryBO objBO= new WitribeInventoryBO();
        objVouchVO.setVoucherType(reqform.getVoucherType());
        objVouchVO.setResType(reqform.getResType());
        objVouchVO.setQuantity(reqform.getQuantity());
        objVouchVO.setVoucherInfo(reqform.getVoucherInfo());
        objVouchVO.setShopId(reqform.getShopId());
        List statusList = objBO.createVoucherRequest(objVouchVO,salesid);
        return statusList;
      //return true;
        
    }
    
}

