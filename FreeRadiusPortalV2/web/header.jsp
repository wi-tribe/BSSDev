<%-- 
    Document   : header
    Created on : Mar 21, 2011, 11:05:26 AM
    Author     : PKMDUsman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page errorPage="errorPage.jsp" %>

<%
            String name = (String) session.getAttribute("username");
            if (name == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }

            List userPermissions = (List) session.getAttribute("groupPermissions");
            Iterator permissionIterator = null;
            permissionIterator = userPermissions.iterator();
%>
<ul>
    <%
                while (permissionIterator.hasNext()) {
                    String permission = (String) permissionIterator.next();
                    if (permission.equals("Add MAC")) {
    %>
    <li><a href="addUser.jsp"><em><b>Add User</b></em></a></li>
    <%      } else if (permission.equals("Search MAC")) {
    %>
    <li><a href="searchUser.jsp"><em><b>Search User</b></em></a></li>
    <%                    } else if (permission.equals("Update MAC")) {
    %>
    <li><a href="updateUser.jsp"><em><b>Update User</b></em></a></li>
    <%                    } else if (permission.equals("Delete MAC")) {
    %>
    <li><a href="deleteUser.jsp"><em><b>Delete User</b></em></a></li>
    <%                    }
                }

    %>


</ul>


