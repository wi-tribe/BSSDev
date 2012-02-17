/*
 * ShopsDAO.java
 *
 * Created on January 28, 2009, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.dao;

import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.BRMShopsVO;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopSubshopVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SC43278
 */
public class ShopsDAO {
    
    /** Creates a new instance of ShopsDAO */
    public ShopsDAO() {
    }
     public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = WitribeConstants.MAX_KEY;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(TO_NUMBER(SHOP_ID)) from SHOP_DETAILS");
            if(rs.next()) {
               maxKey = rs.getLong(1);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return  maxKey;
    }
    public boolean createShop(ShopsVO objShopsVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
           // long key = getMaxKey(con);
            pstmt = con.prepareStatement(
                    "INSERT INTO SHOP_DETAILS (SHOP_ID,SHOP_NAME,SHOP_TYPE," +
                   "ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                    "CREATED_DATE, SHOP_STATUS)" +
                   "VALUES " +
                    "(?,?,1,?,?,?,?,?,?,?,?,?,?)");
           
            pstmt.setString(1, objShopsVO.getShopId().trim());
            pstmt.setString(2, objShopsVO.getShopname());
            //pstmt.setString(3, objShopsVO.getShopType());
            pstmt.setString(3, objShopsVO.getPlot());
            pstmt.setString(4, objShopsVO.getStreet());
            pstmt.setString(5, objShopsVO.getSubzone());
            pstmt.setString(6, objShopsVO.getZone());
            pstmt.setString(7, objShopsVO.getCity());
            pstmt.setString(8, objShopsVO.getProvince());
            pstmt.setString(9, objShopsVO.getCountry());
            pstmt.setString(10, objShopsVO.getZip());
           
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(11, sqlDate);
             pstmt.setString(12, "Active");
            
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
                //Added by Harish
                //objShopsVO.setShopId(String.valueOf(key));
               // status = addLocationforShop(con,objShopsVO);
            }
                
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }
    public ShopsVO getShop(String shopId,Connection con) throws SQLException,Exception{
        ShopsVO objShopVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
             pstmt = con.prepareStatement(
                   "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE,ADDR_PLOT," +
                     "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                     "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE " +
                   "FROM SHOP_DETAILS WHERE SHOP_ID = ?"); 
             pstmt.setString(1,shopId);
             rs = pstmt.executeQuery();
            if(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
                         
          }     
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(pstmt);
        }
        
        return objShopVO;
        
    }
    public List getShops(Connection con) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ShopsVO objShopVO = null;
        try{
            pstmt = con.prepareStatement(
                   "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE," +
                    "ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE," +
                    "ADDR_CITY,ADDR_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                    "CREATED_DATE,MODIFIED_DATE " +
                   "FROM SHOP_DETAILS " +
                   "ORDER BY CREATED_DATE asc");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
               objList.add(objShopVO);
                count++;
                if(count == WitribeConstants.ZERO)
                    break;
            }
            if(rs.next()){
                objList.add("true");
            }
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
    
        public List getShopsforAddZone(Connection con) throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ShopsVO objShopVO = null;
        try{
            pstmt = con.prepareStatement(
                   "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE," +
                    "ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE," +
                    "ADDR_CITY,ADDR_PROVINCE,ADDR_COUNTRY,ADDR_ZIP," +
                    "CREATED_DATE,MODIFIED_DATE " +
                   "FROM SHOP_DETAILS " +
                   "ORDER BY CREATED_DATE asc");
            rs = pstmt.executeQuery();
            objList = new ArrayList();            
            while(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
               objList.add(objShopVO);
                
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
    public long getShopsRowCount(Connection con) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT count(*) from SHOP_DETAILS");
            if(rs.next()) {
                rowsCount = rs.getInt(1);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return  rowsCount;
    }
    public long getSubShopsRowCount(Connection con) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT count(*) from SHOPS_SUBSHOPS");
            if(rs.next()) {
                rowsCount = rs.getInt(1);
            }
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return  rowsCount;
    }
    public List getNextShops(Connection con,int pageNum,SalesPersonnelVO objSales) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        String salesid = objSales.getSalesId();
        int role = Integer.parseInt(objSales.getSalestype());
        String Province = objSales.getProvince();
       String City = objSales.getCity();
       String Zone = objSales.getZone();
       String SubZone = objSales.getSubzone();
        long totalRows;
        ShopsVO objShopVO = null;
        int startRow = 0;
        int endRow = 0;
        startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
         String QueryCont = "";
          try{
       if (role == Integer.parseInt(WitribeConstants.TYPE_TL))
       {
           QueryCont = "AND sd.salespersonnel_id = sm.salespersonnel_id and sd.salespersonnel_type > 2 and UPPER(sd.ADDR_PROVINCE) =UPPER('"+Province+"')"+" AND UPPER(sd.ADDR_ZONE) =UPPER('"+Zone+"')";
           pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //ScrollableResultSet sr= pstmt.executeQuery();
           
            rs = pstmt.executeQuery( "SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                   "SELECT SH.SHOP_ID,SH.SHOP_NAME,SH.SHOP_TYPE,SH.ADDR_PLOT," +
                    "SH.ADDR_STREET,SH.ADDR_SUBZONE,SH.ADDR_ZONE,SH.ADDR_CITY,SH.ADDR_PROVINCE," +
                    "SH.ADDR_COUNTRY,SH.ADDR_ZIP,SH.CREATED_DATE,SH.MODIFIED_DATE,SH.SHOP_STATUS " +
                    "FROM SHOP_DETAILS SH,SHOP_SALESID_MAPPING SM,SalesPersonnel_details sd WHERE SH.SHOP_ID IN (SELECT SM.SHOP_ID FROM SHOP_SALESID_MAPPING WHERE SM.SALESPERSONNEL_ID = ('"+salesid+"')) " +
                    QueryCont +" ORDER BY ADDR_CITY,ADDR_ZONE ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
       } else {
           pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //ScrollableResultSet sr= pstmt.executeQuery();
           
            rs = pstmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                   "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE,ADDR_PLOT," +
                    "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                    "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE,SHOP_STATUS " +
                    "FROM SHOP_DETAILS " +
                    "ORDER BY ADDR_CITY,ADDR_ZONE ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow); 
       }
       
          
            totalRows = getShopsRowCount(con);
           /* if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
                 count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
               objShopVO.setShopStatus(rs.getString(14));
               objList.add(objShopVO);
               
            }
           
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
    public boolean modifyShopInfo(ShopsVO objShopVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            //long key = getMaxKey(con);
           /* pstmt = con.prepareStatement(
                    "UPDATE SHOP_DETAILS set SHOP_NAME = ?," +
                   "SHOP_TYPE = ?,ADDR_PLOT = ?,ADDR_STREET = ?,ADDR_SUBZONE = ?,ADDR_ZONE = ?,"+
                    "ADDR_CITY = ?,ADDR_PROVINCE = ?," +
                   "ADDR_COUNTRY = ?,ADDR_ZIP = ?,MODIFIED_DATE = ? where SHOP_ID = ?");*/
            pstmt = con.prepareStatement(
                    "UPDATE SHOP_DETAILS set SHOP_NAME = ?," +
                   "ADDR_PLOT = ?,ADDR_STREET = ?,MODIFIED_DATE = ? where SHOP_ID = ?");
            
            pstmt.setString(1, objShopVO.getShopname());
            //pstmt.setString(2, objShopVO.getShopType());
            pstmt.setString(2, objShopVO.getPlot());
            pstmt.setString(3, objShopVO.getStreet());
           // pstmt.setString(5, objShopVO.getSubzone());
           // pstmt.setString(6, objShopVO.getZone());
           //pstmt.setString(7, objShopVO.getCity());
           // pstmt.setString(8, objShopVO.getProvince());
           // pstmt.setString(9, objShopVO.getCountry());
           // pstmt.setString(10, objShopVO.getZip());
            pstmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setString(5, objShopVO.getShopId());
            if(pstmt.executeUpdate()>0){
                status = true;
            }
                    
                
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeStatement(pstmt);
        }
        
        return status;
           
        
    }
   public boolean deleteShops(Connection con,String[] check) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        
        try{
            StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append("'");
                        inString.append(check[i]);
                        inString.append("'");
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append("'");
                    inString.append(check[0]);
                    inString.append("'");
                }
                inString.append(")");
           
               pstmt = con.prepareStatement(
                    "DELETE SHOP_DETAILS where SHOP_ID IN "+inString);
                 
                if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status = true;
                } 
            }
                            
                
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeStatement(pstmt);
        }
        
        return status;
           

        
    }
   public List getShopsToMap(Connection con,ShopsVO objkShopVO,SalesPersonnelVO objSalesVO)throws SQLException, Exception{
        List objList = null;
        objList = new ArrayList();
        ShopsVO objShopVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
            if(objSalesVO.getSalestype().equals(WitribeConstants.TYPE_TL)) {
             pstmt = con.prepareStatement(
                  "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE,ADDR_PLOT," +
                  "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                  "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE " +
                  "FROM SHOP_DETAILS  WHERE SHOP_ID NOT IN " +
                  "(SELECT SHOP_ID FROM SHOP_SALESID_MAPPING) and  SHOP_TYPE = 1 and UPPER(ADDR_ZONE)  IN (select UPPER(LOCATION) from SALES_LOCATION where SALESPERSONNEL_ID = ?)and UPPER(ADDR_CITY) = UPPER(?)  and UPPER(ADDR_COUNTRY) = UPPER(?) "); 
                            
              pstmt.setString(1,objSalesVO.getSalesId());
              pstmt.setString(2,objSalesVO.getCity());
              pstmt.setString(3,objSalesVO.getCountry());
              rs = pstmt.executeQuery();
            } else if (objSalesVO.getSalestype().equals(WitribeConstants.TYPE_RSM)) {
                 pstmt = con.prepareStatement(
                  "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE,ADDR_PLOT," +
                  "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                  "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE " +
                  "FROM SHOP_DETAILS  WHERE SHOP_ID NOT IN " +
                  "(SELECT SHOP_ID FROM SHOP_SALESID_MAPPING) and  SHOP_TYPE = 1 and UPPER(ADDR_CITY)  IN (select UPPER(LOCATION) from SALES_LOCATION where SALESPERSONNEL_ID = ?) and UPPER(ADDR_COUNTRY) = UPPER(?) "); 
                 
             //pstmt.setInt(1,Integer.parseInt(wc.TYPE_TL));
              pstmt.setString(1,objSalesVO.getSalesId());
              pstmt.setString(2,objSalesVO.getCountry());
              rs = pstmt.executeQuery();
            } else if (objSalesVO.getSalestype().equals(WitribeConstants.TYPE_ADMIN) || objSalesVO.getSalestype().equals(WitribeConstants.TYPE_NSM)) {
                 pstmt = con.prepareStatement(
                  "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE,ADDR_PLOT," +
                  "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                  "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE " +
                  "FROM SHOP_DETAILS  WHERE SHOP_ID NOT IN " +
                  "(SELECT SHOP_ID FROM SHOP_SALESID_MAPPING) and  SHOP_TYPE = 1 and UPPER(ADDR_COUNTRY) = UPPER(?) "); 
                 
             //pstmt.setInt(1,Integer.parseInt(wc.TYPE_TL));
             // pstmt.setString(1,objSalesVO.getSalesId());
              pstmt.setString(1,objSalesVO.getCountry());
              rs = pstmt.executeQuery();
            }
            if(rs != null) {
                while(rs.next()){
                   objShopVO = new ShopsVO();
                   objShopVO.setShopId(rs.getString(1));
                   objShopVO.setShopname(rs.getString(2));
                   objShopVO.setShopType(rs.getString(3));
                   objShopVO.setPlot(rs.getString(4));
                   objShopVO.setStreet(rs.getString(5));
                   objShopVO.setSubzone(rs.getString(6));
                   objShopVO.setZone(rs.getString(7));
                   objShopVO.setCity(rs.getString(8));
                   objShopVO.setProvince(rs.getString(9));
                   objShopVO.setCountry(rs.getString(10));
                   objShopVO.setZip(rs.getString(11));
                   objShopVO.setCreateDate(rs.getString(12));
                   objShopVO.setModifiedDate(rs.getString(13));
                   objList.add(objShopVO);

              } 
            }
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(pstmt);
        }
        
        return objList;
   }
  
  /** This method is to check the duplicate address details for shops and kiosks
   For Witribe-Owned Shops Key fields are Country,City,Zone
   For Kiosks Key fields are Country,City,Zone,Subzone */
   public boolean checkDuplicateLocationforShop(Connection con,ShopsVO shopsVo) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int shopType = 1;
        String query = "";
        try{
            String city = shopsVo.getCity();
            String country = shopsVo.getCountry();
            String Zone = shopsVo.getZone();
            String subZone = shopsVo.getSubzone();
            /*shopType = Integer.parseInt(shopsVo.getShopType());
           /* Commented as the add location to shop not required 
            if(shopsVo.getShopId() != null) {
                 shopsVo = getShop(shopsVo.getShopId(),con);
                 if(shopsVo!=null)
                 {
                    city = shopsVo.getCity();
                    country = shopsVo.getCountry();
                    String shopid = shopsVo.getShopId();
                 }
            }*/
            /*if(shopType == WitribeConstants.SHOP_WITRIBE_OWNED) {
              query = "SELECT SHOP_ID FROM SHOP_DETAILS WHERE " +
                    "UPPER(ADDR_CITY) =UPPER('"+city+"') AND " +
                    "UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                    "UPPER(ADDR_ZONE)=UPPER('"+Zone+"') AND SHOP_TYPE ="+shopType;
            } else {*/
                query = "SELECT SHOP_ID FROM SHOP_DETAILS WHERE " +
                    "UPPER(ADDR_CITY) =UPPER('"+city+"') AND " +
                    "UPPER(ADDR_COUNTRY) =UPPER('"+country+"') AND " +
                    "UPPER(ADDR_ZONE)=UPPER('"+Zone+"') AND "+
                    "UPPER(ADDR_SUBZONE)=UPPER('"+subZone+"') AND SHOP_TYPE = 1";
                
            //}
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
                if(rs.next()) {
                    isDuplicate = true;
            
            
            
            }
            
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  isDuplicate;
    }
   /** Checks duplicate shopID */
   public boolean checkDuplicateShopId(Connection con,ShopsVO shopsVo) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String query = "";
        try{
            query = "SELECT SHOP_ID FROM SHOP_DETAILS WHERE " +
                    "SHOP_ID = ?";
            
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,shopsVo.getShopId().trim());
            rs = pstmt.executeQuery();
                if(rs.next()) {
                    isDuplicate = true;
                 }
            
        } catch(SQLException se){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        return  isDuplicate;
    }
      public boolean addLocationforShop(Connection con,ShopsVO objShopsVO)throws SQLException, Exception{
       boolean status = false;
       PreparedStatement pstmt = null;      
       try{
           pstmt = con.prepareStatement("INSERT INTO SHOP_LOCATION (SHOP_ID,LOCATION,CREATE_DATE)" +
                   " VALUES  (?,?,?)");
           
            pstmt.setString(1, objShopsVO.getShopId());
            pstmt.setString(2, objShopsVO.getZone());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(3, sqlDate);
            
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           DBUtil.closeStatement(pstmt);
        }
       
       return status;
   }
     public List requestShopsId(Connection con,String salesid)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       ShopsVO objShopVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
             pstmt = con.prepareStatement(
                   "SELECT  sd.SHOP_ID,sd.SHOP_NAME,sd.SHOP_TYPE,sd.ADDR_PLOT," +
                     "sd.ADDR_STREET,sd.ADDR_SUBZONE,sd.ADDR_ZONE,sd.ADDR_CITY,sd.ADDR_PROVINCE," +
                     "sd.ADDR_COUNTRY,sd.ADDR_ZIP,sd.CREATED_DATE,sd.MODIFIED_DATE " +
                     "FROM SHOP_DETAILS sd,SHOP_SALESID_MAPPING SM where SD.SHOP_ID = SM.SHOP_ID AND SM.SALESPERSONNEL_ID = ? AND sd.SHOP_TYPE = "+wc.SHOP_WITRIBE_OWNED+" "); 
                    
             pstmt.setString(1,salesid);
             rs = pstmt.executeQuery();
            while(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
               objList.add(objShopVO);
                         
          }     
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(pstmt);
        }
        
        return objList;
   }
      public List requestkShopsId(Connection con,String salesid)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       ShopsVO objShopVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
            pstmt = con.prepareStatement(
                   "SELECT  SHOP_ID,SHOP_NAME,SHOP_TYPE,ADDR_PLOT," +
                     "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                     "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE " +
                     "FROM SHOP_DETAILS  WHERE SHOP_ID NOT IN " +
                     "(SELECT SUB_SHOP_ID FROM SHOPS_SUBSHOPS) and SHOP_TYPE = "+wc.SHOP_KIOSKS+" "); 
             rs = pstmt.executeQuery();
            while(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setShopId(rs.getString(1));
               objShopVO.setShopname(rs.getString(2));
               objShopVO.setShopType(rs.getString(3));
               objShopVO.setPlot(rs.getString(4));
               objShopVO.setStreet(rs.getString(5));
               objShopVO.setSubzone(rs.getString(6));
               objShopVO.setZone(rs.getString(7));
               objShopVO.setCity(rs.getString(8));
               objShopVO.setProvince(rs.getString(9));
               objShopVO.setCountry(rs.getString(10));
               objShopVO.setZip(rs.getString(11));
               objShopVO.setCreateDate(rs.getString(12));
               objShopVO.setModifiedDate(rs.getString(13));
               objList.add(objShopVO);
                         
          }     
        } catch(SQLException se){
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
           DBUtil.closeStatement(pstmt);
        }
        
        return objList;
   }
       public boolean createKiosksHierarchy(ShopSubshopVO salesform,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
               
        try{
            pstmt = con.prepareStatement(
                    "INSERT INTO SHOPS_SUBSHOPS(SHOP_ID,SUB_SHOP_ID,CREATED_DATE) VALUES (?,?,?)");
            //String fullname = objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
            //pstmt.setLong(1, ++key);
            pstmt.setString(1, salesform.getShopId());
            pstmt.setString(2, salesform.getSubShopId());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(3, sqlDate);
                      
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }
       public List fetchShopHierarchy(Connection con,int pageNum) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        ShopsVO objShopVO = null;
        try{
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //ScrollableResultSet sr= pstmt.executeQuery();
            
            rs = pstmt.executeQuery( 
                   "SELECT  ss.SUB_SHOP_ID,sd.SHOP_NAME as SUB_SHOP_NAME,ss.shop_ID, "+
                    "(select sd1.SHOP_NAME from SHOP_DETAILS sd1 where sd1.shop_id=ss.shop_id) as SHOP_NAME,SHOP_TYPE,ADDR_PLOT, "+
                    "ADDR_STREET,ADDR_SUBZONE,ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE, "+
                     "ADDR_COUNTRY,ADDR_ZIP,ss.CREATED_DATE,ss.MODIFIED_DATE "+
                     "FROM SHOP_DETAILS sd ,SHOPS_SUBSHOPS ss where ss.SUB_SHOP_ID = sd.SHOP_ID "+
                        "ORDER BY CREATED_DATE asc");
           
            totalRows = getSubShopsRowCount(con);
            if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }
            
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
               objShopVO = new ShopsVO();
               objShopVO.setSubShopId(rs.getString(1));
                objShopVO.setSubshopname(rs.getString(2));
               objShopVO.setShopId(rs.getString(3));
               objShopVO.setShopname(rs.getString(4));
               objShopVO.setShopType(rs.getString(5));
               objShopVO.setPlot(rs.getString(6));
               objShopVO.setStreet(rs.getString(7));
               objShopVO.setSubzone(rs.getString(8));
               objShopVO.setZone(rs.getString(9));
               objShopVO.setCity(rs.getString(10));
               objShopVO.setProvince(rs.getString(11));
               objShopVO.setCountry(rs.getString(12));
               objShopVO.setZip(rs.getString(13));
               objShopVO.setCreateDate(rs.getString(14));
               objShopVO.setModifiedDate(rs.getString(15));
               objList.add(objShopVO);
                count++;
                if(count==maxRowCount)
                    break;
            }
            if(rs.next()){
                objList.add("true");
            }
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
    public boolean deleteShopHierarchybySubshop(Connection con,String[] check) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        
        try{
            StringBuffer inString = new StringBuffer("(");
            if(check != null && check.length > WitribeConstants.ZERO) {
                if(check.length >1) {
                    for(int i=0;i<check.length-1;i++){
                        inString.append(check[i]);
                        inString.append(",");
                    }
                    inString.append(check[check.length-1]);
                } else {
                    inString.append(check[0]);
                }
                inString.append(")");
                
                pstmt = con.prepareStatement(
                        "DELETE SHOPS_SUBSHOPS WHERE SUB_SHOP_ID IN "+inString.toString());
                if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status = true;
                }
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
        
    }
    public boolean updateShopHierarchybySubshop(Connection con,ShopsVO ojbShopVo) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        
        try{
            
            pstmt = con.prepareStatement(
                    "UPDATE SHOPS_SUBSHOPS SET SHOP_ID=? , MODIFIED_DATE=? WHERE SUB_SHOP_ID =? ");
            pstmt.setString(1,ojbShopVo.getShopId());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(2, sqlDate);
            pstmt.setString(3,ojbShopVo.getSubShopId());
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
            }
            
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
        
    }
    
    public List fetchBRMShops(Connection con) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        long totalRows;
        BRMShopsVO objShopVO = null;
        try{
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //ScrollableResultSet sr= pstmt.executeQuery();
            
            rs = pstmt.executeQuery( "select subinventory_code,subinventory_description from MTL_SECONDARY_INVENTORIES_V where subinventory_code not in(select shop_id from shop_details)");
            
            objList = new ArrayList();
            int count = WitribeConstants.ZERO;
            while(rs.next()){
                objShopVO = new BRMShopsVO();
                objShopVO.setSubInventoryCode(rs.getString(1));
                objShopVO.setSubInventoryDesc(rs.getString(2));
                objList.add(objShopVO);
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(pstmt);
        }
        
        return objList;
        
    }
}
