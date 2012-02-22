<%@page contentType="text/html" import="java.util.*, java.lang.*, com.portal.pcm.*,  com.portal.pcm.fields.*, com.portal.bas.*"%>
<%@page pageEncoding="UTF-8" import="com.witribe.dao.*"%>

<%

String curValue=request.getParameter("currency");
Hashtable htAccountCurrency = new Hashtable();
htAccountCurrency.put("586","PKR");
Collection c = htAccountCurrency.values();
Iterator itr = c.iterator();
String Currency = "";
Currency=(String)htAccountCurrency.get(curValue);
out.println(Currency);
%>
