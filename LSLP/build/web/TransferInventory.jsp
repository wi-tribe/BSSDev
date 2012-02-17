<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    function onFieldChange1(type,val){
           callTransferInventory(type,val);
      }
      function onFieldChange(){
        document.forms[1].action="./TransferInventory.do";
        document.forms[1].submit();
      }
      function checkSelected(){
      $('p.validationmsg').hide();
      var selectedIndex = document.getElementById('itype').selectedIndex;
      
      if(selectedIndex == -1)
      {
      showMessage(document.getElementById('itype'), 'select atleast one inventory to proceed');
      return(false);
      } 
      var shopindex = document.getElementById('toShop').selectedIndex;
       if(shopindex == -1){
           showMessage(document.getElementById('toShop'), 'select atleast one Shop to proceed');
          return(false);
       }
       if(document.getElementById('childSalesId').options[document.getElementById('childSalesId').selectedIndex].value == ''){
           showMessage(document.getElementById('childSalesId'), 'Select SD');
           return(false);
       }
      
      /* if(document.getElementById('childRSMId').style.display != "none"){
        alert(document.getElementById('childRSMId').style.display);
       if(document.getElementById('childRSMId').options[document.getElementById('childRSMId').selectedIndex].value == ''){
           showMessage(document.getElementById('childRSMId'), 'Select RSM');
           return(false);
       }
      
       if(document.getElementById('childTLId').options[document.getElementById('childTLId').selectedIndex].value == ''){
           showMessage(document.getElementById('childTLId'), 'Select TL');
           return(false);
       }
       if(document.getElementById('tochildRSMId').options[document.getElementById('tochildRSMId').selectedIndex].value == ''){
           showMessage(document.getElementById('tochildRSMId'), 'Select To RSM');
           return(false);
       }
       if(document.getElementById('tochildTLId').options[document.getElementById('tochildTLId').selectedIndex].value == ''){
           showMessage(document.getElementById('tochildTLId'), 'Select To TL');
           return(false);
       }
       }*/
       if(document.getElementById('shopId').options[document.getElementById('shopId').selectedIndex].value == ''){
           showMessage(document.getElementById('shopId'), 'Select From Shop');
           return(false);
       }
       if(document.getElementById('itype').options[document.getElementById('itype').selectedIndex].value == ''){
           showMessage(document.getElementById('itype'), 'Inventory should not be blank');
           return(false);
       }
       if(document.getElementById('tochildTLId').options[document.getElementById('tochildTLId').selectedIndex].value == ''){
           showMessage(document.getElementById('tochildTLId'), 'Select To TL');
           return(false);
       }
       if(document.getElementById('toShop').options[document.getElementById('toShop').selectedIndex].value == ''){
           showMessage(document.getElementById('toShop'), 'Select To Shop');
           return(false);
       }
       
       if(document.getElementById('shopId').value == document.getElementById('toShop').value)
        {
            //alert("From Shop and To Shop cannot be same");
             showMessage(document.getElementById('shopId'), 'From Shop and To Shop cannot be same');
            return false;
        }
      
      
      }
