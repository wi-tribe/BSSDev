<%-- 
    Document   : addUserGroup
    Created on : Apr 14, 2011, 3:52:43 PM
    Author     : PKMDUsman
--%>

 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>
<%@page import="DataBase.DBConnectivity"%>
<%@ page errorPage="../errorPage.jsp" %>
<%
            String username = (String) session.getAttribute("username");
            DBConnectivity dbUtil = new DBConnectivity();
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }

            String groupname = request.getParameter("groupName");
            String[] permissions = request.getParameterValues("permission[]");

            if (groupname == null) {
                System.out.println("Group name can not be null.");
                response.sendRedirect(response.encodeRedirectURL("../addGroup.jsp?result=grpempty"));
            } else if (groupname.equals("")) {
                System.out.println("Group name can not be empty.");
                response.sendRedirect(response.encodeRedirectURL("../addGroup.jsp?result=grpempty"));
            } else if (permissions == null) {
                System.out.println("Atleast select one permission for the group");
                response.sendRedirect(response.encodeRedirectURL("../addGroup.jsp?result=prempty"));
            } else {
                String groupStatus = dbUtil.checkGroupStatus(groupname);
                if (groupStatus.equals("found")) {
                    System.out.println("Attempt to create new group " + groupname + " by " + username + " failed because it already exist.");
                    log.info("Attempt to create new group " + groupname + " by " + username + " failed because it already exist.");
                    response.sendRedirect(response.encodeRedirectURL("../addGroup.jsp?result=grpexist"));
                } else {
                    String insertGroupStatus = dbUtil.addGroup(groupname);
                    if (insertGroupStatus.equals("inserted")) {
                        for (int i = 0; i < permissions.length; i++) {
                            String addPermissionStatus = dbUtil.addGroupPermission(groupname, permissions[i]);
                            if (!addPermissionStatus.equals("inserted")) {
                                System.out.println("Problem in adding permission " + permissions[i] + " to " + groupname + " by " + username);
                                log.info("Problem in adding permission " + permissions[i] + " to " + groupname + " by " + username);
                                response.sendRedirect(response.encodeRedirectURL("../addGroup.jsp?result=problem"));
                            }
                        }
                        System.out.println("Group " + groupname + " has been successfully created by "+ username + " with permissions mentioned below.");
                        log.info("Group " + groupname + " has been successfully created by "+ username + " with permissions mentioned below.");
                        for(int i=0; i<permissions.length; i++){
                            System.out.println(groupname + " - "+ permissions[i]);
                            log.info(groupname + " - "+ permissions[i]);
                        }
                        response.sendRedirect(response.encodeRedirectURL("../home.jsp?result=success"));
                    } else {
                        System.out.println("Problem in inserting new group " + groupname +" by " + username + " ..please try again");
                        log.info("Problem in inserting new group " + groupname +" by " + username + " ..please try again");
                        response.sendRedirect(response.encodeRedirectURL("../addGroup.jsp?result=problem"));
                    }
                }
            }
%>
