<%@page import="com.witribe.inventory.vo.RaiseRequestVO,com.witribe.constants.WitribeConstants"  %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<div id="inner-content">
       <form action="./ViewInventory.do" method="post">
              <input type="hidden" name="page" />
              <input type="hidden" name="flag" />
             
              <logic:notEmpty name="objCSEInvList" >   
                             <div class="scrollable">
                             <table width="679" border="0" cellspacing="0" cellpadding="0"  class="bill-listing">
                             
                             <tr>
                                 <th valign="middle" width="131" align="left">Device ID</th>
                                 <th valign="middle" width="125" align="left">Inventory Sub Type</th>
                                 <th valign="middle" width="162" align="left">State Id</th>
                                 <th valign="middle" width="192" align="left">Make</th>
                                 <th valign="middle" width="192" align="left">Model</th>
                                 <th valign="middle" width="192" align="left">Source</th>
                             </tr>
                             
                             <logic:iterate id="requestobj" name="objCSEInvList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                             <tr><td valign="middle" width="131">${requestobj.inventoryId}</a></td>
                               <td valign="middle" width="131">${requestobj.subtype}</td>
                             <c:set var="changeStatus" value="${requestobj.changeStatus}"/>
                             <td>
                              <c:if test="${changeStatus == WitribeConstants.INV_STATUS_AT_SHOP}">
                                       Available At Shop
                              </c:if>
                              <c:if test="${changeStatus == WitribeConstants.INV_STATUS_NEW}">
                                       New
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INV_STATUS_ASSIGNED}">
                                       Assigned
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INV_STATUS_RETURN_ERP}">
                                       Return To ERP
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INV_STATUS_REFURBISHED}">
                                        Refurbished
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INV_STATUS_DAMAGED}">
                                       Damaged
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INV_STATUS_LOST}">
                                       Lost
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER}">
                                       Return To ERP Transfer
                                    </c:if>
                                    <c:if test="${changeStatus == WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}">
                                       Damaged From ERP
                                    </c:if>
                               </td>
                                 <c:if test="null">
                                     <td valign="middle" width="125">--</td> 
                                 </c:if>
                             <td valign="middle" width="192">${requestobj.make}</td>
                             <td valign="middle" width="192">${requestobj.model}</td>
                             <td valign="middle" width="192">${requestobj.shopId}</td>
                                        </tr>
                             </logic:iterate> 
                             </logic:notEmpty>
                             <logic:empty name="objCSEInvList">
                                 <tr>
                                     <td colspan="4"><b>There are no Inventory assigned to You !</b></td>
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
             
    
    
   