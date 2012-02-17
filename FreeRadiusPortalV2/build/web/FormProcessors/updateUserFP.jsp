<%-- 
    Document   : updateUserFP
    Created on : Apr 4, 2011, 4:06:22 PM
    Author     : PKMDUsman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="DataBase.DBConnectivity"%>
<%@ page errorPage="../errorPage.jsp" %>
 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>

<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }
%>


<%
            if ("POST".equals(request.getMethod())) {
                String MAC = request.getParameter("macSearch");
                String domain = request.getParameter("domain");
                String profile = request.getParameter("profile");
                String currentProfile = request.getParameter("currentProfile");

                String new_MAC = MAC.toUpperCase();
                if (MAC == null ) {
                    System.out.println("MAC is Null");
                    response.sendRedirect(response.encodeRedirectURL("../addUser.jsp"));
                }else if(MAC.equals("")){
                    System.out.println("MAC is empty");
                    response.sendRedirect(response.encodeRedirectURL("../addUser.jsp"));
                }
                

                String userStatus;
                DBConnectivity dbObj = new DBConnectivity();
                userStatus = dbObj.checkUserStatus(MAC);
                if (userStatus == "found") {
                  //  System.out.println(MAC + " found for updation..");
                   // response.sendRedirect(response.encodeRedirectURL("../home.jsp?insert=alreadyExist"));
                    String update_result = dbObj.updateUser(MAC, new_MAC, domain, domain, profile);
                    if (update_result.equals("updated")) {
                        System.out.println(MAC + " has been successfully updated to "+ profile+ " from "+ currentProfile + " by " + username);
                        log.info(MAC + " has been successfully updated to "+ profile+ " from "+ currentProfile + " by " + username);
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?update=success"));
                    } else if (update_result.equals("lenghtProblem")) {
                        System.out.println("Attempt to update " + MAC + " failed because of lenght of MAC by "+ username);
                        log.info("Attempt to update " + MAC + " failed because of lenght of MAC by "+ username);
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?update=macLenght"));
                    } else {
                        System.out.println("Attempt to update " + MAC + " by "+ username + " failed");
                        log.info("Attempt to update " + MAC + " by "+ username + " failed");
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?update=fail"));
                    }


                } else {
                    System.out.println("Attempt to update "+ MAC + " by "+ username + " failed beacuse MAC doesnot exist");
                    log.info("Attempt to update "+ MAC + " by "+ username + " failed beacuse MAC doesnot exist");
                    response.sendRedirect(response.encodeRedirectURL("../home.jsp?update=doesnotExist"));
                }
            }

%>
