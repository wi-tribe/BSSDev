<%-- 
    Document   : addPortalUserFP
    Created on : Apr 15, 2011, 2:52:47 PM
    Author     : PKMDUsman
--%>

 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>

<%@ page errorPage="../errorPage.jsp" %>
<%@page import="DataBase.DBConnectivity"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }
%>

<%
            if ("POST".equals(request.getMethod())) {
                String newUsername = request.getParameter("username");
                String userPassword = request.getParameter("password");
                String group = request.getParameter("group");

                if (newUsername == null) {
                    System.out.println("Username can not be null");
                    response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=unempty"));
                } else if (newUsername.equals("")) {
                    System.out.println("username can not be empty");
                    response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=unempty"));
                } else if (userPassword == null) {
                    System.out.println("Password can not be null");
                    response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=pempty"));
                } else if (group == null) {
                    System.out.println("Group can not be null");
                    response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=grempty"));
                } else if (userPassword.length() < 5) {
                    System.out.println("Password can not be less than 5 characters");
                    response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=plenght"));
                } else {
                    DBConnectivity dbObj = new DBConnectivity();
                    String portalUserStatus = dbObj.checkPortalUserStatus(newUsername);
                   // System.out.println(portalUserStatus);
                    if(portalUserStatus.equals("not found")){
                        String addResult = dbObj.addPortalUser(newUsername, userPassword, group);
                        if(addResult.equals("inserted")){
                            System.out.println("Portal user " + newUsername + " with password " +  userPassword+" and group " + group +" has been successfully added by " + username);
                            log.info("Portal user " + newUsername + " with password " +  userPassword+" and group " + group +" has been successfully added by " + username);
                            response.sendRedirect(response.encodeRedirectURL("../home.jsp?adduser=success"));
                        }else{
                            System.out.println("Problem in adding " + newUsername + " with password " +  userPassword + " and group " + group+"  by " + username + " .");
                            log.info("Problem in adding " + newUsername + " with password " +  userPassword + " and group " + group+"  by " + username + " .");
                            response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=problem"));
                        }
                    }else{
                        System.out.println("Attempt to create new portal user " + newUsername+" by "+ username + " failed because username already exist");
                        log.info("Attempt to create new portal user " + newUsername + " by "+ username + " failed because username already exist");
                        response.sendRedirect(response.encodeRedirectURL("../addPortalUser.jsp?adduser=already"));
                    }

                }
            }

%>