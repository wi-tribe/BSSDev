/*
 * Mailer.java
 *
 * Created on May 20, 2009, 4:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Wtb.MyAccount;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.*;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.ResourceBundle;
import javax.mail.internet.MimeMultipart;
import java.rmi.RemoteException;
import com.portal.app.util.CustomerValErrorData;
import com.portal.app.util.CustomerValErrorParser;

public class Mailer
{
    private String mPort;
    private String mSMTPHost;
    private String mSMTPUserName;
    private String mSMTPPassword;
    private String mSubject;
    private String mFrom;
    private String mTo;
    private String mBody;
    private String mIsAuthMail;
    
    
    public Mailer ()
    {
        mPort = "";
        mSMTPHost = "";
        mSMTPUserName = "";
        mSMTPPassword = "";
        mSubject = "";
        mFrom = "";
        mTo = "";
        mBody = "";
        mIsAuthMail = "";
    }
    
    public void setSMTPPort (String sPort)
    {
        mPort = sPort;
    }
    
    public void setSMTPHost (String sHost)
    {
        mSMTPHost = sHost;
    }
    
    public void setSMTPUserName (String sUser)
    {
        mSMTPUserName = sUser;
    }
    
    public void setSMTPPassword (String sPwd)
    {
        mSMTPPassword = sPwd;
    }
    
    public void setMailSubject (String sSubject )
    {
        mSubject = sSubject;
    }
    
   
    
    public void setFrom (String sFrom)
    {
        mFrom = sFrom;
    }
    public void setTo (String sTo)
    {
        mTo = sTo;
    }
    public void setBody (String sBody)
    {
        mBody = sBody;
    }
    public void setIsAuthMail (String sAuthMail)
    {
        mIsAuthMail = sAuthMail;
    }
    
    
    public Mailer (String to,String subject,String message)
    {
        this.mTo=to;
        this.mBody=message;
        this.mSubject=subject;
    }
    
    
    
    public String isAuthMail ()
    {
        return mIsAuthMail;
        
    }
    
    
    public boolean sendMail ()
    {
        if(mTo!=null && mFrom !=null && mSubject !=null && mBody !=null )
        {
            
            Properties props = new Properties ();
            props.put ("mail.smtp.host", mSMTPHost );
            props.put ("mail.smtp.port", mPort);
            props.put ("mail.smtp.auth", mIsAuthMail);
            
            Session session1 = Session.getDefaultInstance (props, null);
            MimeMessage message = new MimeMessage (session1);
            
            try
            {
                message.setFrom (new InternetAddress (mFrom));
                message.addRecipient (Message.RecipientType.TO,new InternetAddress (mTo));
                
                message.setSubject (mSubject);
                message.setContent (mBody , "text/html");
                //send mail
                if (isAuthMail ().equals ("true"))
                {
                    System.out.println ("using Authenication.. message..");
                    Transport transport = session1.getTransport ("smtp");
                    transport.connect (mSMTPHost, Integer.parseInt (mPort), mSMTPUserName , mSMTPPassword);
                    transport.sendMessage (message, message.getAllRecipients ());
                    transport.close ();
                }
                else
                {
                    Transport.send (message);
                }
                
                return true;
                
            }
            catch(Exception e)
            {
                return false;
            }
            
        }
        else
        {
            return false;
        }
    }
}