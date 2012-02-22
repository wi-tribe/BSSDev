
/*
 * SalesHierarchyDAO.java
 *
 * Created on February 6, 2009, 6:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.dao;

import com.witribe.sales.actionform.SalesHierarchyForm;
import com.witribe.constants.WitribeConstants;
import com.witribe.exception.DuplicateUserException;
import com.witribe.sales.vo.SalesHierarchyVO;
import com.witribe.sales.vo.ShopSaleVO;
import com.witribe.sales.vo.ViewSalesHierarchyVO;
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
 * @author HY27465
 */
public class SalesHierarchyDAO {
    
    /** Creates a new instance of SalesHierarchyDAO */
    public SalesHierarchyDAO() {
    }
    public boolean createHierarchy(SalesHierarchyVO salesform,Connection con) throws SQLException,Exception{
        boolean status = false;
        boolean opcodestatus = false;
        PreparedStatement pstmt = null;
        String childid = salesform.getChild_salespersonnel_id();
        String [] child = childid.split("-");
        con.setAutoCommit(false);
        
        try{
            pstmt = con.prepareStatement(
                    "INSERT INTO SALES_HIERARCHY(PARENT_SALESPERSONNEL_ID,CHILD_SALESPERSONNEL_ID,SHOP_ID,CREATED_DATE) VALUES (?,?,?,?)");
            //String fullname = objSalesVO.getFirstname()+" "+objSalesVO.getLastname();
            //pstmt.setLong(1, ++key);
            pstmt.setString(1, salesform.getParent_salespersonnel_id());
            pstmt.setString(2, child[0]);
            pstmt.setString(3,salesform.getShop_id());
            String shopid = salesform.getShop_id();
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, sqlDate);
            String test1 = salesform.getParent_salespersonnel_id();
            
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;
                if(status && salesform.getShop_id()!=null){
                    SalesPersonnelDAO dao = new SalesPersonnelDAO();
                    opcodestatus=dao.updateShopforSales(child[0],salesform.getShop_id());
                }
                if(opcodestatus && salesform.getShop_id()!=null)
                    con.commit();
                else if(status == false)
                    con.rollback();
            }
            
        } catch(SQLException se){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            con.commit();
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
    }
    
    public List fetchHierarchy(Connection con,int pageNum, HttpServletRequest req) throws SQLException,Exception{
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
        String salesid = (String)usersession.getAttribute(wc.SALES_ID);
        int startRow = 0;
        int endRow = 0;
        String QueryCont = "";
       if (role == Integer.parseInt(wc.TYPE_ADMIN) || role == Integer.parseInt(wc.TYPE_NSM))
       {
           QueryCont = "";
       }
       
       else if (role == Integer.parseInt(wc.TYPE_RSM))
       {          
            QueryCont = "AND SD.SALESPERSONNEL_TYPE > 2 AND UPPER(ADDR_CITY) in (SELECT UPPER(LOCATION) FROM SALES_LOCATION WHERE SALESPERSONNEL_ID="+salesid+")";
       }
       
       else if (role == Integer.parseInt(wc.TYPE_TL))
       {
           QueryCont = "AND SD.SALESPERSONNEL_TYPE > 3 AND SH.PARENT_SALESPERSONNEL_ID="+salesid;           
       }
       
       try{
            startRow = (pageNum*maxRowCount)+1;
            endRow = (pageNum+1)*maxRowCount+1;
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            rs = stmt.executeQuery("SELECT *  FROM (SELECT a.*, rownum RN FROM ("+
                    "SELECT concat(concat(sd.first_name,' '),sd.last_name) AS CHILDNAME, SD.SALESPERSONNEL_ID AS "+
                    "CHILDID,(SELECT concat(concat(first_name,' '),last_name) FROM SALESPERSONNEL_DETAILS WHERE "+
                    " SALESPERSONNEL_ID = SH.PARENT_SALESPERSONNEL_ID) AS PARENTNAME,"+
                    "SH.PARENT_SALESPERSONNEL_ID AS PARENTID, SD.ADDR_SUBZONE AS SUBZONE,"+
                    "SD.ADDR_ZONE AS ZONE, SD.ADDR_CITY AS CITY,SD.ADDR_PROVINCE AS PROVINCE,"+ 
                    "SD.ADDR_COUNTRY AS COUNTRY,SD.SALESPERSONNEL_TYPE,SH.SHOP_ID FROM SALESPERSONNEL_DETAILS SD,"+
                    "SALES_HIERARCHY SH WHERE  SD.SALESPERSONNEL_ID = SH.CHILD_SALESPERSONNEL_ID "+
                    QueryCont +" ORDER BY SD.ADDR_CITY,SD.FIRST_NAME,SD.ADDR_ZONE ) a WHERE rownum <="+endRow+") WHERE rn >="+startRow);
            
            
            totalRows = getSalesHierarchyRowCount(con);
          /*  if(pageNum != WitribeConstants.ZERO){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }*/
            int count = WitribeConstants.ZERO;
            objList = new ArrayList();
            while(rs.next()) {
                ViewSalesHierarchyVO salesform = new ViewSalesHierarchyVO();
                 count++;
               if(count>maxRowCount) {
                   objList.add("true");
                   break;
               }
                salesform.setChildname(rs.getString(1));
                salesform.setChildsalesid(rs.getString(2));
                salesform.setParentname(rs.getString(3));
                salesform.setParentsalesid(rs.getString(4));
                salesform.setSubzone(rs.getString(5));
                salesform.setZone(rs.getString(6));
                salesform.setCity(rs.getString(7));
                salesform.setProvince(rs.getString(8));
                salesform.setCountry(rs.getString(9));
                salesform.setSalestype(rs.getString(10));
                salesform.setShopid(rs.getString(11));
                objList.add(salesform);
               
            }
            
            
        } catch(SQLException se){
            
            se.printStackTrace();
            DBUtil.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            e.printStackTrace();
            DBUtil.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil.closeResultSet(rs);            
            DBUtil.closeStatement(stmt);
        }
        
        return objList;
        
    }
    
    public long getSalesHierarchyRowCount(Connection con) throws SQLException,Exception{
        long rowsCount = WitribeConstants.ROWS_COUNT;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) from SALES_HIERARCHY");
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
    
    public boolean deleteSalesHierarchybyChild(Connection con,String[] check) throws SQLException,Exception{
        boolean status = false;
        boolean  opcodestatus = false;
        PreparedStatement pstmt = null;
        con.setAutoCommit(false);
        
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
                        "DELETE SALES_HIERARCHY WHERE CHILD_SALESPERSONNEL_ID IN "+inString.toString());
                if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                    status = true;
                    if(status){
                        SalesPersonnelDAO dao = new SalesPersonnelDAO();
                        opcodestatus=dao.updateShopforSales(check[0],"");
                        if(opcodestatus)
                            con.commit();
                    }
                    
                }
            }
            
        } catch(SQLException se){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
            con.rollback();
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
            con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;
        
        
    }
    
    public boolean updateSalesHierarchybyChild(Connection con,String parentsalesid, String childsalesid) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        
        try{
            
            pstmt = con.prepareStatement(
                    "UPDATE SALES_HIERARCHY SET PARENT_SALESPERSONNEL_ID=? , MODIFIED_DATE=? WHERE CHILD_SALESPERSONNEL_ID =? ");
            pstmt.setString(1,parentsalesid);
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(2, sqlDate);
            pstmt.setString(3,childsalesid);
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
    
     public boolean updateShopforSalesHierarchy(Connection con, String childsalesid, String shopId) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        con.setAutoCommit(false);
        try{
            
            pstmt = con.prepareStatement(
                    "UPDATE SALES_HIERARCHY SET SHOP_ID=? , MODIFIED_DATE=? WHERE CHILD_SALESPERSONNEL_ID =? ");
            pstmt.setString(1,shopId);
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(2, sqlDate);
            pstmt.setString(3,childsalesid);
            if(pstmt.executeUpdate() > WitribeConstants.ZERO){
                status = true;                
                SalesPersonnelDAO dao = new SalesPersonnelDAO();
                /**
                 *Update the shop on BRM for the selected salesid.
                 **/
                boolean opcodestatus=dao.updateShopforSales(childsalesid,shopId);
                status = opcodestatus;
                if(opcodestatus)
                {
                    con.commit();
                }
                else{
                    con.rollback();
                }
                    
            }                     
            
        } catch(SQLException se){
           // con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
            throw se;
        } catch(Exception e){
          //  con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
            throw e;
        } finally{
           // con.setAutoCommit(true);
            DBUtil.closeStatement(pstmt);
        }
        
        return status;        
        
    }
}
