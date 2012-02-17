<%-- 
    Document   : addUserFP
    Created on : Mar 28, 2011, 12:49:01 PM
    Author     : PKMDUsman
--%>
<%@page import="DataBase.DBConnectivity"%>
<%
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page errorPage="../errorPage.jsp" %>

<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }
%>

<%
            if ("POST".equals(request.getMethod())) {
                String MAC = request.getParameter("macAdd");
                String profile = request.getParameter("profile");
                String domain = request.getParameter("Domain");

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
                    System.out.println("Attempt to provision " + MAC + " at profile "+ profile+" by user " + username + " failed beacuse it already exist");
                    log.info("Attempt to provision " + MAC + " at profile "+ profile+" by user " + username + " failed beacuse it already exist");
                    response.sendRedirect(response.encodeRedirectURL("../home.jsp?insert=alreadyExist"));


                } else {
                    //System.out.println(MAC + " doesnot exist...");
                    String insertionStatus = dbObj.addMAC(MAC, profile, domain);
                    if (insertionStatus.equals("inserted")) {
                        System.out.println(MAC + " has been successfully provisoned at profile " +  profile+" by " + username);
                        log.info(MAC + " has been successfully provisoned at profile " +  profile+" by " + username);
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?insert=success"));
                    } else if (insertionStatus.equals("lenghtProblem")) {
                        System.out.println("Attempt to provision " + MAC + "at profile " + profile +" failed due to wrong lenght of MAC");
                        log.info("Attempt to provision " + MAC + "at profile " + profile +" failed due to wrong lenght of MAC");
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?insert=macLenght"));
                    } else {
                        System.out.println("Attempt to provision " + MAC + " at profile " + profile+ " failed");
                        log.info("Attempt to provision " + MAC + " at profile " + profile+ " failed");
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?insert=fail"));
                    }
                }
            }

%>
