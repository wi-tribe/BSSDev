<%@page contentType="text/html" import="com.witribe.vo.LeadVO, com.witribe.sales.bo.WitribeSalesBO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
       <logic:notEmpty name="objShopsHierarchyList" >
           <p>Choose any of the following shop hierarchy to modify/delete</p>
       </logic:notEmpty>
       <form action="" method="Post">
                    <input type="hidden" name="page" />
                    <input type="hidden" name="flag" />
                    <div id="inner-content">
                        <logic:notEmpty name="objShopsHierarchyList" >
                                 <div class="scrollable">
                                     <table width="679" border="0" cellspacing="0" cellpadding="0" class="bill-listing">
                                         
                                         <tr>
                                             <th valign="middle" width="25" align="left">&nbsp;</th>
                                             <th valign="middle" width="50" align="left">Shop Id</th>
                                             <th valign="middle" width="80" align="left">Shop name</th>
                                             <th valign="middle" width="50" align="left">Kiosk&nbsp;Id</th>
                                             <th valign="middle" width="100" align="left">Kiosk Name</th>
                                             <th valign="middle" width="50" align="left">Sub&nbsp;Zone</th>
                                             <th valign="middle" width="50" align="left">Zone(s)</th>
                                             <th valign="middle" width="50" align="left">City(s)</th>
                                         </tr>
                                         <logic:iterate id="objShopsHierarchy" name="objShopsHierarchyList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                             <tr>
                                                 <td><input id="check" type=checkbox name="check" value ="${objShopsHierarchy.subShopId}"/></td>
                                                 <td valign="middle" width="50"><bean:write name="objShopsHierarchy" property="shopId"/></td>
                                                 <td valign="middle" width="80"><bean:write name="objShopsHierarchy" property="shopname"/></td>
                                                 <td valign="middle" width="50"><bean:write name="objShopsHierarchy" property="subShopId"/></td>
                                                 <td valign="middle" width="100"><bean:write name="objShopsHierarchy" property="subshopname" /></td>                                        
                                                 <td valign="middle" width="80"><bean:write name="objShopsHierarchy" property="subzone"/></td>
                                                 <td valign="middle" width="50"><bean:write name="objShopsHierarchy" property="zone" /></td>
                                                 <td valign="middle" width="50"><bean:write name="objShopsHierarchy" property="city"/></td>
                                                 
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
                                <tr><td colspan="1"><input type="button" name="Modify"  value="Modify"  onclick="return CommonModify('ModifyShopHierarchy.do');"/></td>
                                <td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('DeleteShopsHierarchy.do');"/></td></tr>
                                </logic:notEmpty >
                                  <logic:empty name="objShopsHierarchyList">
                                 <tr>
                                     <td colspan="4"><b>There are no Shops Heirarchy Details !</b></td>
                                 </tr>
                                 </logic:empty>                        
                </table>
                      
       </div></form>
                      
            

