/*
 * SubzoneDAO.java
 *
 * Created on February 4, 2009, 8:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.util.DBUtil;
import com.witribe.vo.SubzoneVO;
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
public class SubzoneDAO {
    
    /** Creates a new instance of SubzoneDAO */
    public SubzoneDAO() {
    }
    public List getSubzones(Connection con)throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT ADDR_SUBZONE_CODE, ADDR_SUBZONE, ADDR_ZONE_CODE FROM SUBZONE");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
            
            
            while(rs.next())
            {  
                SubzoneVO objSubzoneVO= new SubzoneVO();
                objSubzoneVO.setAddr_subzone_code(rs.getInt(1));
                objSubzoneVO.setAddr_subzone(rs.getString(2));
                objSubzoneVO.setAddr_zone_code(rs.getInt(3));
                objList.add(objSubzoneVO);
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