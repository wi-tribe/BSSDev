package utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;

/**
 *
 * @author PKFKhan
 */
public class DBManager {

    private Connection conn;
    //private Statement stmt;
    private String host = "10.1.67.42";
    private String port = "1521";
    private String sid = "bsspindb";
    private String username = "pin";
    private String password = "pin";

    /* Opens dataaase connection */
    /**
     * 
     */
    public void openDB() {
        /* Opens database conection */
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

            // The following needs to be edited with
            // your database specifics:
            conn = DriverManager.getConnection("jdbc:oracle:thin:@"
                    + host + ":" + port + ":" + sid, username, password);
            //stmt = conn.createStatement();

        } catch (SQLException e) {
            AppLogger.log(3, "Unable to open Oracle DB connection.\n" + e.toString());
            System.exit(1);     // Exit
        } catch (Exception e) {
            AppLogger.log(3, "Unable to open Oracle DB connection.\n" + e.toString());
            System.exit(1);
        }
    }
    
    /**
     * 
     * @return
     */
    public Connection getConnection(){
        return conn;
    }

    /* Execute select statement */
    /**
     * 
     * @param stmt
     * @param sql
     * @return
     */
    public ResultSet executeQuery(Statement stmt, String sql) {
        try {
             //stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            AppLogger.log(3, "Unable to execute query.\n" + e.toString());
            return null;
        } catch (Exception e) {
            AppLogger.log(3, "Unable to execute query.\n" + e.toString());
            return null;
        }
    }
    /* Execute update/insert statement */

    /**
     * 
     * @param stmt
     * @param sql
     * @return
     */
    public int execute(Statement stmt, String sql) {
        try {
            //stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            AppLogger.log(3, "Unable to update query.\n" + e.toString());
            return 0;
        } catch (Exception e) {
            AppLogger.log(3, "Unable to update query.\n" + e.toString());
            return 0;
        }
    }

    /**
     * 
     */
    public void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            AppLogger.log(3, "Unable to commit Oracle DB connection.\n" + e.toString());
        } catch (Exception e) {
            AppLogger.log(3, "Unable to commit Oracle DB connection.\n" + e.toString());
            System.exit(1);
        }
    }

    /**
     * 
     */
    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            AppLogger.log(3, "Unable to rollback Oracle DB connection.\n" + e.toString());
        } catch (Exception e) {
            AppLogger.log(3, "Unable to rollback Oracle DB connection.\n" + e.toString());
            System.exit(1);
        }
    }

    /* Close datatabase connection */
    /**
     * 
     */
    public void closeDB() {
        try {
            if(conn != null) conn.close();
        } catch (SQLException e) {
            AppLogger.log(3, "Unable to close Oracle DB connection.\n" + e.toString());
        } catch (Exception e) {
            AppLogger.log(3, "Unable to close Oracle DB connection.\n" + e.toString());
            System.exit(1);
        }
    }
    
        public void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public void closeStatement(Statement stmt) throws SQLException {
       if (stmt != null) {
         try {
            stmt.close();
        } catch (SQLException e) {
            throw e;
        }
    }
	
    }
    
    
}
