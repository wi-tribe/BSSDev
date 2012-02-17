<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<div id="inner-content">
       <form action="./ViewRequestStatus.do" method="post">
              <input type="hidden" name="page" />
              <input type="hidden" name="flag" />
              <logic:notEmpty name="objRequestList" >   
                             <div class="scrollable">
                             <table width="679" border="0" cellspacing="0" cellpadding="0"  class="bill-listing">
                             
                             <tr>
                                 <th valign="middle" width="131" align="left">Request ID</th>
                                 <th valign="middle" width="125" align="left">Inventory Type</th>
                                 <th valign="middle" width="162" align="left">Status</th>
                                 <th valign="middle" width="192" align="left">Quantity Ordered</th>
                                 <th valign="middle" width="192" align="left">Request Date</th>
                                 <th valign="middle" width="192" align="left">Required Date</th>
                                 <th valign="middle" width="192" align="left">Quantity Processed</th>
                             </tr>
                             
                             <logic:iterate id="requestobj" name="objRequestList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                             <tr><td valign="middle" width="131">${requestobj.requestId}</a></td>
                             <c:set var="inventoryType" value="${requestobj.inventorytype}"/>
                             <c:if test="${inventoryType == 'CPE'}">
                                 <td valign="middle" width="125">CPE</td> 
                             </c:if>
                             <c:if test="${inventoryType == 'ROUTER'}">
                                 <td valign="middle" width="125">ROUTER</td> 
                             </c:if>
                             <c:if test="${inventoryType == 'OTHER'}">
                                 <td valign="middle" width="125">OTHER</td> 
                             </c:if>
                             
                             <c:set var="processedstatus" value="${requestobj.processedstatus}"/>
                             
                             <c:if test="${processedstatus == '1'}">
                                 <td valign="middle" width="125">Open</td> 
                             </c:if>
                             <c:if test="${processedstatus == '2'}">
                                 <td valign="middle" width="125">In Process</td> 
                             </c:if>
                             <c:if test="${processedstatus == '3'}">
                                 <td valign="middle" width="125">Processed</td> 
                             </c:if>
                             <c:if test="${processedstatus == '4'}">
                                 <td valign="middle" width="125">ERP Processed</td> 
                             </c:if>
                             <c:if test="${processedstatus == '7'}">
                                 <td valign="middle" width="125">BRM Ready</td> 
                             </c:if>
                             <c:if test="null">
                                 <td valign="middle" width="125">--</td> 
                             </c:if>
                             <td valign="middle" width="192">${requestobj.numberofdevices}</td>
                             <td valign="middle" width="192">${requestobj.requestdate}</td>
                             <td valign="middle" width="192">${requestobj.reqbydate}</td>
                             <td valign="middle" width="192">
                                <c:choose><c:when test="${requestobj.quantityprocessed != null}">
                                                     ${requestobj.quantityprocessed}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose></td>
                             </tr>
                             </logic:iterate> 
                             </logic:notEmpty>
                             <logic:empty name="objRequestList">
                                 <tr>
                                     <td colspan="4"><b>There are no Requests assigned to You !</b></td>
                                 </tr>
                             </logic:empty>
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
                         </table></div>
         </form>   
             
    
    
   