/*
 * ShopSaleDAO.java
 *
 * Created on February 12, 2009, 4:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.dao;

import com.witribe.constants.WitribeConstants;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.sales.vo.ShopSaleVO;
import com.witribe.sales.vo.ShopsVO;
import com.witribe.sales.vo.ViewShopSaleVO;
import com.witribe.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SC43278
 */
public class ShopSaleDAO {
    
    /** Creates a new instance of ShopSaleDAO */
    public ShopSaleDAO() {
    }
    public boolean assignShop(ShopSaleVO ssform,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            pstmt = con.prepareStatement(
                    "INSERT INTO SHOP_SALESID_MAPPING(SHOP_ID,SALESPERSONNEL_ID,CREATED_DATE) VALUES (?,?,?)");
            //String fullname = objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
            //pstmt.setLong(1, ++key);
            pstmt.setString(1, ssform.getShop_id());
            pstmt.setString(2, ssform.getSalespersonnel_id());            
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(3, sqlDate);
           // String test1 = salesform.getParent_salespersonnel_id();
            
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
    public List fetchShopsToSalesId(Connection con,int pageNum, HttpServletRequest req) throws SQLException,Exception{
        List objList;
        Statement stmt = null;
        ResultSet rs = null;
        int maxRowCount = WitribeConstants.MAX_ROW_COUNT;
        long totalRows;
        WitribeConstants wc = new WitribeConstants();
        HttpSession usersession = (HttpSession)req.getSession(true);
       int role =Integer.parseInt((String)usersession.getAttribute(wc.ROLE));
       String Province = (String)usersession.getAttribute(wc.ADDR_PROVINCE);
       String City = (String)usersession.getAttribute(wc.ADDR_CITY);
       String Zone = (String)usersession.getAttribute(wc.ADDR_ZONE);
       String SubZone = (String)usersession.getAttribute(wc.ADDR_SUBZONE);
       int startRow = 0;
       int endRow = 0;
       String QueryCont = "";
       if (role == Integer.parseInt(wc.TYPE_TL))
       {
           QueryCont = "AND SD.SALESPERSONNEL_TYPE > 2 AND UPPER(ADDR_PROVINCE) =UPPER('"+Province+"')"+" AND UPPER(ADDR_ZONE) =UPPER('"+Zone+"')";
       }
        
        try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            rs = stmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT SD.SALESPERSONNEL_ID AS "+
                    "SALESID,SH.SHOP_ID AS SHOPID,(SELECT SHOP_NAME FROM SHOP_DETAILS WHERE "+
                    "SHOP_ID = SH.SHOP_ID) AS SHOPNAME, (SELECT ADDR_ZONE FROM SHOP_DETAILS WHERE "+
                    "SHOP_ID = SH.SHOP_ID) AS SHOPZONE,"+
                    "SD.ADDR_SUBZONE AS SUBZONE,"+
                    "SD.ADDR_ZONE AS ZONE, SD.ADDR_CITY AS CITY,SD.ADDR_PROVINCE AS PROVINCE,"+ 
                    "SD.ADDR_COUNTRY AS COUNTRY,concat(concat(SD.first_name,' '),SD.last_name) AS FULLNAME,SD.SALESPERSONNEL_TYPE FROM SALESPERSONNEL_DETAILS SD,"+
                    "SHOP_SALESID_MAPPING SH WHERE SD.SALESPERSONNEL_ID =SH.SALESPERSONNEL_ID "+
                    QueryCont +" ORDER BY SD.ADDR_CITY,SD.SALESPERSONNEL_TYPE,SD.ADDR_ZONE,SD.FIRST_NAME ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
            
            totalRows = getShopSaleRowCount(con);
           /* if(pageNum != 0){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            int count = WitribeConstants.ZERO;
            objList = new ArrayList();
            while(rs.next()) {
                ViewShopSaleVO ssform = new ViewShopSaleVO();
                count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
                ssform.setSalesid(rs.getString(1));
                ssform.setShopid(rs.getString(2));
                ssform.setShopname(rs.getString(3));
                //Added by bhawna on 13th oct, 2009
                ssform.setShopzone(rs.getString(4));
                //end
                ssform.setSubzone(rs.getString(5));
                ssform.setZone(rs.getString(6));
                ssform.setCity(rs.getString(7));
                ssform.setProvince(rs.getString(8));
                ssform.setCountry(rs.getString(9));
                ssform.setFullname(rs.getString(10));
                ssform.setSalestype(rs.getString(11));
                
                objList.add(ssform);
               
            }
            
            
        } catch(SQLException se){
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        
        return objList;
        
    }
    public long getShopSaleRowCount(Connection con) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) from SHOP_SALESID_MAPPING");
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
    public boolean updateShopToSales(Connection con,String shopid, String salesid) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;      
        
        try{
            
            pstmt = con.prepareStatement(
                    "UPDATE SHOP_SALESID_MAPPING SET SALESPERSONNEL_ID=? , MODIFIED_DATE=? WHERE SHOP_ID =? ");
            pstmt.setString(1,salesid);
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(2, sqlDate);
            pstmt.setString(3,shopid);
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
    public boolean deleteShopToSales(Connection con,String[] check) throws SQLException,Exception{
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
                        "DELETE SHOP_SALESID_MAPPING WHERE SHOP_ID IN "+inString.toString());
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
    
   public ShopSaleVO checkAssignedTlToShop(ShopSaleVO objShopVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WitribeConstants wc = new WitribeConstants();
        try{
            pstmt = con.prepareStatement("SELECT SM.SALESPERSONNEL_ID,SD.SALESPERSONNEL_TYPE from SALESPERSONNEL_DETAILS SD,SHOP_SALESID_MAPPING SM "+
                    " where SM.SHOP_ID = ?  AND SD.SALESPERSONNEL_ID = SM.SALESPERSONNEL_ID AND SD.SALESPERSONNEL_TYPE = ? ");
            //String fullname = objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
            //pstmt.setLong(1, ++key);
             pstmt.setString(1,objShopVO.getShop_id());
            pstmt.setInt(2,Integer.parseInt(wc.TYPE_TL));
           // pstmt.setInt(1,Integer.parseInt(wc.CSE));
            pstmt.setString(3,objShopVO.getSalesId());
            rs=pstmt.executeQuery();
            
              if(rs.next()) {
                String tl = rs.getString(1);
                objShopVO.setAssignedTL(tl);
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
  /*  
   commented on 13 august 2009
   public List getUnAssignedCSE(ShopSaleVO objSalesVO,Connection con) throws SQLException,Exception{
         List objList = null;
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
       try{
            pstmt = con.prepareStatement("SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                   "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                   "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE from SALESPERSONNEL_DETAILS sd, "+
                    "where   sd.SALESPERSONNEL_ID not in (select SALESPERSONNEL_ID "+
                    "from SHOP_SALESID_MAPPING) AND  sd.SALESPERSONNEL_TYPE in (?)",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            pstmt.setInt(1,Integer.parseInt(wc.TYPE_CSE));
             pstmt.setString(2,objSalesVO.getSalesId());
            rs = pstmt.executeQuery();
            while(rs.next()){
               objSalesVO.setSalesId(rs.getString(1));
               objSalesVO.setSalestype(rs.getString(2));
               objSalesVO.setFullname(rs.getString(3));
               StringTokenizer str = new StringTokenizer(objSalesVO.getFullname()," ");
               if(str.countTokens()>1){
                   objSalesVO.setFirstname((String)str.nextElement());
                   objSalesVO.setLastname((String)str.nextElement());
               }
               
               objSalesVO.setContactnumber(rs.getString(4));
               objSalesVO.setEmail(rs.getString(5));
               objSalesVO.setUserId(rs.getString(6));
               objSalesVO.setPassword(rs.getString(7));
               objSalesVO.setPlot(rs.getString(8));
               objSalesVO.setStreet(rs.getString(9));
               objSalesVO.setSubzone(rs.getString(10));
               objSalesVO.setZone(rs.getString(11));
               objSalesVO.setCity(rs.getString(12));
               objSalesVO.setProvince(rs.getString(13));
               objSalesVO.setCountry(rs.getString(14));
               objSalesVO.setZip(rs.getString(15));
               objSalesVO.setCreateDate(rs.getString(16));
               objSalesVO.setModifiedDate(rs.getString(17));
               objList.add(objSalesVO);
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
   }*/

 /*public List getUnAssignedTLCSE(ShopSaleVO objSalesVO1,Connection con)throws SQLException, Exception{
       List objList = null;
       objList = new ArrayList();
       WitribeConstants wc = new WitribeConstants();
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       //SalesPersonnelVO objSalesVO = null;
        try{
             pstmt = con.prepareStatement(
                   "SELECT  SALESPERSONNEL_ID,SALESPERSONNEL_TYPE," +
                   "FULL_NAME,CONTACT_NUMBER,EMAIL,USERID,PASSWORD,ADDR_PLOT,ADDR_STREET,ADDR_SUBZONE," +
                    "ADDR_ZONE,ADDR_CITY,ADDR_PROVINCE," +
                   "ADDR_COUNTRY,ADDR_ZIP,CREATED_DATE,MODIFIED_DATE, FIRST_NAME, LAST_NAME from SALESPERSONNEL_DETAILS "+
                    "where SALESPERSONNEL_TYPE = ? "); 
             pstmt.setString(1,wc.TYPE_TL);
             
             rs = pstmt.executeQuery();
            while(rs.next()){
               ShopSaleVO objSalesVO= new ShopSaleVO();
               objSalesVO.setSalesId(rs.getString(1));
               objSalesVO.setSalestype(rs.getString(2));
               objSalesVO.setFullname(rs.getString(3));
               /*****StringTokenizer str = new StringTokenizer(objSalesVO.getFullname()," ");
               if(str.countTokens()>1){
                   objSalesVO.setFirstname((String)str.nextElement());
                   objSalesVO.setLastname((String)str.nextElement());
               }*/
               
             /*  objSalesVO.setContactnumber(rs.getString(4));
               objSalesVO.setEmail(rs.getString(5));
               objSalesVO.setUserId(rs.getString(6));
               objSalesVO.setPassword(rs.getString(7));
               objSalesVO.setPlot(rs.getString(8));
               objSalesVO.setStreet(rs.getString(9));
               objSalesVO.setSubzone(rs.getString(10));
               objSalesVO.setZone(rs.getString(11));
               objSalesVO.setCity(rs.getString(12));
               objSalesVO.setProvince(rs.getString(13));
               objSalesVO.setCountry(rs.getString(14));
               objSalesVO.setZip(rs.getString(15));
               objSalesVO.setCreateDate(rs.getString(16));
               objSalesVO.setModifiedDate(rs.getString(17));
               objSalesVO.setFirstname(rs.getString(18));
               objSalesVO.setLastname(rs.getString(19));
               objList.add(objSalesVO);
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

   }*/

public List getUnAssignedTLCSE(ShopsVO objShopVO,Connection con) throws SQLException,Exception{
        List objList = null;
        SalesPersonnelVO objSalesVO = null;
        WitribeConstants wc = new WitribeConstants();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
       try{
          pstmt = con.prepareStatement(" SELECT  sd.SALESPERSONNEL_ID,sd.SALESPERSONNEL_TYPE," +
                   " sd.ADDR_zone,sd.addr_city,sd.FIRST_NAME,sd.LAST_NAME,shop.ADDR_ZONE from SALESPERSONNEL_DETAILS sd,SHOP_DETAILS shop,sales_location sl " +
                   " where   sd.SALESPERSONNEL_TYPE = ? and UPPER(shop.shop_ID) = UPPER(?) " +
                   " and UPPER(sd.ADDR_COUNTRY) = UPPER(shop.ADDR_COUNTRY) and UPPER(sd.ADDR_CITY) = UPPER(shop.ADDR_CITY) " + 
                   " and sl.SALESPERSONNEL_ID = sd.SALESPERSONNEL_ID and UPPER(sl.location) = UPPER(shop.ADDR_zone)",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            pstmt.setString(1,wc.TYPE_TL);
            pstmt.setString(2,objShopVO.getShopId());
            rs = pstmt.executeQuery();
           
            objList = new ArrayList();
            
            while(rs.next()){
                 objSalesVO = new SalesPersonnelVO();
                objSalesVO.setSalesId(rs.getString(1));
                objSalesVO.setSalestype(rs.getString(2));
                objSalesVO.setZone(rs.getString(3));
                objSalesVO.setCity(rs.getString(4));
                objSalesVO.setFirstname(rs.getString(5));
                objSalesVO.setLastname(rs.getString(6));
                objSalesVO.setShopZone(rs.getString(7));
                objSalesVO.setFullname(rs.getString(5)+" "+rs.getString(6));//added by bhawna on 13th oct
                 //String shopZone=rs.getString(7);
                 //objSalesVO.setShopZone(shopZone);
                objList.add(objSalesVO);
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
 public List fetchShopsbySalesID(Connection con,String salesID)throws SQLException, Exception{
        List objList = new ArrayList();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.prepareStatement("SELECT SHOP_ID FROM SHOP_SALESID_MAPPING WHERE SALESPERSONNEL_ID =?");
            stmt.setString(1,salesID);
            rs = stmt.executeQuery();
                        
            while(rs.next()) {
                ShopSaleVO salesform = new ShopSaleVO();
                salesform.setShop_id(rs.getString(1));
                objList.add(salesform);               
            }
            if(rs.next()){
                objList.add("true");
            }
            
        } catch(SQLException se){
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
        }
        return objList;
    }
 
 public boolean CheckMappingforShopinSalesHierarchy(Connection con,String[] check) throws SQLException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;        
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT * FROM SALES_HIERARCHY WHERE SHOP_ID=?");            
            pstmt.setString(1,check[0]);            
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                status = false;
                return status;            
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
        
        return status;      
        
    }
 
 public boolean CheckforShopinShopSalesmapping(Connection con,String[] check) throws SQLException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;        
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT * FROM SHOP_SALESID_MAPPING WHERE SHOP_ID=?");            
            pstmt.setString(1,check[0]);            
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                status = false;
                return status;            
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
        
        return status;      
        
    }

   

}
    

