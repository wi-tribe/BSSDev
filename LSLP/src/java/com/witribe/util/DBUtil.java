/*
 * DBUtil.java
 *
 * Created on January 8, 2009, 10:25 AM
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

/**
 *
 * @author SC43278
 */
public class DBUtil {
    
    static Context initContext;
    static Context envContext;
    static DataSource ds;
    static DataSource brmds;
    static{
        try {
            initContext = new InitialContext();
            envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("OraDs");
            brmds = (DataSource)envContext.lookup("OraBRMDs");
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
            System.out.println("brmds"+brmds);
            Connection conn = brmds.getConnection();
            return conn;
        } catch (SQLException se){
             LogUtil.error("Exception "+se.getMessage(),com.witribe.util.DBUtil.class,se); 
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


