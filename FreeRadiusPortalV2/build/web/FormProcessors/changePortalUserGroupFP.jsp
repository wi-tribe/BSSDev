<%-- 
    Document   : changePortalUserGroupFP
    Created on : Apr 19, 2011, 10:30:06 AM
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
                String group = request.getParameter("group");

                if (newUsername == null) {
                    System.out.println("Username can not be null");
                    response.sendRedirect(response.encodeRedirectURL("../changePortalUserGroup.jsp?updateUser=unempty"));
                } else if (newUsername.equals("")) {
                    System.out.println("username can not be empty");
                    response.sendRedirect(response.encodeRedirectURL("../changePortalUserGroup.jsp?updateUser=unempty"));
                } else if (group == null) {
                    System.out.println("Group can not be null");
                    response.sendRedirect(response.encodeRedirectURL("../changePortalUserGroup.jsp?updateUser=grempty"));
                }  else {
                    DBConnectivity dbObj = new DBConnectivity();
                    String portalUserStatus = dbObj.checkPortalUserStatus(newUsername);
                    if(portalUserStatus.equals("found")){
                        String addResult = dbObj.updatePortalUser(newUsername, group);
                        if(addResult.equals("changed")){
                            System.out.println("Portal user " + newUsername +" has been successfully updated to group " + group +" by " + username);
                            log.info("Portal user " + newUsername +" has been successfully updated to group " + group +" by " + username);
                            response.sendRedirect(response.encodeRedirectURL("../home.jsp?updateUser=success"));
                        }else{
                            System.out.println("Problem in adding " + newUsername + " and group " + group+"  by " + username + " .");
                            log.info("Problem in adding " + newUsername + " and group " + group+"  by " + username + " .");
                            response.sendRedirect(response.encodeRedirectURL("../changePortalUserGroup.jsp?updateUser=problem"));
                        }
                    }else{
                        System.out.println("Attempt to update portal user " + newUsername+" by "+ username + " failed because username doesnot exist");
                        log.info("Attempt to update portal user " + newUsername+" by "+ username + " failed because username doesnot exist");
                        response.sendRedirect(response.encodeRedirectURL("../changePortalUserGroup.jsp?updateUser=doesnotexist"));
                    }

                }
            }

%>