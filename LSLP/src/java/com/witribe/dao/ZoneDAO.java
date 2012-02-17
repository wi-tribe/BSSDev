/*
 * ZoneDAO.java
 *
 * Created on February 4, 2009, 8:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.util.DBUtil;
import com.witribe.vo.ZoneVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author HY27465
 */
public class ZoneDAO {
    
    /** Creates a new instance of ZoneDAO */
    public ZoneDAO() {
    }
    public List getZones(Connection con)throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT ADDR_ZONE_CODE, ADDR_ZONE, ADDR_CITY_CODE FROM ZONE");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next())
            {      
                ZoneVO objZoneVO= new ZoneVO();
                objZoneVO.setAddr_zone_code(rs.getInt(1));
                objZoneVO.setAddr_zone(rs.getString(2));
                objZoneVO.setAddr_city_code(rs.getInt(3));
                objList.add(objZoneVO);
            }
        }catch(SQLException se){
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
