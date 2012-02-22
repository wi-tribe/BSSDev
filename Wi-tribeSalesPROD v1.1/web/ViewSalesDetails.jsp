<%@page contentType="text/html" import="com.witribe.vo.LeadVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<form action="" method="Post">
    <input type="hidden" name="page" />
     <input type="hidden" name="flag" />
     <logic:notEmpty name="objSalesList" >
        <p>Choose any of the following Salespersons to modify/delete</p>
     </logic:notEmpty>
                     <div id="inner-content">
                         <logic:notEmpty name="objSalesList" >
                         <div class="scrollable">
                             <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">
                                 
                                 <tr>
                                     <th valign="middle" width="25" align="left">&nbsp;</th>
                                     <th valign="middle" width="80" align="left">Sales Id</th>
                                     <th valign="middle" width="80" align="left">Sales Type</th>
                                     <th valign="middle" width="150" align="left">Channel Type</th>
                                     <th valign="middle" width="100" align="left">Full Name</th>
                                     <th valign="middle" width="50" align="left">House/Plot</th>
                                     <th valign="middle" width="100" align="left">Street</th>
                                     <th valign="middle" width="100" align="left">Sub Zone</th>
                                     <th valign="middle" width="100" align="left">Zone(s)</th>
                                     <th valign="middle" width="50" align="left">City(s)</th>
                                 </tr>
                                 
                                 <logic:iterate id="objSale" name="objSalesList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                     <tr><td><input id="check" type=checkbox name="check" value="${objSale.salesId}"/></td>
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
                                         <c:if test="${salesType == WitribeConstants.TYPE_CSR}">
                                             <td valign="middle" width="50">CSR</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_BO}">
                                             <td valign="middle" width="50">BO-Dealer</td> 
                                         </c:if>
                                         <c:if test="${salesType == WitribeConstants.TYPE_NBO}">
                                             <td valign="middle" width="50">NBO-Dealer</td> 
                                         </c:if>
                                         <c:set var="channelType" value="${objSale.channeltype}"/>
                                         <c:choose>
                                             <c:when test="${channelType != null}">
                                                 <c:if test="${channelType == WitribeConstants.CSE_DIRECT_SALE}">
                                                     <td valign="middle" width="150">Direct Sale</td> 
                                                 </c:if>
                                                 <c:if test="${channelType == WitribeConstants.CSE_SHOP_SALES_EXECUTIVE}">
                                                     <td valign="middle" width="150">Shop-Sales Executive</td> 
                                                 </c:if>
                                                 <c:if test="${channelType == WitribeConstants.CSE_BD}">
                                                     <td valign="middle" width="150">BD-Business development</td> 
                                                 </c:if>
                                                 <c:if test="${channelType == WitribeConstants.CSE_KIOSKS}">
                                                     <td valign="middle" width="150">Kiosks</td> 
                                                 </c:if>
                                                 <c:if test="${channelType == WitribeConstants.CSE_BO}">
                                                     <td valign="middle" width="150">BO</td> 
                                                 </c:if>
                                                 <c:if test="${channelType == WitribeConstants.CSE_NBO}">
                                                     <td valign="middle" width="150">NBO</td> 
                                                 </c:if>
                                                 <c:if test="${channelType == WitribeConstants.CSE_CSO}">
                                                     <td valign="middle" width="150">CSO</td> 
                                                 </c:if>
                                             </c:when>
                                             <c:otherwise>
                                                 <td valign="middle" width="50">--</td>
                                             </c:otherwise>
                                         </c:choose>
                                         <td valign="middle" width="100">${objSale.fullname}</td>
                                         <td valign="middle" width="100">                                             
                                             <c:choose><c:when test="${objSale.plot != null}">
                                                     ${objSale.plot}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                         </td>
                                         <td valign="middle" width="100">
                                         <c:choose><c:when test="${objSale.street != null}">
                                                     ${objSale.street}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                     </td>
                                         <td valign="middle" width="100">
                                            <c:choose><c:when test="${objSale.subzone != null}">
                                                     ${objSale.subzone}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose></td>
                                         <td valign="middle" width="100">
                                     <c:choose><c:when test="${objSale.zone != null}">
                                                     ${objSale.zone}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose></td>
                                         <td valign="middle" width="50">
                                     <c:choose><c:when test="${objSale.city != null}">
                                                     ${objSale.city}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose></td>
                                     </tr>
                                     <!--<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>-->
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
                                  <%if(("true".equalsIgnoreCase(nextEnable))) {%>
                                 <a href="javascript:nextPageClick(${page})">Next</a>
                                 <%}%>
                                 </td>
                             </tr>
                         </table>
                         <table>
                         <tr><td colspan="1"><input type="button" name="Modify" value="Modify" onclick="return CommonModify('./ViewSales.do');"/></td>
                         <td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('./DeleteSalesPersonnel.do');"/></td></tr>
                         </logic:notEmpty >
                                  <logic:empty name="objSalesList">
                                 <tr>
                                     <td colspan="4"><b>There are no Sales Persons !</b></td>
                                 </tr>
                                 </logic:empty>
                                                 
             </table></div></form>
             
    

              
