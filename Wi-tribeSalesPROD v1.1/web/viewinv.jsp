<%@page contentType="text/html"   import="java.util.*"%>
<%@page pageEncoding="UTF-8"%>
<%
String ob = (String)session.getAttribute("InvoiceHTML");
if(ob == null) {
    
    out.print("test");
} else {
    StringBuffer invoiceBuf = new StringBuffer(ob);
    out.print(invoiceBuf);
}
%>
