/*
 * ProvinceDAO.java
 *
 * Created on February 4, 2009, 3:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;
import com.witribe.util.DBUtil;
import com.witribe.vo.ProvinceVO;
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
public class ProvinceDAO {
    
    /** Creates a new instance of ProvinceDAO */
    public ProvinceDAO() {
    }
    
    public List getProvinces(Connection con)throws SQLException,Exception{
        List objList = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt = con.prepareStatement("SELECT ADDR_PROVINCE FROM PROVINCE");
            rs = pstmt.executeQuery();
            objList = new ArrayList();
           //   ProvinceVO objProvinceVO= new ProvinceVO();
            String province;
            while(rs.next()){
                province = rs.getString("addr_province");
                objList.add(province);
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
