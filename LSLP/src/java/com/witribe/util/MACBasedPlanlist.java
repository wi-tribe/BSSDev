/*
 * WitribeBO.java
 *
 * Created on January 8, 2009, 10:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.util;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


/**
 *
 * @author PKAasimN
 */
public class MACBasedPlanlist {

    
    public MACBasedPlanlist() {
    }
    public String GetFilterString(String Model,String DeviceType) throws SQLException, Exception{
        
        Connection con = null;
        String SearchString = null;
       
        try{
            con = DBUtil.getConnection();
            String query = "SELECT upper(string_comp) FROM device_type_check where upper(device_sub_type) like upper(?) and upper(device_model) like upper(?)";
            PreparedStatement prest = con.prepareStatement(query);
            prest.setString(1, DeviceType);
            prest.setString(2, Model);
            ResultSet results = prest.executeQuery();
            
            if(results.next()) {
            SearchString = (String) results.getString(1);
            System.out.println(SearchString);
          }
          prest.close();
          results.close();
        }catch(SQLException se){
           DBUtil.closeConnection(con);
           System.out.println(se);
            throw se;
        } catch(Exception e){
           DBUtil.closeConnection(con);
           System.out.println(e);
            throw e;
        }
        finally{
            DBUtil.closeConnection(con);
        }
        return SearchString;
    }


}
