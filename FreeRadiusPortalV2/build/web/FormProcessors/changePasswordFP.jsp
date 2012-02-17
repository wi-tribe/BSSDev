<%-- 
    Document   : changePasswordFP
    Created on : Apr 8, 2011, 2:59:18 PM
    Author     : PKMDUsman
--%>
 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>
<%@page import="DataBase.DBConnectivity"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@ page errorPage="../errorPage.jsp" %>


<%
            String username = (String) session.getAttribute("username");
            DBConnectivity dbUtil = new DBConnectivity();
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }

            String currentPassword = request.getParameter("currentPwd");
            String newPassword = request.getParameter("newPwd");
            String confirmPassword = request.getParameter("confirmPwd");



            String verificationStatus = dbUtil.verifyUser(username, currentPassword);
            //System.out.println(verificationStatus);
            if(verificationStatus == null){
                System.out.println("Problem in checking user " + username + " status.");
                log.info("Problem in checking user " + username + " status.");
            }
            if (!verificationStatus.equals("not found") && newPassword.equals(confirmPassword)) {
                String changeStatus = dbUtil.changePassword(username, newPassword);
                //System.out.println(changeStatus);
                if(changeStatus.equals("changed")){
                    System.out.println("Password of " + username + " has been successfully changed from " + currentPassword + " to " + newPassword);
                    log.info("Password of " + username + " has been successfully changed from " + currentPassword + " to " + newPassword);
                    response.sendRedirect(response.encodeRedirectURL("../home.jsp?changepassword=success"));
                }else {
                    System.out.println("Attempt to change password by " + username + " failed");
                    log.info("Attempt to change password by " + username + " failed");
                    response.sendRedirect(response.encodeRedirectURL("../changePassword.jsp?changepassword=fail"));
                    }
            } else if (verificationStatus.equals("not found")) {
                System.out.println("Attempt to change password by " + username + " failed because current password was incorrect ");
                log.info("Attempt to change password by " + username + " failed because current password was incorrect ");
                response.sendRedirect(response.encodeRedirectURL("../changePassword.jsp?changepassword=incorrect"));
            }else if(!newPassword.equals(confirmPassword)){
                System.out.println("Attempt to change password by " + username+" failed because new password doesnot match");
                log.info("Attempt to change password by " + username+" failed because new password doesnot match");
                response.sendRedirect(response.encodeRedirectURL("../changePassword.jsp?changepassword=mismatch"));
            }


%>
