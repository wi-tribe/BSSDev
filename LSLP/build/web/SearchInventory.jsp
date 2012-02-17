<%@page contentType="text/html" import="com.witribe.inventory.vo.DistributeInventoryVO,com.witribe.bo.WitribeBO,com.witribe.constants.WitribeConstants,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
     function trim(str) {
        return str.replace(/^\s+|\s+$/g,"");
    }
    function SearchInv(){
     document.forms[1].searchinv.value = trim(document.forms[1].searchinv.value);
    if(document.forms[1].searchinv.value == ''){
        $('p.validationmsg').hide();
        showMessage(document.forms[1].searchinv, 'Please Enter data before search');
        return false;
    } else {
           document.forms[1].action="./SearchInvList.do";
           document.forms[1].submit();
            return true;
         }
        
    }
</script>
<div id="inner-content">
    <form method="Post">
    <!--<input type="hidden" name="page" />
    <input type="hidden" name="flag" />-->
  <!--<label>Please enter Name or LeadId or CNIC Number or Email-Id or&nbsp;Contact Number or Subzone or Zone or City&nbsp;or&nbsp;Province or Lead Status to Search.</label><br><br>-->
    <table border="0" cellspacing="0" cellpadding="0" class="info-listing">
         
        <tr>
              <td><select name="searchby">
               
                    <c:choose>
                        <c:when test="${searchby == 'DEVICE_ID'}">
                            <option value="DEVICE_ID" selected>Device Id</option>
                        </c:when>
                        <c:otherwise>
                            <option value="DEVICE_ID">Device Id</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${searchby == 'MAC_ADDR_WAN'}">
                            <option value="MAC_ADDR_WAN" selected>Mac Address</option>
                        </c:when>
                        <c:otherwise>
                           <option value="MAC_ADDR_WAN">Mac Address</option>
                        </c:otherwise>
                    </c:choose>
                    
                   
        </select></td>
        <td><label>Search By</label></td>
    </tr>
     <tr><td><input type="text" name="searchinv" width="120" value="${searchinv}"/><p class="validationmsg"></p></td>  
        <td><input type="submit" name="search" value="Search"  onclick="return SearchInv();"/></td></tr>   
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
        <%--<tr><% List objlist = (List)request.getAttribute("objInventoryList");
        out.println("Total&nbsp;Inventory&nbsp;List&nbsp;for the Selected Search is :<b>"+objlist.size()+"</b>"); 
         %></tr>--%>
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
            
            <c:set var="changeStatus" value="${objinv.changeStatus}"/>
            <td valign="middle" width="192">
               
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
    </logic:notEmpty >
    <logic:empty name="objInventoryList">
        <tr>
            <td colspan="4"><b>There are no Inventories for the selected Value</b></td>
        </tr>
    </logic:empty>
   </form> </div>




