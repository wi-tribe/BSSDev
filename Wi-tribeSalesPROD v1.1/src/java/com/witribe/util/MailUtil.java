/*
 * DBUtil.java
 *
 * Created on January 8, 2009, 10:25 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.util;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author SC43278
 */
public class MailUtil {
    
        
    
    public static javax.mail.Session getSession() throws NamingException {
        
       Context initCtx = new InitialContext();
       Context envCtx = (Context) initCtx.lookup("java:comp/env");
       return (javax.mail.Session)envCtx.lookup("mail/Session");
    }
    public static void sendMail(String from,String to,String subject,String content) throws MessagingException,NamingException {
       
       Message message = new MimeMessage(getSession());
       message.setFrom(new InternetAddress());
       Address toAddr[] = new Address[1]; 
       toAddr[0] =  new InternetAddress(to);
       message.setRecipients(Message.RecipientType.TO, toAddr);
       message.setSubject(subject);
       message.setContent(content, "text/plain");
       Transport.send(message);
       
    }
   
    
}


