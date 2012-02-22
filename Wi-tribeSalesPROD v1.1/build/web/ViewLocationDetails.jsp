<%@page contentType="text/html" import="com.witribe.vo.LeadVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<form action="./ViewLocation.do" method="Post">
    <input type="hidden" name="page" />
     <input type="hidden" name="flag" />
     <logic:notEmpty name="objSalesList" >
        <p>Choose any of the following City/Zone assigned to the salesperson to delete</p>
     </logic:notEmpty>
                     <div id="inner-content">
                         <logic:notEmpty name="objSalesList" >
                         
                             <table width="600" border="0" cellspacing="0" cellpadding="0" class="bill-listing">
                                 
                                 <tr>
                                     <th valign="middle" width="25" align="left">&nbsp;</th>
                                     <th valign="middle" width="100" align="left">City/Zone/Subzone</th>
                                     <th valign="middle" width="100" align="left">Sales Person Id</th>
                                     <th valign="middle" width="100" align="left">Type</th>
                                     <th valign="middle" width="100" align="left">Full Name</th>
                                     
                                     
                                 </tr>
                                 
                                 <logic:iterate id="objSale" name="objSalesList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                     <tr><td><input id="check" type=checkbox name="check" value="${objSale.salesId}#${objSale.location}"/></td>
                                         <td valign="middle" width="100">${objSale.location}</td>
                                         <td valign="middle" width="50">${objSale.salesId}</td>
                                         <c:set var="salesType" value="${objSale.salestype}"/>
                                         <c:if test="${salesType == WitribeConstants.TYPE_NSM}">
                                             <td valign="middle" width="50">Sales Director</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_RSM}">
                                             <td valign="middle" width="50">RSM</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_TL}">
                                             <td valign="middle" width="50">TL</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_CSE}">
                                             <td valign="middle" width="50">CSE</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_BO}">
                                             <td valign="middle" width="50">BO-Dealer</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_NBO}">
                                             <td valign="middle" width="50">NBO-Dealer</td> 
                                         </c:if>
                                         <td valign="middle" width="100">${objSale.fullname}</td>
                                         
                                    </tr>
                                 </logic:iterate> 
                                 
                         </table>
                         <table border="0" align="right" width="200">
                             <tr>
                                 <%String pageCount =(String) request.getAttribute("page");
                                 String nextEnable = (String) request.getAttribute("next");%>
                                 <td>
                                 <%if(!"0".equals(pageCount)) {%>
                                 <a href="javascript:prevPageClick(${page})">Previous</a>&nbsp;
                                 <%} 
                                 int pageNo = Integer.parseInt(pageCount)+1;%>
                                 <%=pageNo%>
                                  <%if(("true".equalsIgnoreCase(nextEnable))) {%>
                                 <a href="javascript:nextPageClick(${page})">Next</a>
                                 <%}%>
                                 </td>
                             </tr>
                         </table>
                         <table>
                         <tr>
                         <td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('./ViewLocation.do');"/></td></tr>
                         </logic:notEmpty >
                                  <logic:empty name="objSalesList">
                                 <tr>
                                     <td colspan="4"><b>There are no locations to delete !</b></td>
                                 </tr>
                                 </logic:empty>
                                                 
             </table></div></form>
             
    

              
