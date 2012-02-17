<%-- 
    Document   : home
    Created on : Mar 28, 2011, 11:00:01 AM
    Author     : PKMDUsman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page errorPage="errorPage.jsp" %>


<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
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
        <script type="text/javascript" src="js/jquery-ui-1.8.11.custom.min.js"></script>

        <script type="text/javascript">
            $(function() {
                $( "#accordion" ).accordion();
            });
        </script>
    </head>

    <body id="page1">
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
                                                                                out.println("<font size=3 face=arial ><b>Welcome " + username + "!</b></font>");
                                                                                out.println("<br />");
                                                                    %>

                                                                    <%



                                                                                if (request.getParameter("attempt") != null) {
                                                                                    if (request.getParameter("attempt").equals("fail")) {
                                                                                        out.println("Please try again...");
                                                                                        System.out.println("Authentication Failed.");
                                                                                    }
                                                                                }


                                                                    %>
                                                                    <%
                                                                                if (request.getParameter("insert") != null) {
                                                                                    if (request.getParameter("insert").equals("alreadyExist")) {
                                                                                        out.println("MAC already exists...");
                                                                                        System.out.println("MAC already exists...");
                                                                                    } else if (request.getParameter("insert").equals("success")) {
                                                                                        out.println("MAC is added Successfully...");
                                                                                        System.out.println("MAC is added Successfully...");
                                                                                    } else if (request.getParameter("insert").equals("fail")) {
                                                                                        out.println("Problem adding MAC.Please try again...");
                                                                                        System.out.println("Problem adding MAC.Please try again...");
                                                                                    } else if (request.getParameter("insert").equals("macLenght")) {
                                                                                        out.println("MAC lenght should be 12");
                                                                                        System.out.println("MAC lenght should be 12");
                                                                                    }
                                                                                }
                                                                    %>

                                                                    <%
                                                                                if (request.getParameter("changepassword") != null) {
                                                                                    if (request.getParameter("changepassword").equals("success")) {
                                                                                        out.println("Password is changed Successfully...");
                                                                                        System.out.println("Password is changed Successfully...");
                                                                                    }
                                                                                }
                                                                    %>
                                                                    <%
                                                                                if (request.getParameter("update") != null) {
                                                                                    if (request.getParameter("update").equals("doesnotExist")) {
                                                                                        out.println("MAC doesnot exists...");
                                                                                        System.out.println("MAC doesnot exists...");
                                                                                    } else if (request.getParameter("update").equals("success")) {
                                                                                        out.println("MAC is updated Successfully...");
                                                                                        System.out.println("MAC is updated Successfully...");
                                                                                    } else if (request.getParameter("update").equals("fail")) {
                                                                                        out.println("Problem updating MAC.Please try again...");
                                                                                        System.out.println("Problem updating MAC.Please try again...");
                                                                                    } else if (request.getParameter("update").equals("macLenght")) {
                                                                                        out.println("MAC lenght should be 12");
                                                                                        System.out.println("MAC lenght should be 12");
                                                                                    }
                                                                                }
                                                                    %>
                                                                    <%
                                                                                if (request.getParameter("delete") != null) {
                                                                                    if (request.getParameter("delete").equals("doesnotExist")) {
                                                                                        out.println("MAC doesnot exists...");
                                                                                        System.out.println("MAC doesnot exists...");
                                                                                    } else if (request.getParameter("delete").equals("success")) {
                                                                                        out.println("MAC is deleted Successfully...");
                                                                                        System.out.println("MAC is deleted Successfully...");
                                                                                    } else if (request.getParameter("delete").equals("fail")) {
                                                                                        out.println("Problem deleting MAC.Please try again...");
                                                                                        System.out.println("Problem deleting MAC.Please try again...");
                                                                                    } else if (request.getParameter("delete").equals("macLenght")) {
                                                                                        out.println("MAC lenght should be 12");
                                                                                        System.out.println("MAC lenght should be 12");
                                                                                    }
                                                                                }
                                                                    %>

                                                                    <%

                                                                                if (request.getParameter("result") != null) {
                                                                                    if (request.getParameter("result").equals("success")) {
                                                                                        out.println("Group added successfully...");
                                                                                    }
                                                                                }

                                                                    %>
                                                                    <%

                                                                                if (request.getParameter("adduser") != null) {
                                                                                    if (request.getParameter("adduser").equals("success")) {
                                                                                        out.println("Portal User added successfully...");
                                                                                    }
                                                                                }

                                                                    %>

                                                                     <%

                                                                                if (request.getParameter("changeStatus") != null) {
                                                                                    if (request.getParameter("changeStatus").equals("success")) {
                                                                                        out.println("Portal Group updated successfully...");
                                                                                    }
                                                                                }

                                                                    %>
                                                                    <%

                                                                                if (request.getParameter("deleteuser") != null) {
                                                                                    if (request.getParameter("deleteuser").equals("success")) {
                                                                                        out.println("Portal user deleted successfully...");
                                                                                    }
                                                                                }

                                                                    %>
                                                                    <%

                                                                                if (request.getParameter("updateUser") != null) {
                                                                                    if (request.getParameter("updateUser").equals("success")) {
                                                                                        out.println("Portal user updated successfully...");
                                                                                    }
                                                                                }

                                                                    %>


                                                                    <br/>
                                                                    <br/>
                                                                    please select an option..





















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




