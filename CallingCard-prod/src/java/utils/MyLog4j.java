/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.apache.log4j.*;
import java.io.IOException;
/**
 *
 * @author PKMubasharH
 */
public class MyLog4j{
    
    public void MyLog4j()
    {
        
    }
    
    static Logger mylogger = Logger.getLogger(MyLog4j.class);
    DailyRollingFileAppender apender = new DailyRollingFileAppender();
    
    public void smsLogger(String var, String fun_name, String timestamp, String voucherpin) throws IOException
    {
//       String funName = var;
//       int tid = Tid;
       mylogger.info("\n"+var+"Function: "+fun_name+" TimeStamp is: "+timestamp+",  VoucherPin is: "+voucherpin);
    } 
    
    public void log(String header, String message)
    {
        mylogger.debug(header+"\r\n"+message+"\r\n");
    }
    
    public void log1(String header, String message)
    {
        mylogger.info(header+"\r\n"+message+"\r\n");   
    }
    
}
