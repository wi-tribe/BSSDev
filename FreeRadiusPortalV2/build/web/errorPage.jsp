<%-- 
    Document   : errorPage
    Created on : Mar 28, 2011, 11:41:13 AM
    Author     : PKMDUsman
--%>
<%@ page isErrorPage = "false" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">





<%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect(response.encodeRedirectURL("index.jsp"));
                return;
            }
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
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

    <body id="page1" >
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

                                                                    <!-- code start here -->





                                                                    <br/>
                                                                    <br/>
                                                                    We are unable to process your request, Please contact Administrator.

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




