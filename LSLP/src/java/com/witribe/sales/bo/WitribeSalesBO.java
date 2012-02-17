/*
 * WitribeSalesBO.java
 *
 * Created on January 28, 2009, 12:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.bo;

import com.witribe.constants.WitribeConstants;
import com.witribe.dao.CityDAO;
import com.witribe.exception.DuplicateUserException;
import com.witribe.sales.dao.SalesPersonnelDAO;
import com.witribe.sales.dao.ShopsDAO;
import com.witribe.sales.vo.ChannelVO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopSaleVO;
import com.witribe.sales.vo.ShopSubshopVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.DBUtil;
import com.witribe.vo.AddressMappingVO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import com.witribe.sales.vo.SalesHierarchyVO;
import com.witribe.sales.dao.SalesHierarchyDAO;
import com.witribe.sales.dao.ShopSaleDAO;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author SC43278
 */
public class WitribeSalesBO {
    
    /** Creates a new instance of WitribeSalesBO */
    public WitribeSalesBO() {
    }
    public boolean createSale(SalesPersonnelVO objSalesVO) throws SQLException,DuplicateUserException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            brmcon = DBUtil.getBRMConnection();
            //objSalesVO.setFullname();
            if("".equals(checkDuplicateLocation(objSalesVO))) {
                status = objSalesDAO.createSale(objSalesVO, con,brmcon);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
            throw se;
        } catch(DuplicateUserException se){
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
            throw se;
        }catch(Exception e){
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return status;
    }
    
