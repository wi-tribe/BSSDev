<%@page contentType="text/html" import="com.witribe.vo.LeadVO, com.witribe.sales.bo.WitribeSalesBO,com.witribe.sales.vo.ViewSalesHierarchyVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
       <logic:notEmpty name="objShopsToSaleList">
            <p>Choose any of the following Mapping to modify/delete</p>
       </logic:notEmpty>
       <form action="" method="Post">
                    <input type="hidden" name="page" />
                    <input type="hidden" name="flag" />
                    <div id="inner-content">
                         <logic:notEmpty name="objShopsToSaleList">
                                  <div class="scrollable">
                                      <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">
                                         
                                          <tr>
                                              <th valign="middle" width="25" align="left">&nbsp;</th>
                                              <th valign="middle" width="50" align="left">Shop ID</th>
                                              <th valign="middle" width="50" align="left">Sales&nbsp;ID/ Sales&nbsp;Type</th>
                                              <th valign="middle" width="80" align="left">Shop Name</th>
                                              <th valign="middle" width="50" align="left">SalesPerson Name</th>
                                              <!--<th valign="middle" width="50" align="left">Sub&nbsp;Zone</th>-->
                                              <th valign="middle" width="50" align="left">Zone(s)</th>
                                              <th valign="middle" width="50" align="left">City(s)</th>
                                          </tr>
                                          <logic:iterate id="objShopToSales" name="objShopsToSaleList" type="com.witribe.sales.vo.ViewShopSaleVO" scope="request">
                                              <tr>
                                                  <td><input id="check" type=checkbox name="check" value ="${objShopToSales.shopid}~${objShopToSales.shopzone}"/></td>
                                                  <td valign="middle" width="50"><bean:write name="objShopToSales" property="shopid" /></td>                                        
                                                  <td valign="middle" width="50"><bean:write name="objShopToSales" property="idandtype"/></td>
                                                  <td valign="middle" width="80"><bean:write name="objShopToSales" property="shopname"/></td>
                                                  <td valign="middle" width="50"><bean:write name="objShopToSales" property="fullname"/></td>
                                                 <!-- <td valign="middle" width="80"><ean:write name="objShopToSales" property="subzone"/></td>-->
                                                  <td valign="middle" width="50"><bean:write name="objShopToSales" property="shopzone" /></td>
                                                  <td valign="middle" width="50"><bean:write name="objShopToSales" property="city"/></td>
                                                  
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
                         
                        
                                <tr><td colspan="1"><input type="button" name="Modify" value="Modify" onclick="return CommonModify('ModifyShopToSales.do');"/></td>
                                <td colspan="1"><input type="button" name="Delete" value="Delete" onclick="return CommonDelete('DeleteShopToSales.do');"/></td></tr>
                                </logic:notEmpty >
                                  <logic:empty name="objShopsToSaleList">
                                 <tr>
                                     <td colspan="4"><b>No Shop TL mapping available !</b></td>
                                 </tr>
                                 </logic:empty>                        
                </table>
                      
       </div></form>
                    
            

