<%@page contentType="text/html" import="com.witribe.vo.LeadVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
 <p>Choose any of the following Shop to Change status</p>
   <form action="./ChangeShopStatus.do" method="Post">
         <input type="hidden" name="page" />
         <input type="hidden" name="flag" />
        <div id="inner-content"> 
             <logic:notEmpty name="objShopsList" >
        <div class="scrollable">                       
                    <table width="679"  border="1"  cellspacing="0" cellpadding="0" class="bill-listing">
                 
                    <tr>
                        <th valign="middle" width="30" align="left">&nbsp;</th>
                        <th valign="middle" width="50" align="left">Shop ID</th>
                        <th valign="middle" width="100" align="left">Shop Type</th>
                        <th valign="middle" width="100" align="left">Shop Name</th>
                        <th valign="middle" width="80" align="left">House/Plot</th>
                        <th valign="middle" width="50" align="left">Street</th>
                        <th valign="middle" width="100" align="left">Sub Zone</th>
                        <th valign="middle" width="80" align="left">Zone(s)</th>
                        <th valign="middle" width="80" align="left">City(s)</th>
                        <th valign="middle" width="100" align="left">Shop Status</th
                    </tr>
                    <logic:iterate id="objShop" name="objShopsList" type="com.witribe.sales.vo.ShopsVO" scope="request">                                  
                    <tr><td valign="middle" width="30"><input id="checkid" type=checkbox name="check" value="${objShop.shopId}"/></td>
                    <td valign="middle" width="50">${objShop.shopId}</a></td>
                    <c:set var="shopType" value="${objShop.shopType}"/>
                    <c:if test="${shopType == '1'}">
                        <td valign="middle" width="100">wi-tribe owned</td> 
                    </c:if>
                    <c:if test="${shopType == '2'}">
                        <td valign="middle" width="50">Kiosks</td> 
                    </c:if>
                    <td valign="middle" width="100">
                    <c:choose><c:when test="${objShop.shopname != null}">
                                                     ${objShop.shopname}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                    </td>
                    <td valign="middle" width="80">
                    <c:choose><c:when test="${objShop.plot != null}">
                                                     ${objShop.plot}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                    </td>
                    <td valign="middle" width="80">
                    <c:choose><c:when test="${objShop.street != null}">
                                                     ${objShop.street}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose></td>
                    <td valign="middle" width="100">
                    <c:choose><c:when test="${objShop.subzone != null}">
                                                     ${objShop.subzone}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose>
                    </td>
                       
                    <td valign="middle" width="80">${objShop.zone}</td>
                    <td valign="middle" width="70">${objShop.city}</td>
                     <td valign="middle" width="100">
                     <c:choose><c:when test="${objShop.shopStatus != null}">
                                                     ${objShop.shopStatus}
                                                     </c:when>
                                                     <c:otherwise>
                                                         --
                                                     </c:otherwise>
                                                     </c:choose></td>
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
                      <tr> <td><label><b>Shop Status</b></label></td>    
                                <td><select name="shopStatus">

                                    <c:choose>
                                        <c:when test="${shopStatus == 'Active'}">
                                            <option value="Active" selected>Active</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="Active">Active</option>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${shopStatus == 'Inactive'}">
                                            <option value="Inactive" selected>Inactive</option>
                                        </c:when>
                                        <c:otherwise>
                                           <option value="Inactive">Inactive</option>
                                        </c:otherwise>
                                    </c:choose>
                                   
                   
                                </select></td>
         <tr><td colspan="1"><input type="button" name="Submit" value="Submit" onclick="return changeShopStatus('./SubmitShopStatus.do',this);"/></td>
                        </tr>
                      
                      </logic:notEmpty >
                                  <logic:empty name="objShopsList">
                                 <tr>
                                     <td colspan="4"><b>There are no Shops !</b></td>
                                 </tr>
                                 </logic:empty>
           </table></div></form>
   

              