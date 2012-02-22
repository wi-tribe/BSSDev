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
<%System.out.println("into jsp");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>

<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            
            
            <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing" >
                <form method="post" name="NewAccountDealerFormBean" onsubmit="return frmValidate(this)"> 
                <logic:present name="accountDetails">
                <table class="info-listing">
                    <tr>
                        <td colspan="2">
                            <table class="info-listing">
                                <tr>
                                    <td width="15%" nowrap><label>Account No:</label></td>
                                    <td width="35%"><bean:write name="accountDetails" property="accountNo"/></td>       
                                    <td width="15%"><label>Service:</label></td>
                                    
                                </tr>
                                <tr>
                                    <td width="15%"><label>Name :</label></td>
                                    <td width="15%"><bean:write name="accountDetails" property="userName"/></td>          
                                    <td width="15%"><label>E mail :</label></td>
                                    <td width="35%"><bean:write name="accountDetails" property="emailAddress"/></td>
                                </tr>
                            </table>
                        </td>    
                    </tr>
                    <tr>
                        <td align="left" width="50%">
                            <label> <b>Billing Address :</b>&nbsp;&nbsp&nbsp;</label> 
                        </td>
                        <td align="right" width="50%">
                            <label ><b>service Address :</b></label>
                        </td>
                    </tr>
                    <tr>
                        <td><bean:write name="accountDetails" property="billingAddress"/></td>
                        <td><bean:write name="accountDetails" property="serviceAddress"/></td>
                    </tr>
                    <tr>
                        <td align=left><label><b>Phone Numbers :</b></label></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table class="info-listing">
                                <tr>
                                    <td><label>Home :</label></td>
                                    <td><bean:write name="accountDetails" property="homeNo"/></td>
                                    <td><label>Office :</label></td>
                                    <td><bean:write name="accountDetails" property="officeNo"/></td>
                                </tr>
                                <tr>
                                    <td><label>Fax :</label></td>
                                    <td><bean:write name="accountDetails" property="faxNo"/></td>
                                    <td><label>Mobile :</label></td>
                                    <td><bean:write name="accountDetails" property="mobileNo"/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    
                </table>
                </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table class="info-listing">
                            <tr>
                                <td><label><b>Account Status :</b></label></td>
                            </tr>
                            <tr>  
                                <td><bean:write name="accountDetails" property="status"/></td>
                            </tr> 
                             <tr>
                                <td align=middle width="33%"><label><b>Service Type</b></label></td>
                                <td align=middle width="34%"><label><b>&nbsp;&nbsp;&nbsp;&nbsp;Login</b></label></td>
                                <td align=middle width="33%"><label><b>Status</b></label></td>
                            </tr>
                            <logic:notEmpty name="accountDetails" property="serviceDetails">
                                <logic:iterate name="accountDetails" id="regService" property="serviceDetails" type="com.witribe.vo.RegService">
                                    <tr>
                                        <td width="25%"><bean:write name="regService" property="svsType"/></td>
                                        <td width="50%" ><bean:write name="regService" property="svsLogin"/></td>
                                        <td width="25%"><bean:write name="regService" property="svsStatus"/></td> 
                                    </tr>
                                </logic:iterate>
                            </logic:notEmpty>    
                            <logic:empty name="accountDetails" property="serviceDetails">
                                <tr><td colspan="3" align="center"> No Data available</td></tr>
                            </logic:empty>
                        </table>
                    </td>
                </tr>
               
               
<tr>
    <td colspan="2">
        <table class="info-listing">
             <tr>
                    <td colspan="2"><label><b>Payment History :</b></label></td>
              </tr>
                <tr>
                   
                </tr>
                <tr>
                <th align=middle>Payment Id &nbsp;</th>
                <th align=middle>Paid Amount &nbsp;</th>
                <th align=middle>Payment Type&nbsp;</th>
                <th align=middle>Payment Date </th>
            </tr>
           
            <logic:notEmpty name="accountDetails" property="payment">
                <logic:iterate name="accountDetails" id="paymentBean" property="payment" type="com.witribe.vo.PaymentBean">
                    <tr>
                        <td>
                        <a href="viewPayReceipt.do?parameter=getPayReceipt&paymentId=<bean:write name="paymentBean" property="paymentId"/>" target="_new">
                        <bean:write name="paymentBean" property="paymentId"/>
                        </a>
                       </td>
                        
                        <td><jsp:include flush="true" page="currency.jsp">
                                <jsp:param name="currency" value='<%= request.getAttribute("currency")%>'/>
                            </jsp:include> 
                            <bean:write name="paymentBean" property="paidAmount"/>
                        </td>
                        <td><bean:write name="paymentBean" property="paymentType"/></td>
                        <td><bean:write name="paymentBean" property="paymentDate"/></td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="accountDetails" property="payment">
                <tr><td colspan="3" align="center"> No Data available</td></tr>
            </logic:empty>
        </table>
    </td>
</tr>
            </table>
            </td>
            </tr>
            
            </table>
            </logic:present>
            </form>
            </table>
        </div>
    </div>
</div>

