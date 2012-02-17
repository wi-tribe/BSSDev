package utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author PKANawaz
 */
public class SendMail {
    
    MyLog4j callLogger = new MyLog4j();

    public void sendMail(String massege, String mailTo, String mailFrom, String Subject)
    {
        try
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", "10.1.81.22");
            //props.put("mail.smtp.port", "25");
            //props.put("mail.smtp.user", "mubashar.hassan@pk.wi-tribe.com");
            //props.put("mail.smtp.pwd", ")(*&poiu0987");
            
            
            Session mailSession = Session.getInstance(props);
            
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(mailFrom));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            msg.setSubject(Subject);
            msg.setContent(massege,"text/html");
            Transport.send(msg);

            System.out.println("mail sent");
         }
         catch (Exception ioe)
             {
             //ioe.printStackTrace();
                callLogger.smsLogger("Function:sendMail(), ", "Exception Line:42, ", ioe.toString());
         }
    }
}
