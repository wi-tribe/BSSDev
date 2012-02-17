<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>


<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript">
 function goPayment(){
        var accountNo=document.searchAccountDet.accountNo.value;
        var paymentType=document.searchAccountDet.paymentType.value;
        if(paymentType=='Particular invoice'){
        document.searchAccountDet.flag.value="0";
        document.searchAccountDet.action="AccountSearch.do?parameter=getInvoiceDetails&flag=1&accountNumber="+accountNo;
        }
        else if(paymentType=='Full payment'){
         document.searchAccountDet.flag.value="1";
         document.searchAccountDet.action="AccountSearch.do?parameter=getTotalInvoiceDetails&flag=1&accountNumber="+accountNo;
       
        }
        //alert("flag"+document.getElementById('flag').value);
        document.searchAccountDet.submit();
    }
    </script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
    <div id="inner-content">
       <div class="shadow-box">
            <div class="shadow-box-inner">

   <form  name="searchAccountDet" method="POST" action="AccountSearch.do?parameter=searchInvoiceAccountDetails">
     <table align="center" width="600" height="100" border="0" cellspacing="0" cellpadding="0" class="info-listing" >
            <tr>

                <th valign="middle" width="20%" nowrap>Account No</th>
                <th valign="middle" width="20%" nowrap>CNIC Id</th>
                <th valign="middle" width="20%" nowrap>Name</th>
                <th valign="middle" width="20%" nowrap>Email Id</th>
                <th valign="middle" width="20%" nowrap>Payment</th>
            <td>&nbsp;</td>
        </tr>
            
            <logic:empty name="accountList">
                <tr><td colspan="5" align="center">No Data available</td></tr>
            </logic:empty>
            <logic:notEmpty name="accountList">
                <logic:iterate id="accountSearchJavaBean" name="accountList">
                <tr>
                        <td align="middle"nowrap><a href="AccountSearch.do?parameter=getParticularAccountDetails&accountNumber=<bean:write name="accountSearchJavaBean" property="poid"/>"><bean:write name="accountSearchJavaBean" property="accountNumber"/></a></td>
                        <td align="middle"><bean:write name="accountSearchJavaBean" property="nationalId"/></td>
                        <td align="middle"><bean:write name="accountSearchJavaBean" property="name"/></td>
                        <td align="middle"><bean:write name="accountSearchJavaBean" property="emailId"/></td>        
                        <td align="middle"nowrap>
                            <select name="paymentType" id="paymentType"> 
                            <option value="Particular invoice">Particular invoice</option> 
                            <option value="Full payment">Full payment</option>
                            </select>
                       </td>   
                       <td><input class=submit-btn type="submit" name="Go" value="Go" onclick="goPayment()"/></td>
                       
                </tr>
                <tr>
                <td><input type="hidden" name="accountNo" id="accountNo" width="120" value="<bean:write name="accountSearchJavaBean" property="accountNumber"/>"/></td>
                <td><input type="hidden" name="flag" id="flag" width="120" value=""/></td>
                </tr>
            </logic:iterate>
            </logic:notEmpty>
        </table>
        
   </div>
        </div>
        </div>
    

