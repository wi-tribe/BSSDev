<%-- 
    Document   : signout
    Created on : Apr 5, 2011, 5:56:38 PM
    Author     : PKMDUsman
--%>
<%
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="errorPage.jsp" %>
<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }

            System.out.println(session.getAttribute("username") + " has logged out of the system");
            log.info(session.getAttribute("username") + " has logged out of the system");
            session.removeAttribute("username");
            session.removeAttribute("usergroup");
            session.removeAttribute("groupPermissions");
            session.invalidate();
            response.sendRedirect(response.encodeRedirectURL("index.jsp"));
            return;

%>