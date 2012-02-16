<%-- 
    Document   : addGroup
    Created on : Apr 14, 2011, 3:45:52 PM
    Author     : PKMDUsman
--%>

 <%
   org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
 %>


<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="DataBase.DBConnectivity"%>
<%@ page errorPage="errorPage.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<%
            String username = (String) session.getAttribute("username");
            Iterator oIterator = null;
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
                    if (permission.equals("Add Group")) {
                        access = true;
                    }
                }
                if (access == false) {
                    response.sendRedirect(response.encodeRedirectURL("accessDenied.jsp"));
                }
                DBConnectivity dbUtil = new DBConnectivity();
                List<String> oList = new ArrayList<String>();
                oList = dbUtil.getAvailableOperations();
                oIterator = oList.iterator();
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
                $("#addUserGroup").validate({
                    rules: {
                        groupName: {
                            required: true
                        }
                    },
                    messages: {
                        groupName: {
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



                                                                                if (request.getParameter("result") != null) {
                                                                                    if (request.getParameter("result").equals("grpempty")) {
                                                                                        out.println("<font size=2 color=red><b>Group Name can not be empty.</b></font>");
                                                                                    }else if(request.getParameter("result").equals("prempty")){
                                                                                        out.println("<font size=2 color=red><b>Atleast one permission should be selected.</b></font>");
                                                                                    }else if(request.getParameter("result").equals("grpexist")){
                                                                                        out.println("<font size=2 color=red><b>Group already exist,try different name.</b></font>");
                                                                                    }else if(request.getParameter("result").equals("problem")){
                                                                                        out.println("<font size=2 color=red><b>Problem adding group,please try again.</b></font>");
                                                                                    }
                                                                                }


                                                                    %>






                                                                    <form name="addUserGroup" id="addUserGroup" action="FormProcessors/addUserGroup.jsp" method="POST"  >

                                                                        <table style="font-family: Verdana; font-weight: bold; font-size: 15px; background-color: #DCDCDC;">
                                                                            <tr>
                                                                                <td > Group Name : </td>
                                                                                <td colspan="2"> <input name="groupName" value="" size=15 type="text" STYLE=" font-family: Verdana;"/> </td>
                                                                            </tr>


                                                                            <tr>
                                                                                <td > Select permissions  : </td>

                                                                            </tr>



                                                                            <tr>

                                                                                <%
                                                                                            int count = 0;
                                                                                            String operation = null;
                                                                                            while (oIterator.hasNext()) {
                                                                                                operation = (String) oIterator.next();
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
                                                                                        <input type="checkbox"  name="permission[]"  class="required" value="<%= operation%>" > <%= operation%>
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

