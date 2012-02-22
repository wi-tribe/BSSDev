<%@page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.constants.WitribeConstants,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.inventory.vo.DistributeInventoryVO,com.witribe.sales.vo.ShopsVO,java.util.ArrayList,java.util.List"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    
    function onFieldChange(){
        document.forms[1].action="./InvList.do";
        document.forms[1].submit();
      }
</script>
<div id="inner-content">
    <form method="Post">
    <!--<input type="hidden" name="page" />
    <input type="hidden" name="flag" />-->
  <!--<label>Please enter Name or LeadId or CNIC Number or Email-Id or&nbsp;Contact Number or Subzone or Zone or City&nbsp;or&nbsp;Province or Lead Status to Search.</label><br><br>-->
    <table border="0" cellspacing="0" cellpadding="0" class="info-listing">
       <tr><td><label>Select Shop</label><span class="mandatory">*</span></td>
                        <logic:notEmpty name="objShopList">
                            <td><select name="shopId">
                                    <logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == shopId}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>
                            </select></td>
                        </logic:notEmpty>
                        <logic:empty name="objShopList">
                            
                            <td colspan="4"><b>There are no shops to this TL !</b></td>
                            
                    </logic:empty></tr>  
     <logic:notEmpty name="objShopList">
         <tr><td><label>Select Inventory Status</label><span class="mandatory">*</span></td>
           
            <td><select name="searchbystatus">
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_AT_SHOP}">
                            <option value="${WitribeConstants.INV_STATUS_AT_SHOP}" selected>Available At Shop</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INV_STATUS_AT_SHOP}">Available At Shop</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_NEW }">
                            <option value="${WitribeConstants.INV_STATUS_NEW}" selected>New</option>
                        </c:when>
                        <c:otherwise>
                           <option value="${WitribeConstants.INV_STATUS_NEW}">New</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_ASSIGNED }">
                             <option value="${WitribeConstants.INV_STATUS_ASSIGNED}" selected>Assigned</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INV_STATUS_ASSIGNED}">Assigned</option>
                        </c:otherwise>
                    </c:choose>
                   <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_RETURN_ERP }">
                            <option value="${WitribeConstants.INV_STATUS_RETURN_ERP}" selected>Return To ERP</option>
                        </c:when>
                        <c:otherwise>
                           <option value="${WitribeConstants.INV_STATUS_RETURN_ERP}">Return To ERP</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_REFURBISHED}">
                             <option value="${WitribeConstants.INV_STATUS_REFURBISHED}" selected>Refurbished</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INV_STATUS_REFURBISHED}">Refurbished</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_DAMAGED}">
                             <option value="${WitribeConstants.INV_STATUS_DAMAGED}" selected>Damaged</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INV_STATUS_DAMAGED}">Damaged</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_LOST }">
                             <option value="${WitribeConstants.INV_STATUS_LOST}"  selected>Lost</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INV_STATUS_LOST}">Lost</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER }">
                             <option value="${WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER}" selected>Return To ERP Transfer</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER}">Return To ERP Transfer</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchbystatus == WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}">
                              <option value="${WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}" selected>Received Damaged From ERP</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}">Received Damaged From ERP</option>
                        </c:otherwise>
                    </c:choose>     
        </select></td>
        <td><input type="submit" name="ViewInvList" value="InvList"  onclick="onFieldChange()"/></td></tr> 
         </logic:notEmpty>
    </table>
    <logic:notEmpty name="objInventoryList" >
        <div class="scrollable">
     <table width="679" border="1" cellspacing="0" cellpadding="0" class="bill-listing">	
        <tr>
            <th valign="middle" width="131" align="left">Device ID</tH>
            <th valign="middle" width="125" align="left">Sales ID-Name</th>
            <th valign="middle" width="162" align="left">Mac Address</th>
            <th valign="middle" width="192" align="left">Inventory Status</th>
            <th valign="middle" width="192" align="left">Make</th>
            <th valign="middle" width="192" align="left">Model</th>  
            <th valign="middle" width="192" align="left">ItemCode(ERP)</th>
            <th valign="middle" width="192" align="left">ShopId</th>
            <th valign="middle" width="192" align="left">ShopName</th>
            
           <!-- <th valign="middle" width="100" align="left">Assigned To CSE</th>
            <th valign="middle" width="100" align="left">Assigned To TL/RSM</th>
            <th valign="middle" width="100" align="left">Sub Zone</th>
            <th valign="middle" width="100" align="left">Zone(S)</th>
            <th valign="middle" width="65" align="left">City(s)</th>-->
        </tr>
        <tr><% List objlist = (List)request.getAttribute("objInventoryList");
        out.println("Total&nbsp;Inventory&nbsp;List&nbsp;for the Selected Search is :<b>"+objlist.size()+"</b>"); 
         %></tr>
        <logic:iterate id="objinv" name="objInventoryList" type="com.witribe.inventory.vo.DistributeInventoryVO" scope="request">
        <tr><td valign="middle" width="131"><bean:write name="objinv" property="inventoryId" /></td>
            <td valign="middle" width="125">
                <c:choose><c:when test="${objinv.salesId != null}">
                  <bean:write name="objinv" property="salesId"/>
                     <c:if test="${objinv.fullname != null}">
                       -<bean:write name="objinv" property="fullname"/> 
                     </c:if>
                 </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose>
            </td>
            <td valign="middle" width="162"><bean:write name="objinv" property="macaddrwan"/></td>
            <td valign="middle" width="192">
               
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_AT_SHOP}">
                   Available At Shop
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_NEW}">
                   New
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_ASSIGNED}">
                   Assigned
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_RETURN_ERP}">
                   Return To ERP
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_REFURBISHED}">
                    Refurbished
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_DAMAGED}">
                   Damaged
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_LOST}">
                   Lost
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER}">
                   Return To ERP Transfer
                </c:if>
                <c:if test="${searchbystatus == WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}">
                   Damaged From ERP
                </c:if>
            </td>
            <td valign="middle" width="192">
                <c:choose><c:when test="${objinv.make != null}">
                  <bean:write name="objinv" property="make"/> 
                   </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose>
            </td>
            <td valign="middle" width="192">
                <c:choose><c:when test="${objinv.model != null}">
                  <bean:write name="objinv" property="model"/>
                   </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose>
            </td>
            <td valign="middle" width="192">
                <c:choose><c:when test="${objinv.itemcode != null}">
                  <bean:write name="objinv" property="itemcode"/>
                   </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose></td>
             <td valign="middle" width="192">
                <c:choose><c:when test="${objinv.shopId != null}">
                  <bean:write name="objinv" property="shopId"/>
                   </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose></td>
                 <td valign="middle" width="192">
                <c:choose><c:when test="${objinv.shopname != null}">
                  <bean:write name="objinv" property="shopname"/>
                   </c:when>
                 <c:otherwise>
                     --
                 </c:otherwise>
                 </c:choose></td>
        </tr>
        </logic:iterate> 
    </table>
    </div>
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
                <% if(("true".equalsIgnoreCase(nextEnable))) {%>
                <a href="javascript:nextPageClick(${page})">Next</a>
                <%}%>
            </td>
        </tr>
    </table>--%>
    </logic:notEmpty >
    <logic:empty name="objInventoryList">
        <tr>
            <td colspan="4"><b>There are no Inventories to the selected Shop and the selected Inventory Status</b></td>
        </tr>
    </logic:empty>
   </form> </div>




