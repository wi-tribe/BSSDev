/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

//import java.io.IOException;
//import javax.servlet.http.HttpServletRequest;
//import javax.xml.ws.WebServiceContext;
//import javax.xml.ws.handler.MessageContext;
import java.io.IOException;
//import org.apache.log4j.*;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
/**
 *
 * @author PKMubasharH
 */
public class MyLog4j{
    
    public void MyLog4j()
    {
        
    }
    
   // static Logger mylogger = Logger.getLogger(MyLog4j.class);
   // static Logger mylogger1 = Logger.getLogger("MyLog4j");
    static Logger mylogger = Logger.getLogger("MyLog4j");
  //  DailyRollingFileAppender file = new DailyRollingFileAppender(new PatternLayout("- %-5p --- %d, %m%n"), ".Dailyrolling.Log", "'.'yyyy-MM-dd");
    DailyRollingFileAppender apender = new DailyRollingFileAppender();
//     private WebServiceContext wsContext;
//    MessageContext mc = wsContext.getMessageContext();
//    HttpServletRequest req = (HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST);
    
    
    public void smsLogger(String title, String logInfo, String details)
    {
      //  mylogger1.addAppender(apender);
        if(details!=null)
        {
            mylogger.info(title+logInfo+"\r\n"+details);
         //   apender.getImmediateFlush();
        }
        
        else
        mylogger.info(title+logInfo);
//        }
   //     apender.getImmediateFlush();

    }
    
        public void smsLog(String sms){
        try {
            
            DailyRollingFileAppender file = new DailyRollingFileAppender(new PatternLayout("- %-5p --- %d, %m%n"), sms+".Dailyrolling.Log", "'.'yyyy-MM-dd");
           
           mylogger.removeAllAppenders();
           mylogger.addAppender(file);
           
//           mylogger.info(var);
//           file.getImmediateFlush();
           //file.
        }
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(MyLog4j.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         
    }
    
    
    //public static void p
    
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
