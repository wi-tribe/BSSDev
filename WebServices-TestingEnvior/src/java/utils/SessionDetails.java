/*
 * NewClass.java
 *
 * Modified on September 20, 2010, 10:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utils;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.*;

/**
 *
 * @author MK64021
 */
public class SessionDetails {
//    private int totalUpload;
//    private int totalDownload;
    MyLog4j logg = new MyLog4j();

    /** Creates a new instance of NewClass */
    public SessionDetails() {
      }

    
        public String getCreditDetails(String customerID, Float dealPrice) throws SQLException,Exception
    {
        //String checkk = customerID;
        //System.out.println("***********8 in session.java enter getCredit fun "+checkk);
        float dueAmount=0;
        float totalAmount=0;
        float creditLimit=0;
        float checkPrice=0;
        
        String error = "You have exhausted your credit limit, please clear your outstanding dues in order to purchase an add-on";
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = DBUtil.getBRMConnection();
        //System.out.println("*********** in session.java connection creation: "+con);
        try {
            //System.out.println("***********8 in session.java after try "+con);
            String sql = "SELECT * FROM VU_ADDON_PURCHASE WHERE ACCOUNT_NO ='" + customerID + "'";
            //System.out.println("*********** in session.java SQL querry value "+sql);
            stmt = con.createStatement();
            //System.out.println("*********** in session.java stmt value "+stmt);
            rs = stmt.executeQuery(sql);
            //System.out.println("*********** in session.java rs value: "+rs);
//            if (MyDefaultLog.doLog(8)) {
//                MyDefaultLog.log(this,8,"Query is " + sql);
//            }
            while (rs.next()) {
                dueAmount = rs.getFloat("DUE_AMOUNT");
            //    System.out.println("***** in session.java due amount is: "+dueAmount);
                totalAmount = rs.getFloat("TOTAL_ITEMS");
              //  System.out.println("***** in session.java total amount is: "+totalAmount);
                creditLimit = rs.getFloat("CREDIT_LIMIT");
             //   System.out.println("***** in session.java credit limit is: "+creditLimit);
                checkPrice = dueAmount + totalAmount - creditLimit;
             //   System.out.println("***** in session.java check price is= "+checkPrice);
                
                if((checkPrice + dealPrice) <= 0)
                {
                    error="";
                    logg.smsLogger("","Function:getCreditDetails, Line:70 ",error);
                }else{
                    if(checkPrice>0) {
                        error = "You have exhausted your credit limit, please clear your outstanding dues in order to purchase an add-on";
                        logg.smsLogger("","Function:getCreditDetails, Line:74 ",error);
                    } else if(checkPrice<0) {
                        error = "You do not have sufficient credit to purchase the selected add-on.<br> Your available credit is PKR " + Math.abs(checkPrice);
                        logg.smsLogger("","Function:getCreditDetails, Line:77 ",error);
                    }
                }
            }
        } catch(SQLException se) {
            logg.smsLogger("","SQLException:Sessiondetails, Line:82", se.toString());
            throw se;
        } catch(Exception e) {
            logg.smsLogger("","Exception:Sessiondetails, Line:85", e.toString());
            throw e;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(con);
        }
        //System.out.println("CL Result:"+error);
        //  return "";      
        return error;
    }
        
          public int insertNetNannyDetails(String customerID, String email, String license, String status)
    {
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        int rows = 0;

        try {
            con = DBUtil.getBRMConnection();
            String sql = "INSERT INTO WTB_NETNENNY_CUSTOMERS(CUSTOMER_ID, EMAIL, LICENSE_NO, STATUS, CREATED_T) VALUES('" + customerID + "','" + email + "','" + license + "','" + status + "',sysdate)";
            stmt = con.createStatement();
            rows = DBUtil.execute(stmt, sql);
        } catch(SQLException se) {
                        
            logg.smsLogger("SQLException:SessionDetails Line:112 ","", se.toString());        
        } catch(Exception e) {
            logg.smsLogger("Exception:SessionDetails Line:114 ","", e.toString());
        } finally {
            try {
                DBUtil.closeResultSet(rs);
                DBUtil.closeStatement(stmt);
                DBUtil.closeConnection(con);
            } catch(SQLException ex) {
                logg.smsLogger("Exception:SessionDetails Line:121 ","", ex.toString());
            }
        }

        return rows;
    }
        
}


