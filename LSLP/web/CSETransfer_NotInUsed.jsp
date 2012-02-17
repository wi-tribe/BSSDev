<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script language="javascript" type="text/javascript">
    function onFieldChange(){
        document.forms[1].action="./CSETransfer.do";
        document.forms[1].submit();
      }
      function checkSelected(){
       if(document.getElementById('shopId').value == document.getElementById('toShop').value)
            {
                alert("From Shop and To Shop cannot be same");
                return false;
            }else { 
                var test = confirm("Are You Sure Transfer CSE to Shop!");
                    if(test){
                     document.forms[1].action="./CSETransferUpdate.do";
                     document.forms[1].submit();
                     return true;
                     } else {
                     return false;
                     }
                     return true;
            }      
      }
</script>
<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./SumbitTransReq.do" method="POST" onsubmit="return checkSelected();">
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
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
                    <tr><td><label>CSE</label><span class="mandatory">*</span></td>
                        <logic:notEmpty name="objCSEList" >
                            <td><select name="salesId"  id="itype">
                                    <logic:iterate id="objcselist" name="objCSEList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                        <option value="${objcselist.salesId}">${objcselist.salesId}-${objcselist.fullname}</option>
                                    </logic:iterate>
                            </select></td>
                        </logic:notEmpty>
                        <logic:empty name="objCSEList">
                            
                            <td colspan="4"><b>There are no CSE's to this shop !</b></td>
                            
                        </logic:empty>
                        
                    </tr>
                 
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
                        <logic:notEmpty name="objCSEList" >
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
                   
  
 

