<%-- 
    Document   : changePassword
    Created on : Apr 8, 2011, 2:52:00 PM
    Author     : PKMDUsman
--%>

<%@page import="DataBase.DBConnectivity"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@ page errorPage="errorPage.jsp" %>


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
                    if (permission.equals("Change Password")) {
                        access = true;
                    }
                }
                if (access == false) {
                    response.sendRedirect(response.encodeRedirectURL("accessDenied.jsp"));
                }
             //   DBConnectivity dbUtil = new DBConnectivity();
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

        <script type="text/javascript" >

            function setFocus()
            {
                document.getElementById("currentPwd").focus();
            }

            $(document).ready(function(){
                $("#changePwd").validate({
                    rules: {
                        currentPwd: {
                            required: true,
                            minlength: 5
                        },
                        newPwd: {
                            required: true,
                            minlength: 5
                        },
                        confirmPwd: {
                            required: true,
                            minlength: 5
                        }
                    },
                    messages: {
                        currentPwd: {
                            required: "Current password cannot be empty",
                            minlength: "Current password should be atleast 5 characters"
                        },
                        newPwd: {
                            required: "New password cannot be empty",
                            minlength: "New password should be atleast 5 characters"
                        },
                        confirmPwd: {
                            required: "New password cannot be empty",
                            minlength: "Password should be atleast 5 characters"
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
                                                                                if (request.getParameter("changepassword") != null) {
                                                                                    if (request.getParameter("changepassword").equals("fail")) {
                                                                                        out.println("Problem in updating password...Please try again....");
                                                                                      //  System.out.println("Problem in updating password...Please try again....");
                                                                                    } else if (request.getParameter("changepassword").equals("incorrect")) {
                                                                                        out.println("Current Password doesnot matches...Please try again.");
                                                                                      //  System.out.println("Current Password doesnot matches...Please try again.");
                                                                                    } else if (request.getParameter("changepassword").equals("mismatch")) {
                                                                                        out.println("New passwords doesnot match.Please try again..");
                                                                                       // System.out.println("New passwords doesnot match.Please try again..");
                                                                                    }
                                                                                }
                                                                    %>



                                                                    <form name="changePwd" id="changePwd" action="FormProcessors/changePasswordFP.jsp" method="POST"  >

                                                                        <table style=" font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                            <tr>
                                                                                <td > Current Password  : </td>
                                                                                <td> <input name="currentPwd" value="" size=15 type="password" STYLE="font-family: Verdana;"/> </td>
                                                                            </tr>


                                                                            <tr>
                                                                                <td > New Password  : </td>
                                                                                <td> <input name="newPwd" value="" size=15 type="password" STYLE="font-family: Verdana;"/> </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td > Confirm Password  : </td>
                                                                                <td> <input name="confirmPwd" value="" size=15 type="password" STYLE="font-family: Verdana;"/> </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td>&nbsp;</td>
                                                                                <td align="right">

                                                                                    <input type="submit" value="Change"  STYLE="font-family: Verdana;"/>
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

