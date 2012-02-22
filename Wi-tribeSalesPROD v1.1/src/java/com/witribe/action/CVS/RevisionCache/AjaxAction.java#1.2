/*
 * SubmitLeadAction.java
 *
 * Created on January 7, 2009, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

/**
 *
 * @author SC43278
 */
import com.witribe.bo.WitribeBO;
import com.witribe.commission.bo.CommissionBO;
import com.witribe.commission.vo.CommissionPlanVO;
import com.witribe.constants.WitribeConstants;
import com.witribe.inventory.bo.WitribeInventoryBO;
import com.witribe.inventory.vo.DistributeInventoryVO;
import com.witribe.inventory.vo.VoucherVO;
import com.witribe.inventory.vo.RaiseRequestVO;
import com.witribe.sales.bo.WitribeSalesBO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopSaleVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.LogUtil;
import com.witribe.vo.AddressMappingVO;
import com.witribe.vo.ProspectVO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AjaxAction extends Action{
    
    /** Creates a new instance of SubmitLeadAction */
    public AjaxAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        
        
        res.setContentType("text/html");
        res.setHeader( "Cache-Control", "no-store, no-cache"); //HTTP 1.1
        res.setHeader("Pragma","no-cache"); //HTTP 1.0
        try{
            if(req.getParameter("fun")!= null &&
                    "checkDuplicate".equalsIgnoreCase((String)req.getParameter("fun"))) {
                checkDuplicateLocation(req,res);
            }
            if(req.getParameter("fun")!=null &&
                    "callAjaxForShopbyTL".equalsIgnoreCase((String)req.getParameter("fun"))) {
                WitribeSalesBO wsBO = new WitribeSalesBO();
                try{
                    List objList = new ArrayList();
                    objList = wsBO.fetchShopsbySalesID(req.getParameter("salesid"));
                    int listSize = objList.size();
                    ShopSaleVO objSSVO = new ShopSaleVO();
                    String shopid = null;
                   for (int i=0;i<listSize;i++) {
                        objSSVO = (ShopSaleVO)objList.get(i);
                        shopid = objSSVO.getShop_id();
                        //shopid =shopid+"-"+objSSVO.getZone();
                        //System.out.println(shopid);
                        if(i==listSize-1) {
                            res.getWriter().write(shopid);
                        } else {
                            res.getWriter().write(shopid+",");
                        }
                    }//for
                    
                }//try
                catch(Exception e){}
            }
            if(req.getParameter("fun")!=null &&
                    "callAjaxForVoucherInfo".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String resourceType = req.getParameter("resType");
                WitribeInventoryBO objInvBo = new WitribeInventoryBO();
                
                 try{   
                List objList = new ArrayList();
                    objList = objInvBo.requestVoucherInfo(resourceType);
                
                        int listSize = objList.size();
                        VoucherVO objvouchVo = new  VoucherVO();
                        String voucher_info = null;
                        //String address1 = null;
                        for (int i=0;i<listSize;i++) {
                             objvouchVo = (VoucherVO)objList.get(i);
                             voucher_info = objvouchVo.getVoucherInfo();
                            if(i==listSize-1) {
                                res.getWriter().write(voucher_info);
                            } else {
                                res.getWriter().write(voucher_info+",");
                            }
                        
                    }
                }
                catch(Exception e){
                    }
                    
        }
                
                
            
            if(req.getParameter("fun")!=null &&
                    "callAjaxForParentSH".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String salesid = req.getParameter("salesid");
                String salestype = req.getParameter("salestype");
                WitribeSalesBO wsBO = new WitribeSalesBO();
                WitribeBO wb = new WitribeBO();
                try{
                    List objList = new ArrayList();
                    objList = wsBO.SalesforChildbyLead(salesid, salestype);
                    int listSize = objList.size();
                    SalesPersonnelVO objSPVO = new SalesPersonnelVO();
                    
                    for (int i=0;i<listSize;i++) {
                        objSPVO = (SalesPersonnelVO)objList.get(i);
                        String sales = objSPVO.getSalesId()+'-';
                        sales = sales+objSPVO.getFullname()+'-';
                        sales = sales+wb.SalesType(objSPVO.getSalestype());
                        
                        if(i==listSize-1) {
                            res.getWriter().write(sales);
                        } else {
                            res.getWriter().write(sales+",");
                        }
                    }//for
                    
                }//try
                catch(Exception e){throw e;}
            }//if
            if(req.getParameter("fun")!=null &&
                    "getSubAddressbyType".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String type = req.getParameter("type");
                String address = req.getParameter("address");
                String flag = req.getParameter("flag");
                WitribeBO wb = new WitribeBO();
                try{
                    List objList = new ArrayList();
                    objList = wb.getSubAddress(type,address,flag);
                    int listSize = objList.size();
                    AddressMappingVO objAddressVO = new AddressMappingVO();
                    String address1 = null;
                    for (int i=0;i<listSize;i++) {
                        objAddressVO = (AddressMappingVO)objList.get(i);
                        address1 = objAddressVO.getSubAddress();
                        
                        if(i==listSize-1) {
                            res.getWriter().write(address1);
                        } else {
                            res.getWriter().write(address1+",");
                        }
                    }//for
                    
                }//try
                catch(Exception e){
                    throw e;}
            }//if
            
            /*if(req.getParameter("fun")!=null &&
                    "getSubAddressByRole".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String type = req.getParameter("type");
                String address = req.getParameter("address");
                String flag = req.getParameter("flag");
                WitribeBO wb = new WitribeBO();
                HttpSession userSession =  (HttpSession)req.getSession(false);
                SalesPersonnelVO objSales = new SalesPersonnelVO();
                  
                   objSales.setSalesId((String)userSession.getAttribute(WitribeConstants.SALES_ID));
                   objSales.setSalestype((String)userSession.getAttribute(WitribeConstants.ROLE));
                   objSales.setCity((String)userSession.getAttribute(WitribeConstants.ADDR_CITY));
                   objSales.setZone((String)userSession.getAttribute(WitribeConstants.ADDR_ZONE));
                   objSales.setCountry((String)userSession.getAttribute(WitribeConstants.ADDR_COUNTRY));
                try{
                    List objList = new ArrayList();
                    objList = wb.getSubAddressByRole(type,address,flag,objSales);
                    int listSize = objList.size();
                    AddressMappingVO objAddressVO = new AddressMappingVO();
                    String address1 = null;
                    for (int i=0;i<listSize;i++) {
                        objAddressVO = (AddressMappingVO)objList.get(i);
                        address1 = objAddressVO.getSubAddress();
                        
                        if(i==listSize-1) {
                            res.getWriter().write(address1);
                        } else {
                            res.getWriter().write(address1+",");
                        }
                    }//for
                    
                }//try
                catch(Exception e){
                    throw e;}
            }//if
            */
            if(req.getParameter("fun")!=null &&
                    "getUnAssignedLocationList".equalsIgnoreCase((String)req.getParameter("fun"))) {
                SalesPersonnelVO objSalesVo = new SalesPersonnelVO();
                objSalesVo.setSalestype(req.getParameter("type"));
                objSalesVo.setZone(req.getParameter("zone"));
                objSalesVo.setSubzone(req.getParameter("subzone"));
                objSalesVo.setCity(req.getParameter("city"));
                objSalesVo.setSalesId(req.getParameter("salesid"));
                WitribeSalesBO objSalesBO = new WitribeSalesBO();
                try{
                    List objList = new ArrayList();
                    objList = objSalesBO.getUnAssignedLocationList(objSalesVo);
                    if(objList != null) {
                        int listSize = objList.size();
                        AddressMappingVO objAddressVO = new AddressMappingVO();
                        String address1 = null;
                        for (int i=0;i<listSize;i++) {
                            if(i==listSize-1) {
                                res.getWriter().write((String)objList.get(i));
                            } else {
                                res.getWriter().write((String)objList.get(i)+",");
                            }
                        }//for
                    } 
                }//try
                catch(Exception e){
                    throw e;}
            }
            
          if(req.getParameter("fun")!=null &&
                    "getProspectReason".equalsIgnoreCase((String)req.getParameter("fun"))) {
                
                ProspectVO objProspectVo = new ProspectVO();
                int reasonid = Integer.parseInt(req.getParameter("reasonId"));
                objProspectVo.setReasonId(reasonid);
                WitribeBO objProspectBO = new WitribeBO();
                String transitionState = null;
                try{
                    //List objList = new ArrayList();
                   objProspectVo = objProspectBO.getTransitionState(objProspectVo);
                   transitionState = objProspectVo.getTransitionState();
                   res.getWriter().write(transitionState);
                   }//try
                catch(Exception e){
                    throw e;}
            }
                
            if(req.getParameter("fun")!=null &&
                    "callAjaxForParentSHandShop".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String salesid = req.getParameter("salesid");
                String salestype = req.getParameter("salestype");
                WitribeSalesBO wsBO = new WitribeSalesBO();
                WitribeBO wb = new WitribeBO();
                try
                {
                    List objList = new ArrayList();
                    objList = wsBO.SalesforChildbyLead(salesid, salestype);
                    int listSize = objList.size();
                    SalesPersonnelVO objSPVO = new SalesPersonnelVO();
                    
                    for (int i=0;i<listSize;i++) 
                    {
                        objSPVO = (SalesPersonnelVO)objList.get(i);
                        String sales = objSPVO.getSalesId()+'-';
                        sales = sales+objSPVO.getFullname()+'-';
                        sales = sales+wb.SalesType(objSPVO.getSalestype());
                        
                        if(i==listSize-1) 
                        {
                            res.getWriter().write(sales);
                        } 
                        else 
                        {
                            res.getWriter().write(sales+",");
                        }
                    }//for
                    res.getWriter().write("||");
                    if (salestype.equalsIgnoreCase(WitribeConstants.TL)) {
                        objList = wsBO.fetchShopsbySalesID(req.getParameter("salesid"));
                        listSize = objList.size();
                        ShopSaleVO objSSVO = new ShopSaleVO();
                        String shopid = null;
                        for (int i=0;i<listSize;i++) 
                        {
                            objSSVO = (ShopSaleVO)objList.get(i);
                            shopid = objSSVO.getShop_id();
                            //System.out.println(shopid);
                            if(i==listSize-1) 
                            {
                                res.getWriter().write(shopid);
                            } 
                            else 
                            {
                                res.getWriter().write(shopid+",");
                            }
                        }//for
                    }
                }//try
                catch(Exception e){throw e;}
            }//if
        if(req.getParameter("fun")!=null &&
                    "callTransferInventory".equalsIgnoreCase((String)req.getParameter("fun"))) {
                SalesPersonnelVO objSalesVo = new SalesPersonnelVO();
                String salesid = req.getParameter("salesid");
                String type1 = (String)req.getParameter("type");
                int type = Integer.parseInt(type1);
                List objProvList = null;
                List objShopList = new ArrayList();
                List objList = new ArrayList();
                List objToShopList = new ArrayList();
                WitribeBO wbo = new WitribeBO();
                objSalesVo.setSalesId(salesid);
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                try {
                    if(type == 2 || type == 1 || type == 5){
                       objProvList = new ArrayList(); 
                    objProvList = wbo.getTransferProvList(objSalesVo);
                    SalesPersonnelVO objsalesVO = new SalesPersonnelVO();
                    if(objProvList != null && objProvList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                        int listSize = objProvList.size();
                        for (int i=0;i<listSize;i++) 
                        {
                            objsalesVO = (SalesPersonnelVO)objProvList.get(i);
                            String sales =objsalesVO.getChildSalesId()+'-';
                            sales = sales+objsalesVO.getFullname();
                           
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(sales);
                            } 
                            else 
                            {
                                res.getWriter().write(sales+",");
                            }
                        }//for
                    }
                    } else if (type == 3 || type == 6){
                            WitribeInventoryBO objbO= new WitribeInventoryBO();
                            String shopId = objInventoryVO.getShopId();
                              objShopList = objbO.requestShopsId(salesid);  
                              //objToShopList =  objbO.requestShopsId(salesid);
                              ShopsVO objshopsVO = new ShopsVO();
                              if(objShopList != null && objShopList.size()>0) {
                       
                        int listSize = objShopList.size();
                        for (int i=0;i<listSize;i++) 
                        {
                            objshopsVO = (ShopsVO)objShopList.get(i);
                            String shops =objshopsVO.getShopId()+'@';
                            shops = shops+objshopsVO.getShopname();
                           
                            if(i==listSize-1) 
                            {
                             res.getWriter().write(shops);
                            } 
                            else 
                            {
                                res.getWriter().write(shops+",");
                            }
                        }//for
                    }
                    }else if(type == 4){
                         WitribeInventoryBO objbO= new WitribeInventoryBO();
                          objInventoryVO.setShopId(salesid);
                        objInventoryVO.setInventorytype(WitribeConstants.CPE);
                    objInventoryVO.setSubtype(WitribeConstants.SUBTYPE_INDOOR);
                    objList = objbO.fetchTransInventory(objInventoryVO,objInventoryVO.getShopId());
                     int listSize = objList.size();
                        for (int i=0;i<listSize;i++) 
                        {
                            objInventoryVO = (RaiseRequestVO)objList.get(i);
                           /* String inv =objInventoryVO.getInventoryId()+'*';
                            inv = inv+objInventoryVO.getInventorytype()+'*';
                            inv = inv+objInventoryVO.getChangeStatus()+'*';
                            inv = inv+objInventoryVO.getMacaddrwan()+'*';
                            inv = inv+objInventoryVO.getUserdefined4()+'*';
                            inv = inv+objInventoryVO.getMake()+'*';
                            inv = inv+objInventoryVO.getModel();*/
                            
                            String  inv = objInventoryVO.getMacaddrwan()+'*';
                            inv = inv+objInventoryVO.getInventoryId()+'*';
                            inv = inv+objInventoryVO.getUserdefined4()+'*';
                            inv = inv+objInventoryVO.getChangeStatus()+'*';
                            inv = inv+objInventoryVO.getModel()+'*';
                            inv = inv+objInventoryVO.getMake()+'*';
                            inv = inv+objInventoryVO.getInventorytype();
                              
                            if(i==listSize-1) 
                            {
                             res.getWriter().write(inv);
                            } 
                            else 
                            {
                                res.getWriter().write(inv+",");
                            }
                        }//for
                    }
                                                   
                                   
                } catch(Exception e){throw e;}
                 
                             
            }

            if(req.getParameter("fun")!=null &&
                    "callAjaxForTLtoShop".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String shopid = req.getParameter("shopid");
                WitribeSalesBO objSalesBo = new WitribeSalesBO();
                ShopsVO objShopVO = new ShopsVO();
                try
                {
                    List objList = new ArrayList();
                    objShopVO.setShopId(shopid);
                    objList =  objSalesBo.getUnAssignedTLCSE(objShopVO);
                    int listSize = objList.size();
                    SalesPersonnelVO objSPVO = new SalesPersonnelVO();
                   
                       
                    if(objList != null && objList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                        for (int i=0;i<listSize;i++) 
                        {
                            objSPVO = (SalesPersonnelVO)objList.get(i);
                            String sales =objSPVO.getSalesId()+'-';
                            sales = sales+objSPVO.getFirstname()+' '+objSPVO.getLastname()+'@'+objSPVO.getShopZone();
                           
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(sales);
                            } 
                            else 
                            {
                                res.getWriter().write(sales+",");
                            }
                        }//for
                    }//if
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
            if(req.getParameter("fun")!=null &&
                    "callSpecificInventory".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String invtype = req.getParameter("invtype");
                String invsubtype = req.getParameter("invsubtype");
                WitribeInventoryBO objbo= new WitribeInventoryBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                try
                {
                    List objInvTypeList = new ArrayList();
                    
                    objInventoryVO.setInventorytype(invtype);
                    objInventoryVO.setSubtype(invsubtype);
                    if( objInventoryVO.getSubtype() != ""){
                    objInvTypeList = objbo.getTypeInv(objInventoryVO);
                   
                    int listSize = objInvTypeList.size();
                       String specificInv = null;                    
                    if(objInvTypeList != null && objInvTypeList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                        for (int i=0;i<listSize;i++) 
                        {
                            objInventoryVO = (RaiseRequestVO)objInvTypeList.get(i);
                            if(objInventoryVO.getMake() != null){
                             specificInv =objInventoryVO.getMake();
                            }else {
                               specificInv ="";  
                            }
                            //specificInv = specificInv+objInventoryVO.getModel();
                           
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(specificInv);
                            } 
                            else 
                            {
                                res.getWriter().write(specificInv+",");
                            }
                        }//for
                    }//if
                    } else {
                     
                    }
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
             if(req.getParameter("fun")!=null &&
                    "callSpecificMakeInventory".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String invtype = req.getParameter("invtype");
                String invsubtype = req.getParameter("invsubtype");
                 String make = req.getParameter("make");
                WitribeInventoryBO objbo= new WitribeInventoryBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                try
                {
                    List objInvMakeList = new ArrayList();
                    
                    objInventoryVO.setInventorytype(invtype);
                    objInventoryVO.setSubtype(invsubtype);
                    objInventoryVO.setMake(make);
                    if( objInventoryVO.getMake() != ""){
                    objInvMakeList = objbo.getTypeInvMake(objInventoryVO);
                   
                    int listSize = objInvMakeList.size();
                       String specificInv = null;                    
                    if(objInvMakeList != null && objInvMakeList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                        for (int i=0;i<listSize;i++) 
                        {
                            objInventoryVO = (RaiseRequestVO)objInvMakeList.get(i);
                            if(objInventoryVO.getModel() != null){
                             specificInv =objInventoryVO.getModel();
                            }else {
                               specificInv ="";  
                            }
                            //specificInv = specificInv+objInventoryVO.getModel();
                           
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(specificInv);
                            } 
                            else 
                            {
                                res.getWriter().write(specificInv+",");
                            }
                        }//for
                    }//if
                    } else {
                     
                    }
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
          if(req.getParameter("fun")!=null &&
                    "callInventory".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String invtype = req.getParameter("invtype");
                String invsubtype = req.getParameter("invsubtype");
                //String make = req.getParameter("make");
                //String model = req.getParameter("model");
                //String shopid = req.getParameter("shopid");
                WitribeInventoryBO objbo= new WitribeInventoryBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                try
                {
                    List objInvSubTypeList = new ArrayList();
                    //List minmaxvalue = new ArrayList();
                    objInventoryVO.setInventorytype(invtype);
                    objInventoryVO.setSubtype(invsubtype);
                    //objInventoryVO.setMake(make);
                    //objInventoryVO.setModel(model);
                    //objInventoryVO.setShopId(shopid);
                     objInvSubTypeList = objbo.getSubtypeList(objInventoryVO); 
                       int listSize = objInvSubTypeList.size();
                       String specificInv = null;                    
                    if(objInvSubTypeList != null && objInvSubTypeList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                           specificInv = (" ,select");
                        for (int i=0;i<listSize;i++) 
                        {
                            objInventoryVO = (RaiseRequestVO)objInvSubTypeList.get(i);
                            if(objInventoryVO.getSubtype() != null){
                             specificInv =objInventoryVO.getSubtype();
                            }else {
                               specificInv ="";  
                            }
                            
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(specificInv);
                            } 
                            else 
                            {
                                res.getWriter().write(specificInv+",");
                            }
                        }//for
                    }//if
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
           //  System.out.println("Fun value"+req.getParameter("fun"));
            
          if(req.getParameter("fun")!=null &&
                    "callInventorySubType".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String invtype = req.getParameter("invtype");
                String invsubtype = req.getParameter("invsubtype");
                String shopId = req.getParameter("shopId");
                System.out.println("shopId"+shopId);
                System.out.println("invsubtype"+invsubtype);
                System.out.println("invtype"+invtype);
                WitribeInventoryBO objbo= new WitribeInventoryBO();
               
                 DistributeInventoryVO objInventoryVO= new DistributeInventoryVO();
                try
                {
                    List objInventoryList = new ArrayList();
                
                    objInventoryVO.setInventorytype(invtype);
                    objInventoryVO.setSubtype(invsubtype);
                     objInventoryList = objbo.fetchShopInventory(objInventoryVO,shopId);
                       int listSize = objInventoryList.size();
                      // System.out.println("list.size"+listSize);
                       String specificInv = null;                    
                    if(objInventoryList != null && objInventoryList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                           
                           
                        for (int i=0;i<listSize;i++) 
                        {
                            objInventoryVO = (DistributeInventoryVO)objInventoryList.get(i);
                            if(objInventoryVO.getInventoryId() != null){
                             specificInv =objInventoryVO.getInventoryId();
                            }else {
                               specificInv ="";  
                            }
                            
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(specificInv);
                            } 
                            else 
                            {
                                res.getWriter().write(specificInv+",");
                            }
                        }//for
                    }//if
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
                 
          if(req.getParameter("fun")!=null &&
                    "callAJAXZoneList".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String selcity = req.getParameter("city");
                CommissionBO objCommBo = new CommissionBO();
                CommissionPlanVO objCommVO = new CommissionPlanVO();
                try
                {
                    List objList = new ArrayList();
                    objCommVO.setCity(selcity);
                    objList =  objCommBo.getZoneList(objCommVO);
                    int listSize = objList.size();
                    
                    if(objList != null && objList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                        for (int i=0;i<listSize;i++) 
                        {
                            objCommVO = (CommissionPlanVO)objList.get(i);
                            String zonelist =objCommVO.getZone();
                           
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(zonelist);
                            } 
                            else 
                            {
                                res.getWriter().write(zonelist+",");
                            }
                        }//for
                    }//if
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
              if(req.getParameter("fun")!=null &&
                    "callAJAXProductList".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String selcity = req.getParameter("city");
                CommissionBO objCommBo = new CommissionBO();
                CommissionPlanVO objCommVO = new CommissionPlanVO();
                try
                {
                    List objList = new ArrayList();
                    objCommVO.setCity(selcity);
                    objList =  objCommBo.getProductList(objCommVO);
                    int listSize = objList.size();
                    
                    if(objList != null && objList.size()>0) {
                        //objSPVO = (SalesPersonnelVO)objList.get(0);
                       //String sales = objSPVO.getShopZone()+"~"; 
                        for (int i=0;i<listSize;i++) 
                        {
                            objCommVO = (CommissionPlanVO)objList.get(i);
                            String productlist =objCommVO.getProductId()+'-';
                            productlist=productlist+objCommVO.getProductName();
                     
                            if(i==listSize-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(productlist);
                            } 
                            else 
                            {
                                res.getWriter().write(productlist+",");
                            }
                        }//for
                    }//if
                                      
                }//try
                catch(Exception e){throw e;}
            }//if
       
         if(req.getParameter("fun")!=null &&
                    "callMinMax".equalsIgnoreCase((String)req.getParameter("fun"))) {
                String invtype = req.getParameter("invtype");
                String invsubtype = req.getParameter("invsubtype");
                String make = req.getParameter("make");
                String model = req.getParameter("model");
                String shopId = req.getParameter("shopId");
                List minmaxvalue = new ArrayList();
                String val = null;
               
                WitribeInventoryBO objbO= new WitribeInventoryBO();
                RaiseRequestVO objInventoryVO= new RaiseRequestVO();
                try
                {
                    //List objInvTypeList = new ArrayList();
                    objInventoryVO.setInventorytype(invtype);
                    objInventoryVO.setSubtype(invsubtype);
                    objInventoryVO.setMake(make);
                    objInventoryVO.setModel(model);
                    objInventoryVO.setShopId(shopId);
                    minmaxvalue = objbO.getMinMaxLevel(objInventoryVO);
                 if(minmaxvalue != null && minmaxvalue.size() > 0 ){
                 for(int i=0;i<minmaxvalue.size();i++){
                        objInventoryVO = (RaiseRequestVO)minmaxvalue.get(i);
                        if(objInventoryVO.getMinLevel() != null){
                        val = objInventoryVO.getMinLevel()+"-";
                        val = val+objInventoryVO.getMaxLevel();
                        } else {
                            val = "";
                        }
                         if(i==minmaxvalue.size()-1) 
                            {
                            //String tempzone=objSPVO.getShopZone();
                            //sales =  sales+' '+tempzone;
                            //req.setAttribute("shopzone",tempzone);
                            //System.out.println(objList.get(listSize));
                            res.getWriter().write(val);
                            } 
                            else 
                            {
                                res.getWriter().write(val+",");
                            }
                        break;
                 }
               
              } /*else {
                  val = ""+"-";
                  val = val+"";      
              } 
                   
               res.getWriter().write(val);  */                            
                }//try
                catch(Exception e){throw e;}
            }//if

        }catch(SQLException e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
            res.getWriter().write("exception");
        } catch(Exception e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
            res.getWriter().write("exception");
        }
        
        return null;
    }
    
    
    public void checkDuplicateLocation(HttpServletRequest req,HttpServletResponse res) throws SQLException, Exception{
        WitribeSalesBO objSalseBO= new WitribeSalesBO();
        String salesType = (String)req.getParameter("salestype");
        String country = (String)req.getParameter("country");
        String province = (String)req.getParameter("province");
        String city = (String)req.getParameter("city");
        String zone = (String)req.getParameter("zone");
        SalesPersonnelVO salesVo = new SalesPersonnelVO();
        salesVo.setSalestype(salesType);
        salesVo.setCountry(country);
        salesVo.setProvince(province);
        salesVo.setCity(city);
        salesVo.setZone(zone);
        String dupString=null;
        if(salesType != null) {
            dupString = objSalseBO.checkDuplicateLocation(salesVo);
        }
        res.getWriter().write(dupString);
    }
    
    
}
