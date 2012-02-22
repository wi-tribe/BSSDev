<%-- 
    Document   : getMACBasedPlanFilterString
    Created on : Aug 10, 2011, 9:36:29 AM
    Author     : PKAasimN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" import="customfields.*, com.portal.bas.*,com.witribe.util.*, com.portal.bas.comp.*, com.portal.pcm.* , com.portal.app.ccare.comp.*, Wtb.MyAccount.*, com.portal.web.comp.*, com.portal.web.*, java.net.*, java.util.*, java.text.*" %>

<%
 String SearchString = "";
 String model =request.getParameter("model");
 String devicetype =request.getParameter("devicetype");
 System.out.println(model);
 System.out.println(devicetype);

 MACBasedPlanlist PlansObj = new MACBasedPlanlist();
 SearchString = (String)PlansObj.GetFilterString(model, devicetype);

 session.setAttribute("SearchString", SearchString);
%>
<%=SearchString%>