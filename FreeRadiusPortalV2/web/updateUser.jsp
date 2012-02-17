<%--
    Document   : updateUser
    Created on : Mar 28, 2011, 5:06:07 PM
    Author     : PKMDUsman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="DataBase.DBConnectivity"%>
<%@ page errorPage="errorPage.jsp" %>

 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>
 
<%
            Iterator dIterator = null;
            Iterator pIterator = null;
            boolean access = false;
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
                    if (permission.equals("Update MAC")) {
                        access = true;
                    }
                }
                if (access == true) {
                    DBConnectivity dbUtil = new DBConnectivity();
                    List<String> pList = new ArrayList<String>();
                    pList = dbUtil.getAvailableProfiles();
                    pIterator = pList.iterator();
                    List<String> dList = new ArrayList<String>();
                    dList = dbUtil.getAvailableDomains();
                    dIterator = dList.iterator();
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
                document.getElementById("macSearch").focus();
            }

            $(document).ready(function(){
                $("#searchUser").validate({
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


                                                                    <!-- code start here -->
                                                                    <%
                                                                                String mac = request.getParameter("macSearch");
                                                                                if (mac == null || mac.length() != 12) {
                                                                    %>





                                                                    <form name="searchUser" id="searchUser" action="" method="POST">
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


                                                                    <%                                                                                                                                                    } else {
                                                                                                                                                        String userMAC = request.getParameter("macSearch");
                                                                                                                                                        DBConnectivity dbUtil = new DBConnectivity();
                                                                                                                                                        String[] userInfo = dbUtil.searchUser(userMAC);
                                                                                                                                                        if (userInfo != null) {
                                                                                                                                                            System.out.println(userInfo[0] + "---" + userInfo[1]);
                                                                                                                                                            String[] mac_group = userInfo[0].split("@");
                                                                    %>

                                                                    <form name="updateUser" id="updateUser" action="FormProcessors/updateUserFP.jsp" method="POST">
                                                                        <table style=" font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                            <tr>
                                                                                <td> MAC Address  : </td>
                                                                                <td>
                                                                                    <input name="macSearch" value="<%= mac_group[0]%>"  type="text" readonly STYLE="font-family: Verdana ; width:100%;"/>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    Domain  :
                                                                                </td>
                                                                                <td>
                                                                                    <select name="domain" STYLE="font-family: Verdana; width: 100%" disabled >
                                                                                        <%
                                                                                                                                                                                                                                                    while (dIterator.hasNext()) {
                                                                                                                                                                                                                                                        String domain_options = dIterator.next().toString();
                                                                                                                                                                                                                                                        if (domain_options.equals(mac_group[1])) {
                                                                                        %>
                                                                                        <OPTION SELECTED VALUE="<%= domain_options%>"><%=domain_options%></OPTION>
                                                                                        <% } else {

                                                                                        %>

                                                                                        <option><%= domain_options%></option>

                                                                                        <%
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                    }
                                                                                        %>


                                                                                    </select>
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td>
                                                                                    Profile  :
                                                                                </td>
                                                                                <td>
                                                                                    <input type="hidden" name="domain" id="domain" value="<%= mac_group[1]%>" />
                                                                                    <input type="hidden" name="currentProfile" id="currentProfile" value="<%= userInfo[1]%>" />

                                                                                    <select name="profile" STYLE="font-family: Verdana;width: 100%">
                                                                                        <%
                                                                                                                                                                                                                                                    while (pIterator.hasNext()) {
                                                                                                                                                                                                                                                        String profile_options = pIterator.next().toString();
                                                                                                                                                                                                                                                        if (profile_options.equals(userInfo[1])) {
                                                                                        %>
                                                                                        <OPTION SELECTED VALUE="<%= profile_options%>"><%=profile_options%></OPTION>
                                                                                        <%
                                                                                                                                                                                                                                                                                                                                                } else {

                                                                                        %>
                                                                                        <option><%= profile_options%></option>
                                                                                        <%
                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                    }
                                                                                        %>


                                                                                    </select> </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td align="right">

                                                                                    <input type="submit" value="Update"  STYLE="font-family: Verdana;"/>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </form>

                                                                    <%
                                                                                    } else {
                                                                                        System.out.println("Attempt to update " + mac + " by " + username + " failed beacuse MAC doesnot exist");
                                                                                        log.info("Attempt to update " + mac + " by " + username + " failed beacuse MAC doesnot exist");
                                                                                        response.sendRedirect(response.encodeRedirectURL("home.jsp?update=doesnotExist"));

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


