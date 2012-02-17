<%@page import="com.witribe.vo.*" contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<%System.out.println("into getParticularInvoiceDetails jsp");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>

 
       <div class="shadow-box">
            <div class="shadow-box-inner">

   
     <table width="679" border="0" cellspacing="0" cellpadding="0" class="info-listing">
      
<form method="post" name="ParticularInvoiceDetails" > 
<logic:present name="accountDetails">
<table  border="0" cellspacing="0" cellpadding="0" class="info-listing" >

 <tr>
     <th colspan="2" align=left><b><label>Invoice History :</label></b></th>
</tr>

<tr>
   
            <td >
                <table  border="0" cellspacing="0" cellpadding="0" class="info-listing">
                <tr>
                    <th align ="middle">Bill No &nbsp;</th>
                    <th align ="middle">Invoice Amount&nbsp;&nbsp;</th>
                    <th align ="middle">Due Amount&nbsp;</th>
                    <th align ="middle">Bill Date&nbsp;</th>
                    <th align ="middle"></th>
                    <th align ="middle"></th>
                </tr>
               
            <logic:notEmpty name="accountDetails" property="invoiceDetails">
                <logic:iterate id="invoiceRecord" name="accountDetails" property="invoiceDetails" type="com.witribe.vo.InvoiceRecord">
                    <tr>
                        
                        <td><bean:write name="invoiceRecord" property="billNo"/>&nbsp;&nbsp;</td>
                         
                        <td><jsp:include flush="true" page="currency.jsp">
                            <jsp:param name="currency" value='<%= request.getAttribute("currency")%>'/>
                        </jsp:include><bean:write name="invoiceRecord" property="invoiceAmount"/>&nbsp;</td>
                      
                        <td><jsp:include flush="true" page="currency.jsp">
                                <jsp:param name="currency" value='<%= request.getAttribute("currency")%>'/>
                         </jsp:include><bean:write name="invoiceRecord" property="dueAmount"/>&nbsp;</td>
                        <td><bean:write name="invoiceRecord" property="billDate"/>&nbsp;&nbsp;</td>
                        
                        <td>
                            <a href="AccountSearch.do?parameter=paymentPage&flag=0&billNo=<bean:write name="invoiceRecord" property="billNo"/>&accountNumber=<bean:write name="invoiceRecord" property="accountNumber"/>&due=<bean:write name="invoiceRecord" property="dueAmount"/>">
                            Pay&nbsp;&nbsp;</a></td>
                          <td><a href="viewInvoice.do?parameter=getInvoice&accountp=<bean:write name="invoiceRecord" property="accountNo"/>&billp=<bean:write name="invoiceRecord" property="intBillNo"/>" target="_new">View details</a></td>                                
                        <input type="hidden" name="accountNumber" value="<bean:write name="invoiceRecord" property="accountNumber"/>"/>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="accountDetails" property="invoiceDetails">
                <tr><td colspan="4" align="center"> No Data available</td></tr>
            </logic:empty>
            
        </table>
    </td>
</tr>

</table>
</logic:present>
</form>
</table>
 </div>
        </div>
        
