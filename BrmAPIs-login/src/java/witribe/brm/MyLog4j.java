/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package witribe.brm;

import org.apache.log4j.*;
import java.io.IOException;
//import java.util.Date;
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
    
    public void smsLogger(String title, String logInfo, String details)
    {
       //String funName = var;
      // int tid = Tid;
       //mylogger.info("\n"+var+"function: "+Account+" Transection ID is: "+Tid+",  code: "+fun_name + ", Account/CNIC no: "+code);
        if(details!=null)
        {
            mylogger.info(title+logInfo+"\r\n"+details);
        }
        else
        mylogger.info(title+logInfo);
        
        
    } 
    
//    public void log(String header, String message)
//    {
//        mylogger.debug(header+"\r\n"+message+"\r\n");
//    }
//    
//    public void log1(String header, String message)
//    {
//        mylogger.info(header+"\r\n"+message+"\r\n");   
//    }
//    
}
