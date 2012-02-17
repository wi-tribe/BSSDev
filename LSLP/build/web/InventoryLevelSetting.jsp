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
             var invtype = document.getElementById('itype').options[document.getElementById('itype').selectedIndex].value;
             var subtype = document.getElementById('subtypecpe').options[document.getElementById('subtypecpe').selectedIndex].value;
             var make = document.getElementById('invmake').options[document.getElementById('invmake').selectedIndex].value;
             var model = document.getElementById('invmodel').options[document.getElementById('invmodel').selectedIndex].value;
             var shopid = document.getElementById('ShopId').options[document.getElementById('ShopId').selectedIndex].value;
             callMinMax(invtype,subtype,make,model,shopid);   
            }
           
    </script>
<body>
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    <form action="./SubmitInvLevel.do" method="Post" onSubmit="return ValidateInventoryLevelReqForm(this);">
                        <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                            
                            <tr><td><label>For Shop</label><span class="mandatory">*</span></td>
                               <logic:notEmpty name="objShopList" >
                                <td><select name="shopId" id="ShopId">
                                        <option></option>
                                        <logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        
                                        <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option> 
                                        
                                    </logic:iterate>
                            </select></td></logic:notEmpty>  
                        <logic:empty name="objShopList">
                            
                            <td colspan="4"><b>There are no shops to this TL !</b></td>
                            
                    </logic:empty>
                        </tr>
                      
                            <tr><td><label>Inventory Type</label><span class="mandatory">*</span></td>
                                <td><select name="inventorytype"  id="itype" onchange="ChangeInvType(this.options[this.selectedIndex].value);">
                                        <logic:notEmpty name="objInvTypeList" >
                                            <option></option>
                                            <logic:iterate id="objInv" name="objInvTypeList" type="com.witribe.inventory.vo.RaiseRequestVO" scope="request">
                                                <option value="${objInv.inventorytype}">${objInv.inventorytype}</option>
                                                
                                        </logic:iterate></logic:notEmpty>
                            </select><p class="validationmsg"></p></td></tr>
                            <tr><td><label>Inventory Sub Type</label><span class="mandatory">*</span></td>
                                <td id="typecpe"><select name="subtype" id="subtypecpe" onchange="ChangeInvSubType(this.options[this.selectedIndex].value);">
                                </select><p class="validationmsg"></p></td>
                           </tr>
                           
                            <tr><td><label>Make</label><span class="mandatory">*</span></td>
                         
                            <td id="invmake1"><select name="make" id="invmake" onchange="ChangeInvMake(this.options[this.selectedIndex].value);">
                                    
                            </select><p class="validationmsg"></p></td>
                            
                    
                            </tr>
                            <tr><td><label>Model</label><span class="mandatory">*</span></td>
                               
                            
                                    <td id="invmodel1"><select name="model" id="invmodel">
                                    
                                </select><p class="validationmsg"></p></td>
                                    
                               
                            </tr>
                            <tr><td><label>Min Level</label><span class="mandatory">*</span></td>
                             <td><input type="text" name="minlevel" value="<%=request.getAttribute("minval")%>" class="txtxx" maxlength = "5" id="minl"/><p class="validationmsg"></p></td></tr>
                        
                             <tr><td><label>Max Level</label><span class="mandatory">*</span></td>
                             <td><input type="text" name="maxlevel" value="<%=request.getAttribute("maxval")%>" class="txtxx" maxlength = "5" id="maxl"/><p class="validationmsg"></p></td></tr>

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
 

