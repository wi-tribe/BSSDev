<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.inventory.vo.DistributeInventoryVO,com.witribe.inventory.vo.RaiseRequestVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    function bodyload(){
            document.getElementById('typerouter').style.display = "none";
            //document.getElementById('mrouter').style.display = "none";
            }
            function ChangeInvType(invtype){
           var subtype = "";
           
           callDistributeInventory(invtype,subtype);
         }
         
         
         function ChangeInvSubType(invsubtype){
           var shopId = document.forms[1].shopId.options[document.forms[1].shopId.options.selectedIndex].value
           var invtype = document.forms[1].inventorytype.options[document.forms[1].inventorytype.options.selectedIndex].value
           callShopInventory(shopId,invtype,invsubtype);
         }
           
            function ChangeType(){
            var selectedtext =document.forms[1].inventorytype.options[document.forms[1].inventorytype.options.selectedIndex].value;
            if(selectedtext == "2"){
            document.getElementById('typerouter').style.display = "";
            //document.getElementById('mrouter').style.display = "";
            document.getElementById('typecpe').style.display = "none";
            //document.getElementById('mcpe').style.display = "none";
            } else if(selectedtext == "1"){
            document.getElementById('typecpe').style.display = "";
            //document.getElementById('mcpe').style.display = "";
            document.getElementById('typerouter').style.display = "none";
            //document.getElementById('mrouter').style.display = "none";
              }
         
            }
            function onShopChange(){
                document.forms[1].action="./DistributeInventory.do";
                document.forms[1].submit();
            }
           function checkSelected(){
               var selectedIndex = document.getElementById('intype').selectedIndex;
               
               if(selectedIndex == -1)
              {
                alert("select atleast one inventory to proceed");
                return false;
              } 
              else {
              return true;
              }
              }
</script>

