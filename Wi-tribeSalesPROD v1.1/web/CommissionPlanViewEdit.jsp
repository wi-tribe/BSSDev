<%@ page contentType="text/html" import="com.witribe.dao.ChannelList,com.witribe.vo.planList,java.util.*,java.sql.SQLException"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plan List</title>
    </head>
    <body>
   <!--     <div class="scrollable">-->
            <table border=1 cellspacing="0" cellpadding="0" class="bill-listing"><tr><B><td>Plan Id</td><td>Description</td><td>Plan Type</td><td>Applies To(CSE/TL/RSM/SD)</td><td>Plan Details</td><td>Status</td></B></tr>    
                <%
                String exception = "";
                String apply_to="";
                try {
                ChannelList cho = new ChannelList();
                List planlistArray = cho.getPlans(); 
                if(planlistArray.size() > 0) {
                for(int i=0; i< planlistArray.size();i++) {
                planList plist = (planList)planlistArray.get(i);
                    out.print("<tr><td>"+plist.getPlanId()+"</td><td>"+plist.getCityZone()+"</td><td>"+plist.getPlanType()+"</td><td>"+cho.Applies_To(plist.getAppliesTo())+"</td><td><a href=./CommissionPlanDetails.do?PlanId="+plist.getPlanId()+">Details</a></td><td>"+plist.getStatus()+"</td>"+"</tr>");   
                }
                }     
                } catch(SQLException ex) {
                exception = "Exception while loading the data";
                out.print(exception+":"+ex);
                } catch(Exception ex) {
                exception = "Exception while loading the data";
                out.print(exception);
                out.print(exception+":"+ex);
                }
                %>
            </table>
            <h1></h1>
            
            
            <%--
    <c:if test="${param.sayHello}">
        <!-- Let's welcome the user ${param.name} -->
        Hello ${param.name}!
    </c:if>
    --%>
      <!--  </div>-->
    </body>
    
</html>
