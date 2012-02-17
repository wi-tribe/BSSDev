<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.inventory.vo.RaiseRequestVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
        function ChangeInvType(invtype){
           var subtype = "";
           callInventory(invtype,subtype);
         }
        function ChangeInvSubType(invsubtype){
            var invtype = document.getElementById('itype').options[document.getElementById('itype').selectedIndex].value;
            callSpecificInventory(invtype,invsubtype);
       }
       function ChangeInvMake(make){
           var invtype = document.getElementById('itype').options[document.getElementById('itype').selectedIndex].value;
           var invsubtype = document.getElementById('subtypecpe').options[document.getElementById('subtypecpe').selectedIndex].value
           callSpecificMakeInventory(invtype,invsubtype,make);
       }
        function changeRequest(){
             document.forms[1].action="./RaiseRequest.do";
                document.forms[1].submit();
        }
            
</script>
<script type="text/javascript" charset="utf-8">
			$(function()
				{
                                        Date.format = 'dd/mm/yyyy';
                                        var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1;
                                        var dt=D.getDate();
                                        var curDate=dt+"/"+M+"/"+Y;
					$('.date-pick').datePicker({startDate:curDate});
			});
</script>
<body>
    <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
    <div id="inner-content">
        <div class="shadow-box">
            <div class="shadow-box-inner">
                <form action="./SubmitRequest.do" method="Post" onSubmit="return ValidateRaiseReqForm(this);">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr><td><label>Request Type</label><span class="mandatory">*</span></td>
                            <td><select name="requesttype" onchange="changeRequest();">
                                    
                                    <option value="1">Request</option>
                                    <option value="2">Return</option>
                                    <option value="3">Transfer</option>
                        </select></td></tr>
                        <%--<tr><td><label>Inventory Type</label><span class="mandatory">*</span></td>
                                <td><select name="inventorytype"  id="itype" onchange="ChangeType()">
                                        <option value="CPE">CPE</option>
                                        <option value="ROUTER">ROUTER</option>
                                        <option value="OTHER">OTHER</option>
                            </select></td></tr>
                                <tr><td><label>Inventory Sub Type</label><span class="mandatory">*</span></td>
                                    <td id="typecpe"><select name="subtypecpe">
                                            <option value="Indoor">Indoor</option>
                                            <option value="Outdoor">Outdoor</option>
                                            <option value="Nomadic">Nomadic</option>
                                            <option value="USB Dongle">USB Dongle</option>
                                    </select></td>               
                                    <td id="typerouter"><select name="subtyperouter">
                                        <option value="wi-fi AP">wi-fi AP</option>
                                        <option value="Wi-Fi Router">Wi-Fi Router</option>
                            </select></td></tr>
                            <tr><td><label>Make</label><span class="mandatory">*</span></td>
                                <td><select name="make">
                                        <option value="Motorola">Motorola</option>
                                        <option value="Comp2">Comp2</option>
                                        <option value="Comp3">Comp3</option>
                            </select></td></tr>
                                <tr><td><label>Model</label><span class="mandatory">*</span></td>
                                    <td id="mcpe"><select name="modelcpe">
                                            <option value="CPEi35775">CPEi35775</option>
                                            <option value="CPEi 750 Desktop">CPEi 750 Desktop</option>
                                            <option value="CPEi 775. Desktop wi-fi">CPEi 775. Desktop wi-fi</option>
                                            <option value="USB w 100. USB Dongle">USB w 100. USB Dongle</option>
                                            <option value="CPEo 400 (Outdoor)">CPEo 400 (Outdoor)</option>
                                            <option value="CPEo35450">CPEo35450</option>
                                            <option value="WU310MTG07">WU310MTG07</option>
                                    </select></td>
                                   <td id="mrouter"><select name="modelrouter">
                                        <option value="802.11b">802.11b</option>
                                        <option value="802.11g">802.11g</option>
                                        <option value="802.11n">802.11n</option>
                            </select></td></tr>--%>
                        <%--<tr><td><label>Inventory Type</label><span class="mandatory">*</span></td>
                                <td>
                                  <logic:notEmpty name="objInvTypeList" >
                                <select name="inventorytype"  id="itype" onchange="ChangeType()">
                                        <logic:iterate id="objInv" name="objInvTypeList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                        
                                        <option value="${objInv.inventorytype}">${objInv.inventorytype}</option> 
                                        
                                    </logic:iterate>
                            </select></td></logic:notEmpty>  
                        <logic:empty name="objInvTypeList">
                            
                            <td colspan="4"><b>There are no Inventory Types to this TL !</b></td>
                            
                    </logic:empty>     
                            </tr>--%>
                        <tr><td><label>Inventory Type</label><span class="mandatory">*</span></td>
                            <td><select name="inventorytype"  id="itype" onchange="ChangeInvType(this.options[this.selectedIndex].value)">
                                    <logic:notEmpty name="objInvTypeList" >
                                        <option></option>
                                        <logic:iterate id="objInv" name="objInvTypeList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                            <option value="${objInv.inventorytype}">${objInv.inventorytype}</option>
                                            
                                    </logic:iterate></logic:notEmpty>
                        </select><p class="validationmsg"></p></td></tr>
                        <tr><td><label>Inventory Sub Type</label><span class="mandatory">*</span></td>
                            <td id="typecpe"><select name="subtype" id="subtypecpe" onchange="ChangeInvSubType(this.options[this.selectedIndex].value);">
                                    
                                    <!--<option value="Indoor">Indoor</option>
                                        <option value="Nomadic">Nomadic</option>
                                        <option value="Outdoor">Outdoor</option>
                                        <option value="USB Dongle">USB Dongle</option>-->
                            </select><p class="validationmsg"></p></td>
                            <!-- <td id="typerouter"><select name="subtypenew" id="subtyperouter" onchange="ChangeInvSubType(this.options[this.selectedIndex].value);">
                                       <option value="wi-fi AP">wi-fi AP</option>
                                        <option value="Wi-Fi Router">Wi-Fi Router</option>
                            </select></td> -->
                        </tr>
                        
                        <tr><td><label>Make</label><span class="mandatory">*</span></td>
                            
                            <%--  <logic:notEmpty name="objInvTypeList" >
                                <td id="make2"><select name="make" id = "make1">
                                        <option></option>
                                        <logic:iterate id="objInv" name="objInvTypeList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                        
                                        <option value="${objInv.make}">${objInv.make}</option> 
                                        
                                    </logic:iterate>
                            </select><p class="validationmsg"></p></td></logic:notEmpty> --%> 
                       
                            
                            <!--<td colspan="4"><b>There are no  Make to this Inventory !</b></td>-->
                            <td id="invmake1"><select name="make" id="invmake" onchange="ChangeInvMake(this.options[this.selectedIndex].value)">
                                    
                            </select><p class="validationmsg"></p></td>
                            
                            
                        </tr>
                        <tr><td><label>Model</label><span class="mandatory">*</span></td>
                            
                            <%--<logic:notEmpty name="objInvTypeList" >
                                    <td id="model2"> <select name="model" id="model1">
                                        <option></option>
                                        <logic:iterate id="objInv" name="objInvTypeList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                            
                                            <option value="${objInv.model}">${objInv.model}</option> 
                                            
                                        </logic:iterate>
                                </select><p class="validationmsg"></p></td></logic:notEmpty>  --%>
                                
                                    
                            <!--<td colspan="4"><b>There are no  Model to this Inventory !</b></td>-->
                            <td id="invmodel1"><select name="model" id="invmodel">
                                    
                            </select><p class="validationmsg"></p></td>
                            
                            
                        </tr>
                        <tr><td><label>Required Number of Devices</label><span class="mandatory">*</span></td>
                        <td><input type="text" name="numberofdevices" value="" class="txtxx" maxlength = "5" /><p class="validationmsg"></p></td></tr>
                        <tr><td><label>For Shop</label><span class="mandatory">*</span></td>
                            <logic:notEmpty name="objShopList" >
                                <td><select name="shopId">
                                        <logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                            
                                            <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option> 
                                            
                                        </logic:iterate>
                            </select></td></logic:notEmpty>  
                            <logic:empty name="objShopList">
                                
                                <td colspan="4"><b>There are no shops to this TL !</b></td>
                                
                            </logic:empty>
                        </tr>
                        
                        <tr>
                            <td><label for="date1">Required By Date</label><span class="mandatory">*</span></td>
                            <td><input type="text" class="txtxx date-pick dp-applied" name="reqbydate" id="date1"  readonly/>&nbsp;&nbsp;<p class="validationmsg"></p></td>
                            <td>&nbsp;</td>
                        </tr>
                        <logic:notEmpty name="objShopList">
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
</body>


