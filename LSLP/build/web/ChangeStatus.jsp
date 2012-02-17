<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.constants.WitribeConstants,com.witribe.inventory.bo.WitribeInventoryBO,java.util.ArrayList,java.util.List,com.witribe.inventory.vo.DistributeInventoryVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
   
            function ChangeStatus(){
                if(document.forms[1].inventoryId.value !=null && document.forms[1].inventoryId.value != "") {
               
                    document.forms[1].action="./ChangeStatus.do";
                    document.forms[1].submit();
                    
                }
            }
            function ChangeInvStatus(){
                var selectedvalue =document.forms[1].changeStatus.options[document.forms[1].changeStatus.options.selectedIndex].value;
                if(selectedvalue == 2){
                document.getElementById('assignedto').style.display = "";
                document.getElementById('assignedto').disabled = false;
                } else {
                document.getElementById('assignedto').style.display = "none";
                document.getElementById('assignedto').disabled = true;
                }
            }
            function changeStatusConform(){
                if(document.getElementById('submitenable') && document.forms[1].inventoryId.value !=null && document.forms[1].inventoryId.value != "") {
                var selval =document.forms[1].changeStatus.options[document.forms[1].changeStatus.options.selectedIndex].value;
                document.forms[1].action="./SubmitChangeStatus.do";
                if(selval == 2){
                 var test = confirm("Are You Sure to move this inventory to another Sales Person!");
                 if(test)
                     return true;
                     else
                     return false;
                } else {
                    var test = confirm("Are You Sure to Change the Status!");
                    if(test)
                     return true;
                     else
                     return false;
                }
                }
               
            }
</script>

