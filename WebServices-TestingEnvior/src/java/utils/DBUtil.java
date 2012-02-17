package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {

    static Context initContext;
    static Context envContext;
    static DataSource ds;
    static DataSource brmds;
    DBManager dbman = new DBManager();
    
    public void DbConnfun()
    {
       // dbman.openDB();
       // dbman.getConnection().createStatement();
    }
    
    

    static {
        try {
            initContext = new InitialContext();
            envContext = (Context) initContext.lookup("java:/comp/env");
            brmds = (DataSource) envContext.lookup("OraBRMDs");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, NamingException {
        Connection conn = ds.getConnection();
        return conn;
    }

    public static Connection getBRMConnection() throws SQLException, NamingException {
        try {
            Connection conn = brmds.getConnection();
            System.out.println("conn:::" + conn);
            return conn;
        } catch (SQLException se) {
            throw se;
        }
    }

    public static int execute(Statement stmt, String sql) {
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            AppLogger.log(3, "Unable to update query.\n" + e.toString());
            return 0;
        } catch (Exception e) {
            AppLogger.log(3, "Unable to update query.\n" + e.toString());
            return 0;
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
    
  

    public static void main(String args[]) throws Exception {
        Connection con = null;
       DBUtil dbutil = new DBUtil();
       dbutil.DbConnfun();
        try {
            con = getConnection();
        } catch (SQLException se) {
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
