/*
 * LeadDAO.java
 *
 * Created on January 8, 2009, 10:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.util.DBUtil_11;
import com.witribe.vo.HotspotVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

/**
 *
 * @author SC43278
 */
public class HotspotDAO {
    
    /** Creates a new instance of LeadDAO */
    public HotspotDAO() {
    }
    public long getMaxKey(Connection con) throws SQLException,Exception{
        long maxKey = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT MAX(ID) from HOTSPOT");
            if(rs.next()) {
               maxKey = rs.getLong(1);
            }
        } catch(SQLException se){
            DBUtil_11.closeResultSet(rs);
            DBUtil_11.closeStatement(stmt);
            throw se;
        } catch(Exception e){
            DBUtil_11.closeResultSet(rs);
            DBUtil_11.closeStatement(stmt);
            throw e;
        } finally{
            DBUtil_11.closeResultSet(rs);
            DBUtil_11.closeStatement(stmt);
        }
        return  maxKey;
    }
    public boolean loginHotspot(HotspotVO objHotspotVO,Connection con) throws SQLException,Exception{
        boolean status = false;
        PreparedStatement pstmt = null;
        try{
            //long key = getMaxKey(con);
            pstmt = con.prepareStatement(
                    "INSERT INTO HOTSPOT (NAME,EMAIL,MOBILE,USERID,PASSWORD,CREATE_DATE)" +
                    "VALUES " +
                    "(?,?,?,?,?,?)");
            
            //pstmt.setString(1, objHotspotVO.getName());
            pstmt.setString(2, objHotspotVO.getEmail());
            pstmt.setString(3, objHotspotVO.getMobile());
            pstmt.setString(4, objHotspotVO.getUserId());
            pstmt.setString(5, objHotspotVO.getPassword());
           java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(6, sqlDate);
           
            if(pstmt.executeUpdate()>0){
                status = true;
            }
            
           
                
        } catch(SQLException se){
           DBUtil_11.closeStatement(pstmt);
           throw se;
        } catch(Exception e){
            DBUtil_11.closeStatement(pstmt);
            throw e;
        } finally{           
           DBUtil_11.closeStatement(pstmt);
        }
        
        return status;
        
    }
    
    
   
}