</script>
<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./SumbitTransReq.do" method="POST" onsubmit="return checkSelected();">
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                    <c:if test="${role == WitribeConstants.TYPE_ADMIN}">
                        <tr><td><label>Sales Person SD</label><span class="mandatory">*</span></td>
                            <logic:notEmpty name="objProvList">
                                <td><select name="childSalesId" id = "childSalesId" onchange="onFieldChange1(2,this.options[this.selectedIndex].value);">
                                    <option></option>
                                        <logic:iterate id="objShop" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                            <option value="${objShop.childSalesId}">${objShop.childSalesId}-${objShop.fullname}</option>  
                                        </logic:iterate>
                                </select><p class="validationmsg"></p></td>
                        </logic:notEmpty>
                        <logic:empty name = "objProvList">
                            <td> there are no Sales Persons in the herarchy</td>
                        </logic:empty></tr>
                         <tr><td><label>Sales Person RSM</label><span class="mandatory">*</span></td>
                            <td><select name="childRSMId" id="childRSMId" onchange="onFieldChange1(1,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>
                        <tr><td><label>Sales Person TL</label><span class="mandatory">*</span></td>
                            <td><select name="childTLId" id="childTLId" onchange="onFieldChange1(3,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>
                       
                        <tr><td><label>From Shop</label><span class="mandatory">*</span></td>
                            <td><select name="shopId" id="shopId" onchange="onFieldChange1(4,this.options[this.selectedIndex].value)">
                                    <%--<logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == shopId}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>--%>
                                    <option></option>
                            </select><p class="validationmsg"></p></td>
                        <tr><td><label>Select Inventory</label><span class="mandatory">*</span></td>
                        
                            <td><select name="inventoryIdArray"  id="itype" multiple="true">
                                    
                            </select><p class="validationmsg"></p></td>
                      
                    </tr>
                    <td></td>
                     <tr><td><label>To RSM</label><span class="mandatory">*</span></td>
                            <td><select name="childRSMId" id="tochildRSMId" onchange="onFieldChange1(5,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>
                         <tr><td><label>To TL</label><span class="mandatory">*</span></td>
                            <td><select name="childTLId" id="tochildTLId" onchange="onFieldChange1(6,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>
                    <tr><td><label>To Shop</label><span class="mandatory">*</span></td>
                        
                            <td><select name="toShop" id="toShop">
                                   <%-- <logic:iterate id="objShop" name="objToShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == toShop}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>--%>
                            </select><p class="validationmsg"></p></td>
                        </tr>
                    </c:if> 
                    <c:if test="${role == WitribeConstants.TYPE_NSM}"> 
                        <tr><td><label>Sales Person RSM</label><span class="mandatory">*</span></td>
                            <logic:notEmpty name="objProvList">
                                <td><select name="childSalesId" id = "childSalesId" onchange="onFieldChange1(1,this.options[this.selectedIndex].value);">
                                    <option></option>
                                        <logic:iterate id="objShop" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                            <option value="${objShop.childSalesId}">${objShop.childSalesId}-${objShop.fullname}</option>  
                                        </logic:iterate>
                                </select><p class="validationmsg"></p></td>
                        </logic:notEmpty>
                        <logic:empty name = "objProvList">
                            <td> there are no Sales Persons in the herarchy</td>
                        </logic:empty></tr>
                        <tr><td><label>Sales Person TL</label><span class="mandatory">*</span></td>
                            <td><select name="childTLId" id="childTLId" onchange="onFieldChange1(3,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>
                        <tr><td><label>From Shop</label><span class="mandatory">*</span></td>
                            <td><select name="shopId" id="shopId" onchange="onFieldChange1(4,this.options[this.selectedIndex].value)">
                                    <%--<logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == shopId}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>--%>
                                    <option></option>
                            </select><p class="validationmsg"></p></td>
                        <tr><td><label>Select Inventory</label><span class="mandatory">*</span></td>
                        
                            <td><select name="inventoryIdArray"  id="itype" multiple="true">
                                    
                            </select><p class="validationmsg"></p></td>
                      
                    </tr>
                    <td></td>
                    <!--<tr><td><label>To RSM</label><span class="mandatory">*</span></td>
                            <td><select name="childRSMId" id="tochildRSMId" onchange="onFieldChange1(5,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>-->
                         <tr><td><label>To TL</label><span class="mandatory">*</span></td>
                            <td><select name="childTLId" id="tochildTLId" onchange="onFieldChange1(6,this.options[this.selectedIndex].value);">
                                        <option></option>
                                </select><p class="validationmsg"></p></td>
                        </tr>
                    <tr><td><label>To Shop</label><span class="mandatory">*</span></td>
                        
                            <td><select name="toShop" id="toShop">
                                   <%-- <logic:iterate id="objShop" name="objToShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == toShop}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>--%>
                            </select><p class="validationmsg"></p></td>
                        </tr>
                    </c:if>
                    <c:if test="${role == WitribeConstants.TYPE_RSM}">
                        <tr><td><label>Sales Person TL</label><span class="mandatory">*</span></td>
                           <logic:notEmpty name="objProvList">
                                <td><select name="childSalesId" id = "childSalesId" onchange="onFieldChange1(3,this.options[this.selectedIndex].value);">
                                    <option></option>
                                        <logic:iterate id="objShop" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                            <option value="${objShop.childSalesId}">${objShop.childSalesId}-${objShop.fullname}</option>  
                                        </logic:iterate>
                                </select><p class="validationmsg"></p></td>
                        </logic:notEmpty>
                        <logic:empty name = "objProvList">
                            <td> there are no Sales Persons in the herarchy</td>
                        </logic:empty></tr>
                        <tr><td><label>From Shop</label><span class="mandatory">*</span></td>
                            <td><select name="shopId" id="shopId" onchange="onFieldChange1(4,this.options[this.selectedIndex].value)">
                                    <%--<logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == shopId}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>--%>
                                    <option></option>
                            </select><p class="validationmsg"></p></td>
                        <tr><td><label>Select Inventory</label><span class="mandatory">*</span></td>
                        
                            <td><select name="inventoryIdArray"  id="itype" multiple="true">
                                    
                            </select><p class="validationmsg"></p></td>
                      
                    </tr>
                    <td></td>
                    <tr><td><label>To TL</label><span class="mandatory">*</span></td>
                           <logic:notEmpty name="objProvList">
                                <td><select name="childSalesId" id = "tochildTLId" onchange="onFieldChange1(6,this.options[this.selectedIndex].value);">
                                    <option></option>
                                        <logic:iterate id="objShop" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                            <option value="${objShop.childSalesId}">${objShop.childSalesId}-${objShop.fullname}</option>  
                                        </logic:iterate>
                                </select><p class="validationmsg"></p></td>
                        </logic:notEmpty>
                    <tr><td><label>To Shop</label><span class="mandatory">*</span></td>
                        
                            <td><select name="toShop" id="toShop">
                                   <%-- <logic:iterate id="objShop" name="objToShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        <c:choose>
                                            <c:when test="${objShop.shopId == toShop}">
                                                <option value="${objShop.shopId}" selected>${objShop.shopId}-${objShop.shopname}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option>
                                            </c:otherwise>
                                        </c:choose> 
                                        
                                        
                                    </logic:iterate>--%>
                            </select><p class="validationmsg"></p></td>
                        </tr>
                           </c:if>
                    <%--<c:if test="${role == WitribeConstants.TYPE_TL}">
                       <logic:notEmpty name="objProvList">
                                <td><select name="childSalesId" id = "childSalesId" onchange="onFieldChange1(3,this.options[this.selectedIndex].value);">
                                    <option></option>
                                        <logic:iterate id="objShop" name="objProvList" type="com.witribe.sales.vo.SalesPersonnelVO" scope="request">
                                            <option value="${objShop.childSalesId}">${objShop.childSalesId}-${objShop.fullname}</option>  
                                        </logic:iterate>
                                </select></td>
                        </logic:notEmpty>
                        <logic:empty name = "objProvList">
                            <td> there are no Sales Persons in the herarchy</td>
                        </logic:empty></tr>
                    </c:if>--%>
                    
                    
                     <logic:notEmpty name="objProvList">
                            <TR>
                                <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                                          onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                          onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                          src="images/btn-submit-thickbox.gif" 
                                                  name=input> </TD></TR>
                        </logic:notEmpty>
                   
                    
                </table>
            </form>
        </div>
    </div>
</div>
                   
  
 

