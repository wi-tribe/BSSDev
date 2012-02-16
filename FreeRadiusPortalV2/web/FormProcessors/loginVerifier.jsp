<%-- 
    Document   : loginVerifier
    Created on : Mar 28, 2011, 10:01:41 AM
    Author     : PKMDUsman
--%>

<%@page import="DataBase.DBConnectivity"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ page errorPage="../errorPage.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
%>

<%
            if ("POST".equals(request.getMethod())) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                if (username == null || password == null || username.equals("") || password.equals("")) {
                    System.out.println("Username or Password is NULL");
                }
                if (username.equals("") || password.equals("")) {
                    System.out.println("Username or Password is empty");
                }
                String verify;
                DBConnectivity dbObj = new DBConnectivity();
                verify = dbObj.verifyUser(username, password);

                if (verify == null) {
                    response.sendRedirect(response.encodeRedirectURL("../index.jsp?attempt=fail"));
                    return;
                } else if (verify.equals("not found")) {
                    System.out.println("Attempt to login by " + username + " failed because user doesnot exist or password is incorrect");
                    log.info("Attempt to login by " + username + " failed because user doesnot exist or password is incorrect");
                    response.sendRedirect(response.encodeRedirectURL("../index.jsp?attempt=fail"));
                    return;
                } else {
                   // session.setMaxInactiveInterval(6000);
                    session.setAttribute("username", username);
                    session.setAttribute("usergroup", verify);

                    System.out.println(username + " has successfully logged in to the system");
                    log.info(username + " has successfully logged in to the system");

                    List<String> pList = new ArrayList<String>();
                    pList = dbObj.getGroupPermissions(verify);

                    session.setAttribute("groupPermissions", pList);

                    response.sendRedirect(response.encodeRedirectURL("../home.jsp"));
                    return;
                }


            }

%>