package witribe.brm;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SC43278
 */
public class DBUtil {
    
	static boolean debug = true;
    static Connection mConn=null;
    private static DBUtil dbInstance = null;
    
    private DBUtil () {
    	
    }
    public static Connection getConnection() throws SQLException {
    	if(mConn == null || mConn.isClosed()) {
    		dbInstance = null;
    		dbInstance = new DBUtil();
    		dbInstance.CreateConnection();
    	} 
    		return mConn;
    }
    public static void closeConnection() throws SQLException {
    	if(mConn != null && !mConn.isClosed()) {
    		mConn.close();
    	}
    	dbInstance = null;
    	//MainClass.logMsg(Level.INFO,"Closed the Connection");
    }
    private void CreateConnection() throws SQLException {
        printDebug("-----------------------------------------");
       
        String driverClass = "";
        Properties prop = new Properties();
        File file=null;
        try {
            
            
           // prop.load(new FileInputStream(new File(URI.create("./DBUtil.properties"))));
         //   Class.forName(prop.getProperty("dbutil.driverClass")).newInstance();
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            driverClass = "oracle.jdbc.driver.OracleDriver";//prop.getProperty("dbutil.driverClass");
            //MainClass.logMsg(Level.INFO,"Attempting connection.");
            printDebug("driverClass: "+driverClass);
            if (connectedToDatabase()) { 
            
                /*
                 * We are within the Oracle JVM and should simply return a
                 * Connection object using the built-in kprb driver. You can
                 * use either of the two URLs to obtain a default connection:
                 *      jdbc:default:connection:
                 *      jdbc:oracle:kprb:
                 */
                
                String connectionURL = "jdbc:default:connection:";
        
                printDebug("-----------------------------------------");
                printDebug("INTERNAL ORACLE JVM (kprb) CONNECTION");
                printDebug("-----------------------------------------");
                printDebug("Loading JDBC Driver  : " + driverClass);
                printDebug("Connecting to        : " + connectionURL);
                printDebug("Database Version     : " + System.getProperty("oracle.server.version"));
                //MainClass.logMsg(Level.INFO,"Created the Connection");
                mConn = DriverManager.getConnection(connectionURL);
                
            } else {
            
                /*
                 * We are not within the Oracle JVM and should create a
                 * Connection Object using parameters from a properties file.
                 */
                
                
                    
                    

               /* String      userName        = prop.getProperty("dbutil.user");
                String      userPassword    = prop.getProperty("dbutil.pwd");
                String      databaseServer  = prop.getProperty("dbutil.host");
                String      listenerPort    = prop.getProperty("dbutil.port");
                String      oracleSID       = prop.getProperty("dbutil.listener");
                String      connectionURL   = "jdbc:oracle:thin:@" + databaseServer + ":" + listenerPort + ":" + oracleSID;
        */
                 String      userName        = "pin";
                String      userPassword    = "pin";
                String      databaseServer  = "10.1.67.141";
                String      listenerPort    = "1521";
                String      oracleSID       = "bsspindb";
                String      connectionURL   = "jdbc:oracle:thin:@" + databaseServer + ":" + listenerPort + ":" + oracleSID;
        
                if (debug) {
                    printDebug("-----------------------------------------");
                    printDebug("THIN CLIENT CONNECTION");
                    printDebug("-----------------------------------------");
                    printDebug("Loading JDBC Driver  : " + driverClass);
                    printDebug("Connecting to        : " + connectionURL + ", " + userName + ", " + userPassword);
                }
                mConn = DriverManager.getConnection(connectionURL, userName, userPassword);
               // MainClass.logMsg(Level.INFO,"Created the Connection");
            }

            mConn.setAutoCommit(false);
            

        } catch (IllegalAccessException e) 
        {
            printDebug(Level.SEVERE+": "+e.toString());
            throw new SQLException("Illegal Access Error"); 
            
        } catch (InstantiationException e) {
        
            printDebug(Level.SEVERE+": "+e.toString());
            throw new SQLException("Instantiation Error"); 
            
        } catch (ClassNotFoundException e) 
        {
            printDebug(Level.SEVERE+": "+e.toString());
            throw new SQLException("Class Not Found"); 
            
        } catch (SQLException e) 
        {
            printDebug(Level.SEVERE+": "+e.toString());
            throw new SQLException("Error loading JDBC Driver"); 
            
      /*  } catch (FileNotFoundException e) 
        {
            printDebug(Level.SEVERE+":FNF: "+e.toString());
			// TODO Auto-generated catch block
        	//MainClass.logMsg(Level.SEVERE,e.toString());
        } catch (IOException e) {
                // TODO Auto-generated catch block
                //MainClass.logMsg(Level.SEVERE,e.toString());*/
        }
		
    }
    	private static boolean connectedToDatabase() {
            String version = System.getProperty("oracle.server.version");
            return (version != null && !version.equals(""));
        }
    	private static void printDebug(String msg) {
    	    
            if (debug) {
                String className = "[OracleConnection] : ";
                System.out.println(Level.INFO + ": " + className + msg);
                //MainClass.logMsg(Level.INFO,className + msg);
            }
        }
    
}


