/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author PKFKhan
 */
public class AppLogger {
    private static Logger theLogger = Logger.getLogger("sms_brm_log");
    private static Handler fileHandle;
    
    public static int INFO      = 1;
    public static int WARNING   = 2;
    public static int SEVERE    = 3;
    
    /**
     * 
     */
    public static void init(){
        try{
            Date resTime = new Date();
            SimpleDateFormat df =  new SimpleDateFormat();
            df.applyPattern("-yyyy-MM-dd");
            fileHandle = new FileHandler("sms_brm"+df.format(resTime)+".log",true);
            fileHandle.setFormatter(new SimpleFormatter());
            fileHandle.setLevel(Level.INFO);
            theLogger.addHandler(fileHandle);
            
        }catch(Exception ex){
            System.err.print(ex.getMessage());
        }
        
    }
    
    /**
     * 
     * @param severity
     * @param message
     */
    public static void log(int severity, String message){
        try{
            if(severity == 1)   // info
                theLogger.info(message);
            if(severity == 2)   // warn
                theLogger.warning(message);
            if(severity == 3)   // sever
                theLogger.severe(message);
            
        }catch(Exception ex){
            System.err.print(ex.getMessage());
        }
    }
    
    /**
     * 
     */
    public static void close(){
        try{
            fileHandle.close();
        }catch(Exception ex){
            System.err.print(ex.getMessage());
        }
        
    }
}
