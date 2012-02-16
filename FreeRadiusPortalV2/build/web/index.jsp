<%--
    Document   : Home
    Author     : PKMDUSMAN
--%>

<%@ page errorPage="errorPage.jsp" %>

<%
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
%>



<%@page import="com.sun.xml.internal.messaging.saaj.soap.ver1_1.Header1_1Impl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <%@ include file="favicon.jsp" %>
        <title>
            <%@ include file="title.jsp" %>
        </title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Content-Style-Type" content="text/css" />
        <link href="style.css" rel="stylesheet" type="text/css" />


        <script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.validate.min.js"></script>
        <script type="text/javascript" >

            function setFocus()
            {
                document.getElementById("username").focus();
            }

            $(document).ready(function(){
                $("#loginform").validate({
                    rules: {
                        username: "required",
                        password: {
                            required: true,
                            minlength: 5
                        }
                    },
                    messages: {
                        username: "Please specify your username",
                        password: {
                            required: "Password cannot be empty",
                            minlength: "Password should be atleast 5 characters"
                        }
                    }
                })

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

                                                                    <%
                                                                                out.println("<font size=2 face=arial ><b>Faisalabad Customers provisioning portal </b></font>");
                                                                                out.println("<br />");
                                                                    %>
                                                                    <!-- menu start -->
                                                                    <div id="menu">



                                                                    </div>
                                                                    <!-- menu end -->

                                                                    <!--   <img alt="" src="images/homepageTitle.gif" width="327" height="41" /> -->



                                                                    <!-- code start here -->

                                                                    <%



                                                                                if (request.getParameter("attempt") != null) {
                                                                                    if (request.getParameter("attempt").equals("fail")) {
                                                                                        //   out.println("Please try again with valid username and password...</b>");
                                                                                        out.println("<font size=2 color=red><b>Please try again with valid username and password...</b></font>");
                                                                                        System.out.println("Authentication Failed.");
                                                                                    }
                                                                                }


                                                                    %>


                                                                    <form name="loginform" id ="loginform" action="FormProcessors/loginVerifier.jsp" method="POST" >
                                                                        <table  style="  font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;" >
                                                                            <tr>
                                                                                <td> Username  : </td><td> <input name="username" id="username" size=15 tabindex="0" type="text"  STYLE="  font-family: Verdana;" /> </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td> Password  : </td><td> <input name="password" type="password" size=15 type="text" class="required" STYLE="  font-family: Verdana;" /> </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td>
                                                                                    &nbsp;
                                                                                </td>
                                                                                <td align="right">
                                                                                    <input type="submit" name="signin" value="Sign In"  STYLE="font-family: Verdana;"/>
                                                                                </td>
                                                                            </tr>
                                                                        </table>

                                                                    </form>
                                                                </div>
                                                                <!-- side bar start-->
                                                                <div id="sidebar">

                                                                </div>

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