<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>

    <div id="inner-content">
        <div class="shadow-box">
            <div class="shadow-box-inner">
               <form  method="Post" onsubmit="return changeStatusConform();">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                         <logic:notEmpty name="objShopList" >
                         <tr><td><label>Shop Id</label><span class="mandatory">*</span></td>
                            <td><select name="shopId" onchange="ChangeStatus()">
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
                        </select></td></tr>
                        <tr><td><label>Enter Inventory Id</label><span class="mandatory">*</span></td>
                        <td><input type="text" name="inventoryId" class="txtxx" onblur="ChangeStatus()" value="${invId}"/></td></tr>
                        <logic:empty name="objShopList">
                                
                                     <td colspan="4"><b>There are no shops to this TL !</b></td>
                               
                                 </logic:empty>
                                            
                            <logic:notEmpty name="objInventoryList" >
                                                                               
                                            <logic:iterate id="objinvlist" name="objInventoryList" type="com.witribe.inventory.vo.DistributeInventoryVO" scope="request">
                                            
                                            <c:set var="invmake" value="${objinvlist.make}"/> 
                                            <c:set var="invmodel" value="${objinvlist.model}"/>
                                            <c:set var="invsalesid" value="${objinvlist.salesId}"/>
                                            <c:set var="changeStatus" value="${objinvlist.changeStatus}"/>  
                                            <input type="hidden" name="oldStatus" value="${objinvlist.changeStatus}"/>
                                            
                                           <!-- COMMENTED ON 3RD NOV FOR REPEATED ID ISSUE-->
                                            <%--<tr><td>Inventory Id</td>
                                            <td>${objinvlist.inventoryId}</td></tr>--%>
                                            <tr><td>Make</td>
                                                <c:choose>
                                                <c:when test="${invmake != null}">
                                                    <td>${objinvlist.make}</td></tr>
                                                </c:when>
                                                <c:otherwise>
                                                   <td>Not Available</td></tr>
                                                </c:otherwise>
                                                </c:choose>
                                                
                                                <tr><td>Model</td>
                                                <c:choose>
                                                <c:when test="${invmodel != null}">
                                                    <td>${objinvlist.model}</td></tr>
                                                </c:when>
                                                <c:otherwise>
                                                   <td>Not Available</td></tr>
                                                </c:otherwise>
                                                </c:choose>
                                                <tr><td>Current Status</td>
                                                <td>
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
                                               </td></tr>
                                                <tr><td>Current Sales Person Id</td>
                                                 <c:choose>
                                                <c:when test="${invsalesid != null}">
                                                    <td>${objinvlist.salesId}</td></tr>
                                                </c:when>
                                                <c:otherwise>
                                                   <td>Not Available</td></tr>
                                                </c:otherwise>
                                                </c:choose>
                                                <input type="hidden" name="poidArray" value="${objinvlist.poidId}"/>
                                                <input type="hidden" name="inventorytype" value="${objinvlist.inventorytype}"/>
                                            </logic:iterate>
                             
                        </logic:notEmpty>
                        <logic:empty name="objInventoryList">
                               <c:if test="${invId != null}">
                                     <td colspan="4"><b>Inventory not found !</b></td>
                               </c:if>
                                 </logic:empty>
                         
                                 <tr><td><label>Change Status</label><span class="mandatory">*</span></td>
                            
                            <td><select id="statuschange" name="changeStatus" onchange="ChangeInvStatus()">
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_AT_SHOP}">
                                                <option value=${WitribeConstants.INV_STATUS_AT_SHOP} selected>Available At Shop</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${WitribeConstants.INV_STATUS_AT_SHOP}>Available At Shop</option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_NEW}">
                                                <option value=${WitribeConstants.INV_STATUS_NEW} selected>New</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${WitribeConstants.INV_STATUS_NEW}>New</option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_RETURN_ERP}">
                                                <option value=${WitribeConstants.INV_STATUS_RETURN_ERP} selected>Return To ERP</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${WitribeConstants.INV_STATUS_RETURN_ERP}>Return To ERP</option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_REFURBISHED}">
                                                <option value=${WitribeConstants.INV_STATUS_REFURBISHED} selected>Refurbished</option>
                                            </c:when>
                                            <c:otherwise>
                                               <option value=${WitribeConstants.INV_STATUS_REFURBISHED}>Refurbished</option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_DAMAGED}">
                                                <option value=${WitribeConstants.INV_STATUS_DAMAGED} selected>Damaged</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${WitribeConstants.INV_STATUS_DAMAGED}>Damaged</option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_LOST}">
                                                <option value=${WitribeConstants.INV_STATUS_LOST} selected>Lost</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${WitribeConstants.INV_STATUS_LOST}>Lost</option>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}">
                                                <option value=${WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP} selected>Damaged From ERP</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value=${WitribeConstants.INVSTATUS_RECVDAMAGEDFROMERP}>Damaged From ERP</option>
                                            </c:otherwise>
                                        </c:choose>
                         </select></td></tr>
                         <c:choose>
                                <c:when test="${changeStatus == WitribeConstants.INV_STATUS_RETURN_ERP || changeStatus == WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER|| changeStatus == WitribeConstants.INV_STATUS_ASSIGNED}">
                                    <script language="javascript" type="text/javascript">
                                        
                                    document.getElementById('statuschange').disabled = true;
                                   
                                    </script>
                                </c:when>
                            <c:otherwise>
                                <script language="javascript" type="text/javascript">
                                document.getElementById('statuschange').disabled = false;
                                </script>                                
                            </c:otherwise>
                            </c:choose>
                            
                            <tr id="assignedto"><td><label>Assigned To</label><span class="mandatory">*</span></td>
                         <logic:notEmpty name="objsalesList" >
                            <td><select name="assignedTo">
                                  
                                    <logic:iterate id="objSale" name="objsalesList" type="com.witribe.inventory.vo.DistributeInventoryVO" scope="request">
                                        <c:set var="assignedto" value="${objSale.assignedTo}"/>
                                        <c:choose>
                                            <c:when test="${objSale.salesId == invsalesid}">
                                                <c:if test="${assignedto == WitribeConstants.TYPE_CSE}">
                                                    <option value="${objSale.salesId}" selected>${objSale.salesId}-CSE-${objSale.fullname}</option> 
                                                </c:if>
                                                <c:if test="${assignedto == WitribeConstants.TYPE_BO}">
                                                    <option value="${objSale.salesId}" selected>${objSale.salesId}-BO-Dealer</option> 
                                                </c:if>
                                                <c:if test="${assignedto == WitribeConstants.TYPE_NBO}">
                                                    <option value="${objSale.salesId}" selected>${objSale.salesId}-NBO-Dealer</option> 
                                                </c:if>
                                            </c:when>
                                           <c:otherwise>
                                                <c:if test="${assignedto == WitribeConstants.TYPE_CSE}">
                                                    <option value="${objSale.salesId}">${objSale.salesId}-CSE-${objSale.fullname}</option> 
                                                </c:if>
                                                <c:if test="${assignedto == WitribeConstants.TYPE_BO}">
                                                    <option value="${objSale.salesId}">${objSale.salesId}-BO-Dealer</option> 
                                                </c:if>
                                                <c:if test="${assignedto == WitribeConstants.TYPE_NBO}">
                                                    <option value="${objSale.salesId}">${objSale.salesId}-NBO-Dealer</option> 
                                                </c:if>
                                            </c:otherwise>
                                       </c:choose>                                     
                                    </logic:iterate>
                                    
                        </select></td>
                        </logic:notEmpty>
                        <logic:empty name="objsalesList">
                                
                                     <td colspan="4"><b>There are no Sales Persons assigned to this shop !</b></td>
                               
                                 </logic:empty>
                                 </tr>
                                 <c:choose>
                                            <c:when test="${changeStatus == WitribeConstants.INV_STATUS_NEW}">
                                                <script language="javascript" type="text/javascript">
                                                    document.getElementById('assignedto').style.display = "";
                                                    document.getElementById('assignedto').disabled = false;
                                                </script>
                                            </c:when>
                                            <c:otherwise>
                                                <script language="javascript" type="text/javascript">
                                                    document.getElementById('assignedto').style.display = "none";
                                                    document.getElementById('assignedto').disabled = true;
                                                </script>
                                            </c:otherwise>
                                        </c:choose>
                                 
                                 <logic:notEmpty name="objInventoryList" >
                                    
                                     <c:if test="${changeStatus != WitribeConstants.INV_STATUS_RETURN_ERP}">
                                         <c:if test="${changeStatus != WitribeConstants.INV_STATUS_RETURN_ERP_TRANSFER}">
                                             <c:if test="${changeStatus != WitribeConstants.INV_STATUS_ASSIGNED}">
                                             <TR>
                                                 <TD>&nbsp;</TD>
                                                 <TD align=left><INPUT id="submitenable" class=submit-btn 
                                                                           onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                                           onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                                           src="images/btn-submit-thickbox.gif" 
                                                                   name=input> </TD></TR>
                                            </c:if>                          
                                         </c:if>  
                                     </c:if>
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