       public boolean createAddressMapping(AddressMappingVO addrVO) throws SQLException,DuplicateUserException, Exception{
        
        CityDAO objSalesDAO = new CityDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            status = objSalesDAO.createAddressMapping(addrVO, con);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(DuplicateUserException se){
            DBUtil.closeConnection(con);
            throw se;
        }catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean deleteAddressMapping(AddressMappingVO addrVO) throws SQLException,DuplicateUserException, Exception{
        
        CityDAO objSalesDAO = new CityDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            status = objSalesDAO.deleteAddressMapping(addrVO, con);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(DuplicateUserException se){
            DBUtil.closeConnection(con);
            throw se;
        }catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }   
    public boolean checkDuplicate(AddressMappingVO addrVO) throws SQLException,DuplicateUserException, Exception{
        
        CityDAO objSalesDAO = new CityDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            status = objSalesDAO.checkDuplicate(addrVO, con);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(DuplicateUserException se){
            DBUtil.closeConnection(con);
            throw se;
        }catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
   public boolean checkAssigned(AddressMappingVO addrVO) throws SQLException,DuplicateUserException, Exception{
        
        CityDAO objSalesDAO = new CityDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            status = objSalesDAO.checkAssigned(addrVO, con);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(DuplicateUserException se){
            DBUtil.closeConnection(con);
            throw se;
        }catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public String createShop(ShopsVO objShopsVO) throws SQLException, Exception{
        
        ShopsDAO objShopsDAO = new ShopsDAO();
        Connection con = null;
        String status = WitribeConstants.DUPLICATE_SHOP_ID;
        try{
            con =  DBUtil.getConnection();
            //objSalesVO.setFullname();
            //Added by Harish
            if(objShopsVO.getShopId() != null && !"".equals(objShopsVO.getShopId()) ) {
                if(!objShopsDAO.checkDuplicateShopId(con,objShopsVO)) {
                    if(!objShopsDAO.checkDuplicateLocationforShop(con,objShopsVO)){
                        if(objShopsDAO.createShop(objShopsVO, con)) {
                            return "";
                        }
                    }else {
                         return WitribeConstants.DUPLICATE_ADDRESS;    
                    }
                } else {
                    return WitribeConstants.DUPLICATE_SHOP_ID;
                }
            } else {
                return WitribeConstants.EMPTY_SHOP_ID;
            }
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public List getSales() throws SQLException,Exception{
        List objList = null;
        SalesPersonnelDAO objSaleDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objSaleDAO.getSales(con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List getNextSales(int pageCount,SalesPersonnelVO objSalesVO) throws SQLException,Exception{
        List objList = null;
        SalesPersonnelDAO objSaleDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objSaleDAO.getNextSales(con,pageCount,objSalesVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List getSalesLocation(int pageCount,SalesPersonnelVO objSalesVO) throws SQLException,Exception{
        List objList = null;
        SalesPersonnelDAO objSaleDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objSaleDAO.getSalesLocation(con,pageCount,objSalesVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public boolean deleteSalesLocation(String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.CheckMappingforSalesLocation(con,check);
            if(status == true)
            {
               status = objSalesDAO.deleteSalesLocation(con,check);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public SalesPersonnelVO getSale(String salesId) throws SQLException,Exception{
        SalesPersonnelVO objSaleVO = null;
        SalesPersonnelDAO objSaleDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con =  DBUtil.getConnection();
            objSaleVO = objSaleDAO.getSale(salesId, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objSaleVO;
    }
    public List getShops() throws SQLException,Exception{
        List objList = null;
        ShopsDAO objShopDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objShopDAO.getShops(con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List getShopsforAddZone() throws SQLException,Exception{
        List objList = null;
        ShopsDAO objShopDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objShopDAO.getShopsforAddZone(con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }    
    public List getNextShopes(int pageCount,SalesPersonnelVO salesid) throws SQLException,Exception{
        List objList = null;
        ShopsDAO objShopDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objShopDAO.getNextShops(con,pageCount,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public ShopsVO getShop(String salesId) throws SQLException,Exception{
        ShopsVO objShopVO = null;
        ShopsDAO objShopDAO = new ShopsDAO();
        Connection con = null;
        try{
            con =  DBUtil.getConnection();
            objShopVO = objShopDAO.getShop(salesId, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objShopVO;
    }
    public boolean modifySalesPersonnel(SalesPersonnelVO objSalesVO) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.modifySalesInfo(objSalesVO, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public boolean deleteSalesPersonnel(SalesPersonnelVO objSalesVO,String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.CheckMappingforSalesInfo(con,check);
            if(status == true)
            {
                status = objSalesDAO.deleteSalesInfo(con,check);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }

     public boolean inactivateSalesPersonnel(SalesPersonnelVO objSalesVO,String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            brmcon = DBUtil.getBRMConnection();
            status = objSalesDAO.inactiveCseInfo(con,check,brmcon);
            
            /* if(status == true)
            {
                status = objSalesDAO.RamoveMappingforCse(con,check);
            }*/
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return status;
    }
       public boolean closeSalesPersonnel(SalesPersonnelVO objSalesVO,String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            brmcon = DBUtil.getBRMConnection();
            status = objSalesDAO.closeCseInfo(con,check,brmcon);
           /* if(status == true)
            {
                status = objSalesDAO.RamoveMappingforCse(con,check);
            }*/
        } finally{
            DBUtil.closeConnection(con);
            DBUtil.closeConnection(brmcon);
        }
        return status;
    }
      public boolean activateSalesPersonnel(SalesPersonnelVO objSalesVO,String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = true;
        try{
           con = DBUtil.getConnection();
         //  status = objSalesDAO.CheckMappingforCseInfo(con,check);
           if(status == true)
          {
                status = objSalesDAO.activeCseInfo(con,check);
          }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
      public boolean activateShop(ShopsVO objShopsVO,String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
         Connection brmcon = null;
        Connection con = null;
        boolean status = true;
        try{
           con = DBUtil.getConnection();
           brmcon = DBUtil.getBRMConnection();
           if(status == true)
          {
                status = objSalesDAO.activeShopInfo(con,check,brmcon);
          }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
             DBUtil.closeConnection(brmcon);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             DBUtil.closeConnection(brmcon);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
             DBUtil.closeConnection(brmcon);
        }
        return status;
    }
      public boolean inactivateShop(ShopsVO objShopVO,String[] check) throws SQLException, Exception{
        
        SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        Connection brmcon = null;
        boolean status = true;
        try{
           con = DBUtil.getConnection();
           brmcon = DBUtil.getBRMConnection();
           if(status == true)
          {
               status = objSalesDAO.inactiveShopInfo(con,check,brmcon);
          }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
             DBUtil.closeConnection(brmcon);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
             DBUtil.closeConnection(brmcon);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
             DBUtil.closeConnection(brmcon);
        }
        return status;
    }
     public List getNextCse(int pageCount,SalesPersonnelVO objSalesVO) throws SQLException,Exception{
        List objList = null;
        SalesPersonnelDAO objSaleDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            objList = objSaleDAO.getNextCse(con,pageCount,objSalesVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }

    public boolean modifyShop(ShopsVO objShopVO) throws SQLException, Exception{
        
        ShopsDAO objShopDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objShopDAO.modifyShopInfo(objShopVO, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public boolean deleteShops(ShopsVO objShopVO,String[] check) throws SQLException, Exception{
        
        ShopsDAO objShopDAO = new ShopsDAO();
        ShopSaleDAO objSalesDAO = new ShopSaleDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.CheckMappingforShopinSalesHierarchy(con,check);
            if(status == true)
            {
                status = objSalesDAO.CheckforShopinShopSalesmapping(con,check);
                if(status == true)
                {
                    status = objShopDAO.deleteShops(con,check);
                }
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public List SalesforParent(HttpServletRequest req)throws Exception{
        List objList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = spDAO.SalesforParent(con, req);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List SalesforChild(HttpServletRequest req)throws Exception{
        List objList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = spDAO.SalesforChild(con,req);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
     public List getRsmCity(SalesPersonnelVO objSalesVO)throws Exception{
        List objRsmList = new ArrayList();
        List objTlList = new ArrayList();
        List objCseList = new ArrayList();
        List objCityList = new ArrayList();
        List objRsmCityList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objRsmList = spDAO.getRSMList(con,objSalesVO);
            objTlList = spDAO.getTlList(con,objSalesVO);
            objCseList = spDAO.getCseList(con,objSalesVO);
           // objCityList = spDAO.getUnAssignedCities(con);
            objRsmCityList.add(objRsmList);
            objRsmCityList.add(objTlList);
            objRsmCityList.add(objCseList);
           // objRsmCityList.add(objCityList);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objRsmCityList;
    }
     
    public boolean addLocation(SalesPersonnelVO objSalesVO)throws Exception{
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        
        try{
            con = DBUtil.getConnection();
          //  if(!spDAO.checkDuplicateLocation(con,objSalesVO)) {
                status = spDAO.addLocation(con,objSalesVO);
           // }
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
     
  /*  public boolean addRsmCity(SalesPersonnelVO objSalesVO)throws Exception{
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        
        try{
            con = DBUtil.getConnection();
            status = spDAO.addRsmCity(con,objSalesVO);
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }*/
    public List getTL(SalesPersonnelVO objSalesVO)throws Exception{
        List objTlList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objTlList = spDAO.getTL(con,objSalesVO);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objTlList;
    }
    public List getCSE(ChannelVO objSalesVO,String salesid)throws Exception{
        List objCSEList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objCSEList = spDAO.getCSE(con,objSalesVO,salesid);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objCSEList;
    }
    
    public List getChannelList(ChannelVO objSalesVO,String salesid)throws Exception{
        List objChannelList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objChannelList = spDAO.getChannelList(con,objSalesVO,salesid);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objChannelList;
    }
    public boolean modifyChannelType(ChannelVO objSalesVO)throws Exception{
                
       SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            status = spDAO.modifyChannelType(con,objSalesVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
      
    }
   /* public boolean addTlZone(SalesPersonnelVO objSalesVO)throws Exception{
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        boolean status = false;
        
        try{
            con = DBUtil.getConnection();
            status = spDAO.addTlZone(con,objSalesVO);
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }*/
    public List shopsForSalesId(ShopsVO objShopVO)throws Exception{
        List objList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            
            objList = spDAO.shopsForSalesId(con,objShopVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public List getShopsToMap(ShopsVO objShopsVO,SalesPersonnelVO objSalesVO)throws Exception {
        List objList = new ArrayList();
        ShopsDAO shopDAO = new ShopsDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = shopDAO.getShopsToMap(con,objShopsVO,objSalesVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
  public List requestShopsId(String salesid)throws Exception{
        List objList = new ArrayList();
        ShopsDAO shopDAO = new ShopsDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = shopDAO.requestShopsId(con,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
  public List requestkShopsId(String salesid)throws Exception{
        List objList = new ArrayList();
        ShopsDAO shopDAO = new ShopsDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = shopDAO.requestkShopsId(con,salesid);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public boolean createHierarchy(SalesHierarchyVO salesform) throws SQLException,DuplicateUserException, Exception{
        
        SalesHierarchyDAO objSalesDAO = new SalesHierarchyDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            status = objSalesDAO.createHierarchy(salesform, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean assignShop(ShopSaleVO ssform) throws SQLException,DuplicateUserException, Exception{
        
        ShopSaleDAO objssDAO = new ShopSaleDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            status = objssDAO.assignShop(ssform, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public List fetchHierarchy(int page,HttpServletRequest req) throws SQLException, Exception{
        
        List objList = new ArrayList();
        SalesHierarchyDAO objSalesDAO = new SalesHierarchyDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            objList = objSalesDAO.fetchHierarchy(con,page,req);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
     public List fetchShopHierarchy(int page) throws SQLException, Exception{
        
        List objList = new ArrayList();
        ShopsDAO objShopsDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            objList = objShopsDAO.fetchShopHierarchy(con,page);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public List fetchShopsToSalesId(int page,HttpServletRequest req) throws SQLException, Exception{
        
        List objList = new ArrayList();
        ShopSaleDAO objssDAO = new ShopSaleDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            objList = objssDAO.fetchShopsToSalesId(con,page,req);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    public boolean deleteSalesHierarchybyChild(SalesHierarchyVO objSalesVO,String[] check) throws SQLException, Exception{
        
        SalesHierarchyDAO objSalesDAO = new SalesHierarchyDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.deleteSalesHierarchybyChild(con,check);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean deleteShopHierarchybySubshop(ShopsVO objShopsVO,String[] check) throws SQLException, Exception{
        
        ShopsDAO objShopsDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objShopsDAO.deleteShopHierarchybySubshop(con,check);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean updateSalesHierarchybyChild(SalesHierarchyVO objSalesVO) throws SQLException, Exception{
        
        SalesHierarchyDAO objSalesDAO = new SalesHierarchyDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            // String[] arr = req.getParameterValues("child_salespersonnel_id");
            status = objSalesDAO.updateSalesHierarchybyChild(con,objSalesVO.getParent_salespersonnel_id(),objSalesVO.getChild_salespersonnel_id());
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
        public boolean updateShopforSalesHierarchy(Connection con,String childId,String shopId) throws SQLException, Exception{
        
        SalesHierarchyDAO objSalesDAO = new SalesHierarchyDAO();
        //Connection con = null;
        boolean status = false;
        try{
            //con = DBUtil.getConnection();           
            status = objSalesDAO.updateShopforSalesHierarchy(con,childId,shopId);
        }catch(SQLException se){
           // DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
          //  DBUtil.closeConnection(con);
            throw e;
        } finally{
          //  DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean updateShopHierarchybySubshop(ShopsVO objSalesVO) throws SQLException, Exception{
        
        ShopsDAO objShopDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            // String[] arr = req.getParameterValues("child_salespersonnel_id");
            status = objShopDAO.updateShopHierarchybySubshop(con,objSalesVO);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean updateShopToSales(ShopSaleVO objSalesVO) throws SQLException, Exception{
        
        ShopSaleDAO objSalesDAO = new ShopSaleDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            // String[] arr = req.getParameterValues("child_salespersonnel_id");
            String []check;
            check = new String[1];
            check[0] = objSalesVO.getShop_id();
            status = objSalesDAO.CheckMappingforShopinSalesHierarchy(con,check);
            if(status == true)
            {
                status = objSalesDAO.updateShopToSales(con,objSalesVO.getShop_id(),objSalesVO.getSalespersonnel_id());
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    public boolean deleteShopToSales(ShopSaleVO objSalesVO,String[] check) throws SQLException, Exception{
        
        ShopSaleDAO objSalesDAO = new ShopSaleDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.CheckMappingforShopinSalesHierarchy(con,check);
            if(status == true)
            {
                status = objSalesDAO.deleteShopToSales(con,check);
            }
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public List getUnAssignedTLCSE(ShopsVO objShopVO) throws SQLException, Exception{
        List objList = new ArrayList();
        ShopSaleDAO objSalesDAO = new ShopSaleDAO();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
           /*objSalesVO.setAssignedTL("");
            // This block will be called onchange of shopid
            if(objSalesVO.getShop_id() != null && !objSalesVO.getShop_id().equals("")) {
                    objSalesVO = objSalesDAO.checkAssignedTlToShop(objSalesVO,con);
                if(!(objSalesVO.getAssignedTL().equals(""))){
                    objList =  objSalesDAO.getUnAssignedCSE(objSalesVO,con);
                    return objList;
                }
              } */
            
          objList = objSalesDAO.getUnAssignedTLCSE(objShopVO,con);
           
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }

    
    public List fetchShopsbySalesID(String salesID) throws SQLException, Exception{
        List objList = new ArrayList();
        Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            ShopSaleDAO salesdao = new ShopSaleDAO();
            
            objList = salesdao.fetchShopsbySalesID(con,salesID);
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public String checkDuplicateLocation( SalesPersonnelVO salesVo) throws SQLException, Exception{
        Connection con = null;
        String respString = "";
        try{
            con = DBUtil.getConnection();
            SalesPersonnelDAO salesdao = new SalesPersonnelDAO();
            if(WitribeConstants.TYPE_RSM.equalsIgnoreCase(salesVo.getSalestype())) {
                if(salesdao.checkDuplicateLocation(con,salesVo)) {
                    respString = "duplicateCity";
                }
            }
            if(WitribeConstants.TYPE_TL.equalsIgnoreCase(salesVo.getSalestype())) {
                if(salesdao.checkDuplicateLocation(con,salesVo)) {
                    respString = "duplicateZone";
                }
            }
                       
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return respString;
    }
    
    public List SalesforChildbyLead(String salesid, String salestype)throws Exception{
        List objList = new ArrayList();
        SalesPersonnelDAO spDAO = new SalesPersonnelDAO();
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            objList = spDAO.SalesforChildbyLead(con,salesid, salestype);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return objList;
    }
    
    public boolean addLocationforShop(ShopsVO objshopsVO)throws Exception{
        ShopsDAO sDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        
        try{
            con = DBUtil.getConnection();
            if(!sDAO.checkDuplicateLocationforShop(con,objshopsVO))
            {
            //if(!spDAO.checkDuplicateLocation(con,objSalesVO)) {
             //   status = sDAO.addLocationforShop(con,objshopsVO);
            }
            
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }


   public boolean createKiosksHierarchy(ShopSubshopVO salesform) throws SQLException,DuplicateUserException, Exception{
        
        ShopsDAO objSalesDAO = new ShopsDAO();
        Connection con = null;
        boolean status = false;
        try{
            con =  DBUtil.getConnection();
            
            status = objSalesDAO.createKiosksHierarchy(salesform, con);
        }catch(SQLException se){
            DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
            DBUtil.closeConnection(con);
            throw e;
        } finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
   
     public List fetchBRMShops() throws SQLException, Exception{
         
         List objList = new ArrayList();
         ShopsDAO objShopsDAO = new ShopsDAO();
         Connection con = null;
         boolean status = false;
         try{
             con =  DBUtil.getConnection();
             
             objList = objShopsDAO.fetchBRMShops(con);
         }catch(SQLException se){
             DBUtil.closeConnection(con);
             throw se;
         } catch(Exception e){
             DBUtil.closeConnection(con);
             throw e;
         } finally{
             DBUtil.closeConnection(con);
         }
         return objList;
     }
     public List getUnAssignedLocationList(SalesPersonnelVO objSalesVo)throws SQLException,Exception{
         
            List objList = null;
            SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
            Connection con = null;
        boolean status = false;
        try{
            con = DBUtil.getConnection();
            objList = objSalesDAO.getUnAssignedLocationList(con,objSalesVo);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return objList;
        }

  public boolean addChannelList(ChannelVO objchnVo)throws SQLException,Exception{
            SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
            Connection con = null;
             boolean status = false;
        try{
            con = DBUtil.getConnection();
            status = objSalesDAO.addChannelList(con,objchnVo);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return status;
        }
   public List getNewChannelList(ChannelVO objchnVo)throws SQLException,Exception{
       SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
       List chnList = null;
        Connection con = null;
        try{
            con = DBUtil.getConnection();
            chnList = objSalesDAO.getNewChannelList(con,objchnVo);
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return chnList;
        }
    //Muralidhar added for populating cities
   public List getCityList() throws SQLException,Exception{
     SalesPersonnelDAO objSalesDAO = new SalesPersonnelDAO();
        Connection con = null;
        List cityList = null;
        try{
            con = DBUtil.getConnection();
            cityList = objSalesDAO.getCityList(con);
            
        }catch(SQLException se){
           DBUtil.closeConnection(con);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return cityList;  
    }
   }

