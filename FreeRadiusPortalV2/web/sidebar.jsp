<%-- 
    Document   : sidebar
    Created on : Mar 21, 2011, 4:31:23 PM
    Author     : PKMDUsman
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%
            String uname = (String) session.getAttribute("username");
            if (uname == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }

            List uPermissions = (List) session.getAttribute("groupPermissions");
            Iterator permIterator = null;
            permIterator = uPermissions.iterator();
%>



<h3><a href="#">Home</a></h3>
<div>
    <p>
        <a href="home.jsp">Back to Home</a>
    </p>
</div>





<h3><a href="#">Portal User Management</a></h3>
<div>

    <%
                while (permIterator.hasNext()) {
                    String permission = (String) permIterator.next();
                    if (permission.equals("Add Group")) {
    %>
    <p>
        <a href="addGroup.jsp">Add Portal Group</a>
    </p>
    <%      } else if (permission.equals("Add Portal User")) {
    %>
    <p>
        <a href="addPortalUser.jsp">Add Portal User</a>
    </p>
    <%                    }else if (permission.equals("Delete Portal User")) {
    %>
    <p>
        <a href="deletePortalUser.jsp">Delete Portal User</a>
    </p>
    <%                    } else if (permission.equals("Change Permissions")) {
    %>
    <p>
        <a href="selectGroup.jsp">Update Portal Group</a>
    </p>
    <%                    } else if (permission.equals("Change Password")) {
    %>
    <p>
        <a href="changePassword.jsp">Change Password</a>
    </p>
    <%                    }else if (permission.equals("Change Portal User Group")) {
    %>
    <p>
        <a href="changePortalUserGroup.jsp">Update Portal User</a>
    </p>
    <%                    }
                }

    %>

</div>




    
<h3><a href="signout.jsp">Sign Out</a></h3>
<div>
    <p>
        <a href="signout.jsp">Sign Out</a>
    </p>
</div>