<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<body>
    <div id="inner-content">                    
        <div class="shadow-box">
            <div class="shadow-box-inner">
            
                <form action="./SubmitDistributeInventory.do" method="Post" onsubmit="return checkSelected();">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                         <logic:notEmpty name="objShopList" >
                             <input type="hidden" name="changeStatus" value="2">
                             <tr><td><label>Shop Id</label><span class="mandatory">*</span></td>
                           
                            <td><select name="shopId" onchange="onShopChange()">
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
                       
                        <logic:empty name="objShopList">
                                     <td colspan="4"><b>There are no shops to this TL !</b></td>
                                 </logic:empty>
                                 </tr>
                                 <tr><td><label>Inventory Type</label><span class="mandatory">*</span></td>
                            <%--<td><select name="inventorytype"  id="itype" onchange="onShopChange()">
                                <c:choose>
                                    <c:when test="${invtype == 'CPE'}">
                                        <option value="CPE" selected>CPE</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="CPE">CPE</option>
                                    </c:otherwise>
                                 </c:choose> 
                                 <c:choose>
                                    <c:when test="${invtype == 'ROUTER'}">
                                         <option value="ROUTER" selected>ROUTER</option>
                                   </c:when>
                                    <c:otherwise>
                                        <option value="ROUTER">ROUTER</option>
                                    </c:otherwise>
                                 </c:choose>
                                 <c:choose>
                                    <c:when test="${invtype == 'OTHER'}">
                                         <option value="OTHER" selected>OTHER</option>
                                    </c:when>
                                    <c:otherwise>
                                         <option value="OTHER">OTHER</option>
                                    </c:otherwise>
                                 </c:choose>
                        </select></td>--%>
                     <td><select name="inventorytype"  id="itype" onchange="ChangeInvType(this.options[this.selectedIndex].value)">
                                    <logic:notEmpty name="objTypeList" >
                                        <option></option>
                                        <logic:iterate id="objInv" name="objTypeList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                           <option value="${objInv.inventorytype}">${objInv.inventorytype}</option>
                                            
                                    </logic:iterate></logic:notEmpty>
                        </select><p class="validationmsg"></p></td>
                    
                    </tr>
                        <tr><td><label>Inventory Sub Type</label><span class="mandatory">*</span></td>
                            <%--<td id="typecpe"><select name="subtype" onchange="onShopChange()">
                                 <c:choose>
                                    <c:when test="${invsubtype == 'Indoor'}">
                                         <option value="Indoor" selected>Indoor</option>
                                    </c:when>
                                    <c:otherwise>
                                          <option value="Indoor">Indoor</option>
                                    </c:otherwise>
                                 </c:choose>
                                   <c:choose>
                                    <c:when test="${invsubtype == 'Outdoor'}">
                                         <option value="Outdoor" selected>Outdoor</option>
                                    </c:when>
                                    <c:otherwise>
                                          <option value="Outdoor">Outdoor</option>
                                    </c:otherwise>
                                 </c:choose>
                                   <c:choose>
                                    <c:when test="${invsubtype == 'Nomadic'}">
                                         <option value="Nomadic" selected>Nomadic</option>
                                    </c:when>
                                    <c:otherwise>
                                          <option value="Nomadic">Nomadic</option>
                                    </c:otherwise>
                                 </c:choose>
                                 <c:choose>
                                    <c:when test="${invsubtype == 'USB Dongle'}">
                                         <option value="USB Dongle" selected>USB Dongle</option>
                                    </c:when>
                                    <c:otherwise>
                                          <option value="USB Dongle">USB DONGLE</option>
                                    </c:otherwise>
                                 </c:choose>
                            </select></td>               
                            <td id="typerouter"><select name="subtype" onchange="onShopChange()">
                                    <option value="1">wi-fi AP</option>
                                    <option value="2">Wi-Fi Router</option>
                        </select></td> --%>
                    
                    <td id="typecpe"><select name="subtype" id="subtypecpe" onchange="ChangeInvSubType(this.options[this.selectedIndex].value)">
                          
                            </select><p class="validationmsg"></p></td>
                    </tr>
                        <tr><td><label>Select Inventory</label><span class="mandatory">*</span></td>
                            <td><select name="poidArray"  id="intype" multiple="true" onblur="checkSelected();">
                                </select><p class="validationmsg"></p></td>
                          </tr>              
                          <tr><td><label>Assigned To</label><span class="mandatory">*</span></td>
                            <logic:notEmpty name="objsalesList" >
                            <td><select name="assignedTo">
                                    <logic:iterate id="objSale" name="objsalesList" type="com.witribe.inventory.vo.DistributeInventoryVO" scope="request">
                                        <c:set var="assignedto" value="${objSale.assignedTo}"/>
                                        <c:if test="${assignedto == 4}">
                                            <option value="${objSale.salesId}">${objSale.salesId}-CSE-${objSale.fullname}</option> 
                                        </c:if>
                                        <c:if test="${assignedto == 5}">
                                            <option value="${objSale.salesId}">${objSale.salesId}-BO-Dealer</option> 
                                        </c:if>
                                        <c:if test="${assignedto == 6}">
                                            <option value="${objSale.salesId}">${objSale.salesId}-NBO-Dealer</option> 
                                        </c:if>                           
                                    </logic:iterate>
                                    
                        </select></td>
                        </logic:notEmpty>
                        <logic:empty name="objsalesList">
                                
                                     <td colspan="4"><b>There are no Sales Persons assigned to this shop !</b></td>
                               
                                 </logic:empty>
                                 </tr>
                                
                                     <logic:notEmpty name="objsalesList" >
                                         <TR>
                                             <TD>&nbsp;</TD>
                                             <TD align=left><INPUT class=submit-btn id="button"
                                                                       onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                                       onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                                       src="images/btn-submit-thickbox.gif" 
                                                               name=input> </TD></TR>
                                     </logic:notEmpty>
                                 </logic:notEmpty>
                                 <logic:empty name="objShopList">
                                     <td colspan="4"><b>There are no shops to this TL !</b></td>
                                 </logic:empty>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body>