<%-- 
    Document   : searchUser
    Created on : Mar 28, 2011, 5:06:07 PM
    Author     : PKMDUsman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="DataBase.DBConnectivity"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ page errorPage="errorPage.jsp" %>
 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>

<%
            String username = (String) session.getAttribute("username");
            boolean access = false;
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            } else {
                List permissions = (List) session.getAttribute("groupPermissions");
                Iterator permissionsIterator = null;
                permissionsIterator = permissions.iterator();
                while (permissionsIterator.hasNext()) {
                    String permission = (String) permissionsIterator.next();
                    if (permission.equals("Search MAC")) {
                        access = true;
                    }
                }
                if (access == false) {
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
                document.getElementById("macSearch").focus();
            }

            $(document).ready(function(){
                $("#serachUser").validate({
                    rules: {
                        macSearch: {
                            required: true,
                            minlength: 12,
                            maxlength: 12
                        }
                    },
                    messages: {
                        macSearch: {
                            required: "MAC should not be empty",
                            minlength: "MAC lenght should be 12",
                            maxlength: "MAC lenght should be 12"
                        }
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
                                                                                String mac = request.getParameter("macSearch");
                                                                                if (mac == null || mac.length() != 12) {
                                                                    %>





                                                                    <form name="serachUser" id="serachUser" action="" method="POST">
                                                                        <table style=" font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                            <tr>
                                                                                <td> MAC Address  : </td>
                                                                                <td>
                                                                                    <input name="macSearch" value="" size=12 type="text"  STYLE="font-family: Verdana;"/>
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td align="right">

                                                                                    <input type="submit" value="Search"  STYLE="font-family: Verdana;"/>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </form>


                                                                    <%                                                                                                                                            } else {
                                                                                                                                                        String userMAC = request.getParameter("macSearch");
                                                                                                                                                        DBConnectivity dbUtil = new DBConnectivity();
                                                                                                                                                        String[] userInfo = dbUtil.searchUser(userMAC);
                                                                                                                                                        if (userInfo != null) {
                                                                                                                                                            String[] mac_group = userInfo[0].split("@");
                                                                                                                                                            System.out.println(username + " searched for the MAC " + mac);
                                                                                                                                                            log.info(username + " searched for the MAC " + mac);
                                                                    %>


                                                                    <table style=" font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                        <tr>
                                                                            <td> MAC Address  : </td>
                                                                            <td>
                                                                                <input name="macSearch" value="<%= mac_group[0]%>"  type="text" disabled STYLE="font-family: Verdana ; width:100%;"/>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Domain  : </td>
                                                                            <td>
                                                                                <input name="macSearch" value="<%= mac_group[1]%>" type="text" disabled STYLE="font-family: Verdana ; width:100%;"/>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td> Profile  : </td>
                                                                            <td>
                                                                                <input name="macSearch" value="<%= userInfo[1]%>" type="text" disabled STYLE="font-family: Verdana; width:100%"/>
                                                                            </td>
                                                                        </tr>

                                                                        <tr>
                                                                            <td>&nbsp;</td>
                                                                            <td align="right">

                                                                                <input type="submit" value="Search"  STYLE="font-family: Verdana;"/>
                                                                            </td>
                                                                        </tr>
                                                                    </table>

                                                                    <%
                                                                                                                                                                                                                            } else {
                                                                                                                                                            System.out.println("Attempt to search MAC " + mac + " by " + username + " failed because MAC doesnot exist");
                                                                                                                                                            log.info("Attempt to search MAC " + mac + " by " + username + " failed because MAC doesnot exist");
                                                                    %>

                                                                    <tr>
                                                                        <td>
                                                                            <%= mac%> not found...Please try again.
                                                                        </td>
                                                                    </tr>


                                                                    <%
                                                                                    }
                                                                                }


                                                                    %>











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


