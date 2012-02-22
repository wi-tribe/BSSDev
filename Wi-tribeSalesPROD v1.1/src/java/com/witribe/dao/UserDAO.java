/*
 * LeadDAO.java
 *
 * Created on January 8, 2009, 10:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.exception.DuplicateUserException;
import com.witribe.sales.vo.SalesPersonnelVO;
import com.witribe.util.DBUtil;
import com.witribe.vo.LeadVO;
import com.witribe.vo.UserRegVO;
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
public class UserDAO {
    
    /** Creates a new instance of LeadDAO */
    public UserDAO() {
    }
    public boolean checkDuplicate(Connection con,String userId) throws SQLException,Exception{
        boolean isDuplicate = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT USERID from USER_DETAILS where USERID=?");
            pstmt.setString(1,userId);
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
   /* public UserRegVO validateUser(UserRegVO objUserRegVO,Connection con) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select USERID,PASSWORD,SALESPERSONNEL_TYPE from SALESPERSONNEL_DETAILS where USERID=? and PASSWORD=?");
            pstmt.setString(1,objUserRegVO.getUserId());
            pstmt.setString(2,objUserRegVO.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               objUserRegVO.setRole(rs.getString(3));
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
        return  objUserRegVO;
    }*/
    
  /*   public UserRegVO validateUser(UserRegVO objUserRegVO,Connection con) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select USERID,PASSWORD,SALESPERSONNEL_TYPE from SALESPERSONNEL_DETAILS where USERID=? and PASSWORD=?");
            pstmt.setString(1,objUserRegVO.getUserId());
            pstmt.setString(2,objUserRegVO.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               objUserRegVO.setRole(rs.getString(3));
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
        return  objUserRegVO;
    }*/
    
     public SalesPersonnelVO validateUser(SalesPersonnelVO objSalesVO,Connection con) throws SQLException,Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("select USERID,PASSWORD,SALESPERSONNEL_TYPE," +
                    "ADDR_PLOT, ADDR_STREET, ADDR_SUBZONE, ADDR_ZONE, ADDR_CITY, " +
                    "ADDR_PROVINCE, ADDR_COUNTRY, ADDR_ZIP,SALESPERSONNEL_ID " +
                    "from SALESPERSONNEL_DETAILS where USERID=? and PASSWORD=?");
            pstmt.setString(1,objSalesVO.getUserId());
            pstmt.setString(2,objSalesVO.getPassword());
            rs = pstmt.executeQuery();
            if(rs.next()) {
               objSalesVO.setSalestype(rs.getString(3));
               objSalesVO.setPlot(rs.getString(4));
               objSalesVO.setStreet(rs.getString(5));
               objSalesVO.setSubzone(rs.getString(6));
               objSalesVO.setZone(rs.getString(7));
               objSalesVO.setCity(rs.getString(8));
               objSalesVO.setProvince(rs.getString(9));
               objSalesVO.setCountry(rs.getString(10));
               objSalesVO.setZip(rs.getString(11));
               objSalesVO.setSalesId(rs.getString(12));
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
        return  objSalesVO;
    }
    public boolean createUser(UserRegVO objUserRegVO,Connection con) throws SQLException,DuplicateUserException,Exception{
        boolean status = true;
        PreparedStatement pstmt = null;
        con.setAutoCommit(false);
        try{
            boolean duplicate = checkDuplicate(con,objUserRegVO.getUserId());
            if(duplicate)
                throw new DuplicateUserException();
            
            pstmt = con.prepareStatement(
                    "INSERT INTO USER_DETAILS (NAME,EMAIL,MOBILE,AREA,USERID,CREATE_DATE)"+
                    "VALUES (?,?,?,?,?,?)");
            
            pstmt.setString(1, objUserRegVO.getName());
            pstmt.setString(2, objUserRegVO.getEmail());
            pstmt.setString(3, objUserRegVO.getMobile());
            pstmt.setString(4, objUserRegVO.getArea());
            pstmt.setString(5, objUserRegVO.getUserId());
            java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(6, sqlDate);
           
            if(pstmt.executeUpdate()<=0){
                status = false;
            }
            pstmt = con.prepareStatement(
                    "INSERT INTO USERS(USERID,PASSWORD,CREATE_DATE)"+
                    "VALUES (?,?,?)");
            
            pstmt.setString(1, objUserRegVO.getUserId());
            pstmt.setString(2, objUserRegVO.getPassword());
            pstmt.setTimestamp(3, sqlDate);
           
            if(pstmt.executeUpdate() <= 0 ){
                status = false;
            }
            pstmt = con.prepareStatement(
                    "INSERT INTO ROLES(USERID,ROLE_NAME,CREATE_DATE)"+
                    "VALUES (?,?,?)");
            
            pstmt.setString(1, objUserRegVO.getUserId());
            pstmt.setString(2, objUserRegVO.getRole());
            pstmt.setTimestamp(3, sqlDate);
           
            if(pstmt.executeUpdate() <= 0 ){
                status = false;
            }
            
            if(status) {
             con.commit();
            }
            con.rollback();    
        } catch(SQLException se){
           con.rollback();
           DBUtil.closeStatement(pstmt);
           throw se;
        } catch(DuplicateUserException se){
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
    
    
    public UserRegVO getUser(String UserId,Connection con) throws SQLException,Exception{
        UserRegVO objUserVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
             pstmt = con.prepareStatement(
                   "SELECT  USERID,NAME," +
                   "EMAIL,MOBILE," +
                   "AREA,CREATE_DATE FROM USER_DETAILS WHERE USERID = ?"); 
             pstmt.setString(1,UserId);
             rs = pstmt.executeQuery();
            if(rs.next()){
               objUserVO = new UserRegVO();
               objUserVO.setUserId(rs.getString(1));
               objUserVO.setName(rs.getString(2));
               objUserVO.setEmail(rs.getString(3));
               objUserVO.setMobile(rs.getString(4));
               objUserVO.setArea(rs.getString(5));
               objUserVO.setCreateDate(rs.getString(6));
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
        
        return objUserVO;
        
    }
    
    public List getUsers(Connection con) throws SQLException,Exception{
        List objList = null;
        UserRegVO objUserVO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement(
                    "SELECT  USERID,NAME," +
                   "EMAIL,MOBILE," +
                   "AREA,CREATE_DATE FROM USER_DETAILS" +
                    " ORDER BY  CREATE_DATE asc");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            int count=0;
            while(rs.next()){
                LeadVO objLeadVO = new LeadVO();
                objUserVO = new UserRegVO();
               objUserVO.setUserId(rs.getString(1));
               objUserVO.setName(rs.getString(2));
               objUserVO.setEmail(rs.getString(3));
               objUserVO.setMobile(rs.getString(4));
               objUserVO.setArea(rs.getString(5));
               objUserVO.setCreateDate(rs.getString(6));
                objList.add(objUserVO);
                count++;
                if(count==5)
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
    public long getUserRowCount(Connection con) throws SQLException,Exception{
        long rowsCount = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT count(*) from USER_DETAILS");
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
    public List getNextUsers(Connection con,int pageNum) throws SQLException,Exception{
        List objList = null;
        Statement pstmt = null;
        ResultSet rs = null;
        int maxRowCount=5;
        long totalRows;
        try{
            pstmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //ScrollableResultSet sr= pstmt.executeQuery();
            
            rs = pstmt.executeQuery("SELECT  USERID,NAME," +
                   "EMAIL,MOBILE," +
                   "AREA,CREATE_DATE FROM USER_DETAILS" +
                    " ORDER BY  CREATE_DATE asc");
            // Check ResultSet's scrollability
           
            totalRows = getUserRowCount(con);
            if(pageNum != 0){
                pageNum*=maxRowCount;
                if(pageNum < totalRows){
                    boolean b=rs.next();
                    rs.relative(pageNum-1);
                }
            }
            
            objList = new ArrayList();
            int count=0;
            while(rs.next()){
                UserRegVO objUserVO = new UserRegVO();
                   objUserVO = new UserRegVO();
                   objUserVO.setUserId(rs.getString(1));
                   objUserVO.setName(rs.getString(2));
                   objUserVO.setEmail(rs.getString(3));
                   objUserVO.setMobile(rs.getString(4));
                   objUserVO.setArea(rs.getString(5));
                   objUserVO.setCreateDate(rs.getString(6));
                    objList.add(objUserVO);
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
   
}
