<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script language="javascript" type="text/javascript">
    function onFieldChange(){
        document.forms[1].action="./RaiseRequest.do";
        document.forms[1].submit();
      }
      function checkSelected(){
      var selectedIndex = document.getElementById('itype').selectedIndex;
      if(selectedIndex == -1)
      {
      alert("select atleast one inventory to proceed");
      return false;
      } else {
            if(document.getElementById('shopId').value == document.getElementById('toShop').value)
            {
                alert("From Shop and To Shop cannot be same");
                return false;
            }else {            
                return true;
            }      
      }
      
      
      }
</script>
<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./SumbitTransReq.do" method="POST" onsubmit="return checkSelected();">
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                    
                    <tr><td><label>Request Type</label><span class="mandatory">*</span></td>
                        <td><select name="requesttype" onchange="onFieldChange()">
                                <option value="1">Request</option>
                                <option value="2" >Return</option>
                                <option value="3" selected>Transfer</option>
                    </select></td></tr>
                    <tr><td><label>From Shop</label><span class="mandatory">*</span></td>
                        <logic:notEmpty name="objShopList">
                            <td><select name="shopId" id="shopId" onchange="onFieldChange()">
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
                    <tr><td><label>Select Inventory</label><span class="mandatory">*</span></td>
                        <logic:notEmpty name="objInventoryList" >
                            <td><select name="inventoryIdArray"  id="itype" multiple="true">
                                    <logic:iterate id="objinvlist" name="objInventoryList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                        <option value="${objinvlist.macaddrwan}*${objinvlist.inventoryId}*${objinvlist.userdefined4}*${objinvlist.changeStatus}*${objinvlist.make}*${objinvlist.model}*${objinvlist.inventorytype}">${objinvlist.macaddrwan}-${objinvlist.inventoryId}-${objinvlist.subtype}-${objinvlist.userdefined4}-${objinvlist.inventorytype}</option>
                                    </logic:iterate>
                            </select></td>
                        </logic:notEmpty>
                        <logic:empty name="objInventoryList">
                            
                            <td colspan="4"><b>There are no Inventories to this shop !</b></td>
                            
                        </logic:empty>
                        
                    </tr>
                    <td></td>
                    <tr><td><label>To Shop</label><span class="mandatory">*</span></td>
                        <logic:notEmpty name="objToShopList">
                            <td><select name="toShop" id="toShop">
                                    <logic:iterate id="objShop" name="objToShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == toShop}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>
                            </select></td>
                        </logic:notEmpty>
                        <logic:empty name="objToShopList">
                            
                            <td colspan="4"><b>There are no shops to this TL !</b></td>
                            
                    </logic:empty></tr>
                    <logic:notEmpty name="objToShopList">
                        <logic:notEmpty name="objInventoryList" >
                            <logic:notEmpty name="objShopList">
                            <TR>
                                <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                                          onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                          onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                          src="images/btn-submit-thickbox.gif" 
                                                  name=input> </TD></TR>
                        </logic:notEmpty>
                    </logic:notEmpty>
                     </logic:notEmpty>
                    
                </table>
            </form>
        </div>
    </div>
</div>
                   
  
 

