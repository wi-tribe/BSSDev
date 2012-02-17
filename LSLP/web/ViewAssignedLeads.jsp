<%@page contentType="text/html" import="com.witribe.vo.LeadVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<logic:notEmpty name="objLeadList" >
     <p>Click on any of the following lead Id to proceed</p>
</logic:notEmpty>
<div id="inner-content">
<form action="./ReAssignJob.do" method="Post">
<input type="hidden" name="page" />
<input type="hidden" name="flag" />
<logic:notEmpty name="objLeadList" >
<div class="scrollable">
<table width="679" border="0" cellspacing="0" cellpadding="0" class="bill-listing">	

<tr>
    <th valign="middle" width="131" align="left">Lead ID</th>
    <th valign="middle" width="125" align="left">Name</th>
    <th valign="middle" width="162" align="left">Contact</th>
    <!--<th valign="middle" width="192" align="left">House/Plot</th>
    <th valign="middle" width="100" align="left">Street</th>-->
    <th valign="middle" width="100" align="left">Sub Zone</th>
    <th valign="middle" width="100" align="left">Zone(S)</th>
    <th valign="middle" width="65" align="left">City(s)</td>
</tr>

<logic:iterate id="objLead" name="objLeadList" type="com.witribe.vo.LeadVO" scope="request">
    <tr><td valign="middle" width="131"><a href="./ReAssignJob.do?leadId=<bean:write name='objLead' property='leadId' />"><u><bean:write name="objLead" property="leadId" /></u></a></td>
        <td valign="middle" width="125"><bean:write name="objLead" property="firstname"/> <bean:write name="objLead" property="lastname"/></td>
        <td valign="middle" width="162"><bean:write name="objLead" property="contactnumber"/></td>
        <%--<td valign="middle" width="192"><bean:write name="objLead" property="plot"/></td>
        <td valign="middle" width="192"><bean:write name="objLead" property="street"/></td>--%>
        <td valign="middle" width="192"><bean:write name="objLead" property="subzone"/></td>
        <td valign="middle" width="192"><bean:write name="objLead" property="zone"/></td>
        <td valign="middle" width="65"><bean:write name="objLead" property="city"/></td>
    </tr>
</logic:iterate> 
</table></div>
<table border="0" align="right" width="150">
    <tr>
        <%String pageCount =(String) request.getAttribute("page");
        String nextEnable = (String) request.getAttribute("next");%>
        <td>
            <%if(!"0".equals(pageCount)) {%>
            <a href="javascript:prevPageClick(${page})">Previous</a>&nbsp;
            <%} 
            int pageNo = Integer.parseInt(pageCount)+1;%>
            <%=pageNo%>
            <% if(("true".equalsIgnoreCase(nextEnable))) {%>
            <a href="javascript:nextPageClick(${page})">Next</a>
            <%}%>
        </td>
    </tr>
</table>
</logic:notEmpty >
<logic:empty name="objLeadList">
    <tr>
        <td colspan="4"><b>There are no Jobs/Leads assigned to You !</b></td>
    </tr>
</logic:empty>
</tbody>
</table></div></form>




