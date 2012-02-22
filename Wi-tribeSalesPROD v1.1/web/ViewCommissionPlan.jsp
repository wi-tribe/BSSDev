<%@page contentType="text/html" import="com.witribe.commission.vo.CommissionPlanVO"%>
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
                         <logic:notEmpty name="objPlansList" >
                         <div class="scrollable">
                             <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">
                                 <tr>
                                     <th valign="middle" width="25" align="left">&nbsp;</th>
                                     <th valign="middle" width="80" align="left">Plan Id</th>
                                     <th valign="middle" width="80" align="left">Plan Name</th>
                                     <th valign="middle" width="80" align="left">Plan Status</th>
                                     <!--<th valign="middle" width="150" align="left">Plan Type</th>-->
                                     <th valign="middle" width="100" align="left">Start Date</th>
                                     <th valign="middle" width="50" align="left">End Date</th>
                                </tr>
                                 
                                 <logic:iterate id="objSale" name="objPlansList" type="com.witribe.commission.vo.CommissionPlanVO" scope="request">
                                     <tr><td><input id="check" type=checkbox name="check" value="${objSale.planId}"/></td>
                                         <td valign="middle" width="50">${objSale.planId}</td>
                                         
                                         <td valign="middle" width="80">${objSale.planDesc}</th>
                                         
                                         <c:set var="planstatus" value="${objSale.planStatus}"/>
                                          <c:choose><c:when test="${planstatus == 1}">
                                                     <td valign="middle" width="80">Active</td>
                                                     </c:when>
                                                     <c:otherwise>
                                                         <td valign="middle" width="80">In Active</td>
                                                     </c:otherwise>
                                                     </c:choose>
                                        
                                         <td valign="middle" width="100">${objSale.startDate}</td>
                                         
                                         <td valign="middle" width="100">${objSale.endDate}</td>
                                        
                                     </tr>
                                     <!--<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>-->
                                 </logic:iterate> 
                                 
                         </table></div>
                         <%--<table border="0" align="right" width="150">
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
                         </table>--%>
                         <table>
                         <tr><td colspan="1"><input type="button" name="Modify" value="Modify" onclick="return CommonModify('./ViewCommissionPlan.do');"/></td>
                         <!--<td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('./DeleteSalesPersonnel.do');"/></td></tr>-->
                         </logic:notEmpty >
                                  <logic:empty name="objPlansList">
                                 <tr>
                                     <td colspan="4"><b>There are no Plans to View !</b></td>
                                 </tr>
                                 </logic:empty>
                                                 
             </table></div></form>
             
    

              
