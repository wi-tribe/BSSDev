<%-- 
    Document   : changeGroupPermission
    Created on : Apr 15, 2011, 5:21:13 PM
    Author     : PKMDUsman
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="DataBase.DBConnectivity"%>
<%@ page errorPage="errorPage.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%

            Iterator gIterator = null;
            Iterator pIterator = null;
            boolean access = false;
            String[] permissonsAllowed = null;
            String groupName = "";
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            } else {

                List permissions = (List) session.getAttribute("groupPermissions");
                Iterator permissionsIterator = null;
                permissionsIterator = permissions.iterator();
                while (permissionsIterator.hasNext()) {
                    String permission = (String) permissionsIterator.next();
                    if (permission.equals("Change Permissions")) {
                        access = true;
                    }
                }

                if (access == true) {
                    if ("POST".equals(request.getMethod())) {
                        groupName = request.getParameter("group");
                    } else if ("GET".equals(request.getMethod())) {
                        groupName = request.getParameter("groupname");
                        System.out.println("GROUPNAME" + groupName);
                    }
                    DBConnectivity dbUtil = new DBConnectivity();
                    List<String> gList = new ArrayList<String>();
                    List<String> pList = new ArrayList<String>();

                    gList = dbUtil.getAvailableOperations();
                    gIterator = gList.iterator();


                    pList = dbUtil.getPermissions(groupName);
                    pIterator = pList.iterator();

                    permissonsAllowed = new String[pList.size()];
                    int i = 0;
                    while (pIterator.hasNext()) {
                        permissonsAllowed[i] = (String) pIterator.next();
                        i++;
                    }


                } else {
                    response.sendRedirect(response.encodeRedirectURL("accessDenied.jsp"));
                }

            }

%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <%@ include file="favicon.jsp" %>
        <title>
            <%@ include file="title.jsp" %>
        </title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Content-Style-Type" content="text/css" />


        <link href="style.css" rel="stylesheet" type="text/css" />
        <link type="text/css" href="jquery-ui-1.8.11.custom.css" rel="stylesheet" />

        <script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.11.custom.min.js"></script>

        <script type="text/javascript">

            function setFocus()
            {
                document.getElementById("groupName").focus();
            }

            $(document).ready(function(){
                $("#changeGroupPermission").validate({
                    rules: {
                        groupNames: {
                            required: true
                        }
                    },
                    messages: {
                        groupNames: {
                            required: "Group name should not be empty"
                        }
                    }
                })

            });

        </script>

        <script type="text/javascript">
            $(function() {
                $( "#accordion" ).accordion();
            });
        </script>

    </head>

    <body id="page1" onload="setFocus()" >
        <div class="tail-top">
            <div id="main">
                <div class="box">
                    <div class="border-top">
                        <div class="border-right">
                            <div class="border-bot">
                                <div class="border-left">
                                    <div class="left-top-corner">
                                        <div class="right-top-corner">
                                            <div class="right-bot-corner">
                                                <div class="left-bot-corner">
                                                    <div class="bg">
                                                        <div class="inside indent">
                                                            <div class="wrapper">
                                                                <!-- content -->
                                                                <div id="content">

                                                                    <!-- menu start -->
                                                                    <div id="menu">

                                                                        <%@ include file="header.jsp" %>


                                                                    </div>
                                                                    <!-- menu end -->


                                                                    <!-- code start here -->


                                                                    <%



                                                                                if (request.getParameter("changeStatus") != null) {
                                                                                    if (request.getParameter("changeStatus").equals("pempty")) {
                                                                                        out.println("<font size=2 color=red><b>Atleast one permission should be selected.</b></font>");
                                                                                    } else if (request.getParameter("changeStatus").equals("problem")) {
                                                                                        out.println("<font size=2 color=red><b>Problem updating group,please try again.</b></font>");
                                                                                    }
                                                                                }


                                                                    %>




                                                                    <form name="changeGroupPermission" id="changeGroupPermission" action="FormProcessors/changeGroupPermissionsFP.jsp" method="POST"  >

                                                                        <table style="font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                            <tr>
                                                                                <td > Group Name : </td>
                                                                                <td colspan="2"> <input name="groupName" value="<%= groupName%>" size=15 readonly type="text" STYLE=" font-family: Verdana;"/> </td>
                                                                            </tr>


                                                                            <tr>
                                                                                <td > Change permissions  : </td>

                                                                            </tr>



                                                                            <tr>

                                                                                <%
                                                                                            int count = 0;
                                                                                            boolean permissionStatus = false;
                                                                                            String operation = null;
                                                                                            while (gIterator.hasNext()) {
                                                                                                operation = (String) gIterator.next();
                                                                                                for (int i = 0; i < permissonsAllowed.length; i++) {
                                                                                                    if (permissonsAllowed[i].equals(operation)) {
                                                                                                        permissionStatus = true;
                                                                                                        break;
                                                                                                    }
                                                                                                }

                                                                                                count++;
                                                                                                if (count == 4) {
                                                                                %>
                                                                            </tr>
                                                                            <tr>
                                                                                <%
                                                                                                                                                                                    count = 1;
                                                                                                                                                                                }
                                                                                %>
                                                                                <td>
                                                                                    <font face="verdena" size="2">
                                                                                        <%
                                                                                                                                                                                        if (permissionStatus == true) {
                                                                                        %>
                                                                                        <input type="checkbox"  name="permission[]"  class="required"  checked value="<%= operation%>" > <%= operation%>
                                                                                        <%
                                                                                                                                                                                                                                                                                    permissionStatus = false;
                                                                                                                                                                                                                                                                                } else {

                                                                                        %>

                                                                                        <input type="checkbox"  name="permission[]"   value="<%= operation%>" > <%= operation%>
                                                                                        <%

                                                                                                                                                                                        }
                                                                                        %>

                                                                                    </font>
                                                                                </td>
                                                                                <%
                                                                                            }
                                                                                %>



                                                                            </tr>






                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td align="right">

                                                                                    <input type="submit" value="Save"  STYLE="font-family: Verdana;"/>
                                                                                </td>
                                                                            </tr>
                                                                        </table>


                                                                    </form>
























                                                                </div>
                                                                <!-- side bar start here-->
                                                                <div id="accordion">
                                                                    <%@include file="sidebar.jsp" %>
                                                                </div>
                                                                <!-- side bar end here-->

                                                            </div>



                                                            <!-- footer start -->
                                                            <div id="footer">
                                                                <%@ include file="footer.jsp" %>
                                                            </div>


                                                            <!-- footer end -->
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


