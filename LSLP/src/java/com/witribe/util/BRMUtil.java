/*
 * BRMUtil.java
 *
 * Created on March 31, 2011, 10:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.util;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ES61060
 */
public class BRMUtil  implements java.io.Serializable {
    public static final Logger logger =Logger.getLogger("DepartmentLog");// For log4j implementation
    
    /** Creates a new instance of BRMUtil */
    public BRMUtil() {
      //  PropertyConfigurator.configure("log4j.properties");
    }
     static Context initContext;
    static Context envContext;
    static DataSource ds;
    static DataSource brmds;
    
    static{
        try {
            initContext = new InitialContext();
            System.out.println("initContext"+initContext.getEnvironment());
            envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("OraDs");
            brmds = (DataSource)envContext.lookup("OraBRMDs");
            logger.info("BRM DB Context.."+brmds);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }
           
    public static Connection getConnection() throws SQLException,NamingException {
            
        Connection conn = ds.getConnection();
        return conn;
    }
    public static Connection getBRMConnection() throws SQLException,NamingException {
        try{
            //Context initContext = new InitialContext();
            //Context envContext  = (Context)initContext.lookup("java:/comp/env");
            logger.info("getBRMConnection..1");
            System.out.println("getBRMConnection..1");
            Connection conn = brmds.getConnection();
            logger.info("getBRMConnection..2");
            return conn;
        } catch (SQLException se){
             logger.error("Exception "+se.getMessage()); 
            throw se;
        }
    }
    public static void closeConnection(Connection con) throws SQLException {
        
        if (con != null && !con.isClosed()) {
            try {
                con.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        
    }
    
    public static void closeStatement(Statement stmt) throws SQLException {
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        
    }
    public static void closePreparedStatement(PreparedStatement pstmt) throws SQLException {
        
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        
    }
    public static void closeResultSet(ResultSet rs) throws SQLException {
        
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        
    }
    
    public static void main(String args[])throws Exception{
        Connection con = null;
        try{
            con = getConnection();
        } catch(SQLException se){
            se.printStackTrace();
        } finally {
            
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }
}

