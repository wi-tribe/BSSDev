<%@page contentType="text/html" import="com.witribe.vo.LeadVO, com.witribe.sales.bo.WitribeSalesBO,com.witribe.sales.vo.ViewSalesHierarchyVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
       <logic:notEmpty name="objSalesHierarchyList" >
           <p>Choose any of the following Sales Hierarchy to delete</p>
       </logic:notEmpty>
       <form action="" method="Post">
                    <input type="hidden" name="page" />
                    <input type="hidden" name="flag" />
                    <div id="inner-content">
                        <logic:notEmpty name="objSalesHierarchyList" >
                                 <div class="scrollable">
                                     <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">
                                         
                                         <tr>
                                             <th valign="middle" width="25" align="left">&nbsp;</th>
                                             <th valign="middle" width="50" align="left">Manager&nbsp;ID</th>
                                             <th valign="middle" width="80" align="left">Manager&nbsp;Name</th>
                                             <th valign="middle" width="50" align="left">Sales Person ID/Type</th>
                                             <th valign="middle" width="50" align="left">Shop Mapped</th>
                                             <th valign="middle" width="100" align="left">Sales&nbsp;Person&nbsp;Name</th>
                                             <th valign="middle" width="50" align="left">Sub&nbsp;Zone</th>
                                             <th valign="middle" width="50" align="left">Zone(s)</th>
                                             <th valign="middle" width="50" align="left">City(s)</th>
                                         </tr>
                                         <logic:iterate id="objSalesHierarchy" name="objSalesHierarchyList" type="com.witribe.sales.vo.ViewSalesHierarchyVO" scope="request">
                                             <tr>
                                                 <td><input id="check" type=checkbox name="check" value ="${objSalesHierarchy.childsalesid}"/></td>
                                                 <td valign="middle" width="50"><bean:write name="objSalesHierarchy" property="parentsalesid"/></td>
                                                 <td valign="middle" width="80"><bean:write name="objSalesHierarchy" property="parentname"/></td>
                                                 <td valign="middle" width="50"><bean:write name="objSalesHierarchy" property="idandtype"/></td>
                                                 <td valign="middle" width="50"><c:choose>
                                                     <c:when test="${objSalesHierarchy.shopid != null}">
                                                     ${objSalesHierarchy.shopid}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                                                 </td>
                                                 <td valign="middle" width="100"><bean:write name="objSalesHierarchy" property="childname" /></td>                                        
                                                 <td valign="middle" width="80"><bean:write name="objSalesHierarchy" property="subzone"/></td>
                                                 <td valign="middle" width="50"><bean:write name="objSalesHierarchy" property="zone" /></td>
                                                 <td valign="middle" width="50"><bean:write name="objSalesHierarchy" property="city"/></td>
                                                 
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
                                 <%if(("true".equalsIgnoreCase(nextEnable))) {%>
                                 <a href="javascript:nextPageClick(${page})">Next</a>
                                 <%}%>
                                 </td>
                             </tr>
                         </table>
                         <table>
                                <tr><td colspan="1"><!--<input type="button" name="Modify" value="Modify"  disabled="true" onclick="return CommonModify('ModifySalesHierarchy.do');"/>--></td>
                                <td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('DeleteSalesHierarchy.do');"/></td></tr>
                                </logic:notEmpty >
                                  <logic:empty name="objSalesHierarchyList">
                                 <tr>
                                     <td colspan="4"><b>There are no Sales Heirarchy Details !</b></td>
                                 </tr>
                                 </logic:empty>                        
                </table>
                      
       </div></form>
                      
            

