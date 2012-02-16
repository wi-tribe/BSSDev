<%-- 
    Document   : deletePortalUserFP
    Created on : Apr 18, 2011, 6:08:03 PM
    Author     : PKMDUsman
--%>


<%
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
%>

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
                String usernameDelete = request.getParameter("username");


                if (usernameDelete == null) {
                    System.out.println("Username can not be null");
                    response.sendRedirect(response.encodeRedirectURL("../deletePortalUser.jsp?deleteuser=unempty"));
                } else if (usernameDelete.equals("")) {
                    System.out.println("username can not be empty");
                    response.sendRedirect(response.encodeRedirectURL("../deletePortalUser.jsp?deleteuser=unempty"));

                } else {
                    DBConnectivity dbObj = new DBConnectivity();
                    String portalUserStatus = dbObj.checkPortalUserStatus(usernameDelete);
                    if (portalUserStatus.equals("found")) {
                        String deleteStatus = dbObj.deletePortalUser(usernameDelete);
                        if (deleteStatus.equals("deleted")) {
                            System.out.println("Portal user " + usernameDelete + " has been successfully deleted by " + username);
                            log.info("Portal user " + usernameDelete + " has been successfully deleted by " + username);
                            response.sendRedirect(response.encodeRedirectURL("../home.jsp?deleteuser=success"));
                        } else {
                            System.out.println("Problem in deleting portal user " + username + " by " + username);
                            log.info("Problem in deleting portal user " + username + " by " + username);
                            response.sendRedirect(response.encodeRedirectURL("../deletePortalUser.jsp?deleteuser=problem"));
                        }

                    } else {
                        System.out.println("Attempt to delete portal user " + usernameDelete + " by " + username + " failed because username doesnot exist");
                        log.info("Attempt to delete portal user " + usernameDelete + " by " + username + " failed because username doesnot exist");
                        response.sendRedirect(response.encodeRedirectURL("../deletePortalUser.jsp?deleteuser=doesnotexist"));
                    }

                }
            }

%>
