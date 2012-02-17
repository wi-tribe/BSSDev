package emailManager;

import java.io.File;
import java.io.IOException;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import org.ini4j.Wini;

/**
 * @author Muhammad Usman
 * @date modified 11-04-2011
 * @version 1.0
 */

public class emailSender {


    /*
     * @param subject email Subject
     * @param message Content of email
     * @param receieverAddress receiver addresses
     * @param ClientName name of user
     */
    public synchronized String sendEmail(String subject, String message, String receieverAddress, String ClientName) throws MessagingException{
        try {

            Wini configObj = new Wini(new File("Configration.ini"));
            // System.out.println(configObj.getAll("Customer_Invoices_email"));
            String serverip = configObj.get("Customer_Invoices_email", "serverip");
            String sender_email_address = configObj.get("Customer_Invoices_email", "sender_email_address");
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", serverip);
            Session session = Session.getDefaultInstance(props);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender_email_address, "wi-tribe Pakistan"));

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receieverAddress, ClientName));

            msg.setSubject(subject);
            msg.setContent(message, "text/html");
            Transport.send(msg);
            return "sent";

        } catch (IOException ex) {
                // System.out.println(ex.toString());
                //ex.printStackTrace();
                  return ex.toString();

        }
        catch(MessagingException me){
            throw new MessagingException(me.toString());
        }


    }
}
