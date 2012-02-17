<%-- 
    Document   : changePortalUserGroup
    Created on : Apr 19, 2011, 10:28:08 AM
    Author     : PKMDUsman
--%>


<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="DataBase.DBConnectivity"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page errorPage="errorPage.jsp" %>

<%
            Iterator gIterator = null;
            boolean access = false;
            String username = (String) session.getAttribute("username");
            String portalUserGroup = null;
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            } else {
                List permissions = (List) session.getAttribute("groupPermissions");
                Iterator permissionsIterator = null;
                permissionsIterator = permissions.iterator();
                while (permissionsIterator.hasNext()) {
                    String permission = (String) permissionsIterator.next();
                    if (permission.equals("Change Portal User Group")) {
                        access = true;
                    }
                }

                if (access == true) {
                    DBConnectivity dbUtil = new DBConnectivity();
                    List<String> gList = new ArrayList<String>();
                    gList = dbUtil.getAvailableGroups();
                    gIterator = gList.iterator();

                    
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
            $(function() {
                $( "#accordion" ).accordion();
            });
        </script>

        <script type="text/javascript">

            function setFocus()
            {
                document.getElementById("username").focus();
            }

            $(document).ready(function(){
                $("#UpdatePortalUser").validate({
                    rules: {
                        username: "required"

                    },
                    messages: {
                        username: "Please specify username"

                    }
                })

            });





        </script>

    </head>

    <body id="page1" onload="setFocus()">
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

                                                                    <!--   <img alt="" src="images/homepageTitle.gif" width="327" height="41" /> -->



                                                                    <!-- code start here -->


                                                                    <%



                                                                                if (request.getParameter("updateUser") != null) {
                                                                                    if (request.getParameter("updateUser").equals("unempty")) {
                                                                                        out.println("<font size=2 color=red><b>User Name should not be empty.</b></font>");
                                                                                    } else if (request.getParameter("updateUser").equals("grempty")) {
                                                                                        out.println("<font size=2 color=red><b>Group should not be empty.</b></font>");
                                                                                    } else if (request.getParameter("updateUser").equals("doesnotexist")) {
                                                                                        out.println("<font size=2 color=red><b>User doesnot exist,try different username.</b></font>");
                                                                                    } else if (request.getParameter("updateUser").equals("problem")) {
                                                                                        out.println("<font size=2 color=red><b>Problem updating user,please try again.</b></font>");
                                                                                    }
                                                                                }


                                                                    %>






                                                                    <form name="UpdatePortalUser" id="UpdatePortalUser" action="FormProcessors/changePortalUserGroupFP.jsp" method="POST"  >

                                                                        <table style=" font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                            <tr>
                                                                                <td> Username : </td>
                                                                                <td>
                                                                                    <input name="username" value="" size=18 type="text" STYLE="font-family: Verdana;"/>
                                                                                </td>
                                                                            </tr>



                                                                            <tr>
                                                                                <td> Group : </td>
                                                                                <td> <select name="group" STYLE="width:100%; font-family: Verdana;">
                                                                                        <%
                                                                                                    while (gIterator.hasNext()) {%>

                                                                                        <option><%= gIterator.next()%></option>
                                                                                        <%
                                                                                                    }
                                                                                        %>                                                                                               </select>  </td>
                                                                            </tr>



                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td align="right">

                                                                                    <input type="submit" value="Update Portal User"  STYLE="font-family: Verdana;"/>
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

