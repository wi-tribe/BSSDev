<%@ page contentType="text/html;charset=UTF-8" language="java"  import=" java.io.*, javax.mail.*,javax.mail.internet.*, javax.mail.event.*,java.net.*,java.util.* " %>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <h1>JSP Page</h1>
    
    <%--
    This example uses JSTL, uncomment the taglib directive above.
    To test, display the page like this: index.jsp?sayHello=true&name=Murphy
    --%>
    <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
    
    </body>
</html>
<% 
try

        {

           Properties props=new Properties();

           props.put("mail.smtp.host","10.1.82.10");   //  'localhost' for testing
           props.put("mail.smtp.auth", true);
           props.put("mail.debug", "true");

             javax.mail.Session   session1  =  Session.getDefaultInstance(props,null);

           String s1 = request.getParameter("text1"); //sender (from)

           String s2 = request.getParameter("text2");

           String s3 = request.getParameter("text3");

           String s4 = request.getParameter("area1");

     Message message =new MimeMessage(session1);

      message.setFrom(new InternetAddress(s1));

      message.setRecipients

              (Message.RecipientType.TO,InternetAddress.parse(s2,false));

           message.setSubject(s3);

           message.setText(s4);  
           // Get session        
        session.setDebug(true);
        Transport transport = session.getTransport("smtp");
                transport.connect("10.1.82.10","satyam@wi-tribe.pk","scs123");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();  
            

//           Transport.send(message);

           out.println("mail has been sent");

        }

        catch(Exception ex)

        {

           System.out.println("ERROR....."+ex);

        }
%>
