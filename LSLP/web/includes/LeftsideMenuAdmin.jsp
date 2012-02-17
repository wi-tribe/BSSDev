<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

   <div id="sidebar-left">
   <!--<div style="{
		width:255px;
		float:left;
		padding:122px 0 0px 0px;
	}">-->
    <UL class="mktree" id="tree1">
        
        <li style="{ font-size: 10pt; }" id="lm">Lead Management
            <ul>
                <li><a href="./CreateLead.do">Create Lead</a></li>
                
                <c:if test="${role == '3' }">
                     <li><a href="./DealerAccountCreationAction.do?salesweb=DEALER&Dealerid=${SALES_ID}">Create Account</a></li>
                   <!--  <li><a href="./AccountSearch.do?parameter=searchAccount">Search Account</a></li> -->
                </c:if>
                <li><a href='./Prospect.do'>View Prospects</a></li>
                <li><a href='./LeadAccount.do'>View Leads</a></li>
                <li><a href='./Search.do'>Search Leads</a></li>
            </ul>
        </li>
               
        <li style="{ font-size: 10pt; }">Sales Force Management
            <ul>
                <li style="{ font-size: 10pt; }">Sales Personnel Mgmt
                    <ul>
                        <li style="{ font-size: 10pt; }" id="sp">Sales Personnel
                            <ul>
                                <li style="{ font-size: 10pt; }"><a href='./CreateSale.do'>Create</a></li>
                                <li style="{ font-size: 10pt; }"><a href='./ViewSales.do'>View/Modify/Delete</a></li>
                                <li style="{ font-size: 10pt; }"><a href='./InactiveCse.do'>Change&nbsp;Cse&nbsp;Status&nbsp;</a></li>
                                <c:if test="${role == '0' || role == '1' || role == '2'|| role == '3'}">
                                    <li style="{ font-size: 10pt; }"><a href='./ShowRsmToCity.do'>Add&nbsp;Location&nbsp;</a></li>
                                    <li style="{ font-size: 10pt; }"><a href='./AddZoneSubzone.do'>Add&nbsp;Zone&nbsp;and&nbsp;Subzone</a></li>
                                    <li style="{ font-size: 10pt; }"><a href='./ZoneSubzoneDelete.do'>Delete&nbsp;Zone&nbsp;and&nbsp;Subzone</a></li>
                                </c:if>
                                <li style="{ font-size: 10pt; }"><a href='./ModifyChannel.do'>Modify&nbsp;ChannelType</a></li>
                                 <c:if test="${role == '0'|| role == '1' || role == '2'|| role == '3'}">
                                    <!-- <li style="{ font-size: 10pt; }"><a href='./ViewLocation.do'>View/Delete &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;City/Zone</a></li>-->
                                    <li style="{ font-size: 10pt; }"><a href='./ViewLocation.do'>Delete&nbsp;Location</a></li>
                                </c:if>
                                
                            </ul>
                    </li></ul>
                    <ul>
                        <li style="{ font-size: 10pt; }" id="saleH">Sales Hierarchy
                            
                            <ul>
                                <li style="{ font-size: 10pt; }"><a href='./SalesHirarchy.do'>Create</a></li>
                                <li style="{ font-size: 10pt; }"><a href='./ViewSalesHierarchy.do'>View/Delete</a></li>
                                
                        </ul></li>
                        
                    </ul>
                </li>
                
                <li style="{ font-size: 10pt; }">Shop Management 
                <ul>
                      <li style="{ font-size: 10pt; }" id="shops">Shops
                            <ul>
                                <li style="{ font-size: 10pt; }"><a href='./CreateShop.do'>Create</a></li>
                                <li style="{ font-size: 10pt; }"><a href='./ViewShops.do'>View/Modify/Delete</a></li>
                                 <li style="{ font-size: 10pt; }"><a href='./ChangeShopStatus.do'>Change&nbsp;Shop&nbsp;Status</a></li>
                               <!-- <li style="{ font-size: 10pt; }"><a href='./ShopZoneAction.do'>Add Zone to Shop</a></li>-->
                        </ul> </li>
                         <!--<c:if test="${role == '3'}">
                            <li style="{ font-size: 10pt; }" id="shopH">Shop Hierarchy
                             <ul>
                                <li style="{ font-size: 10pt; }"><a href='./KiosksToShop.do'>Assign Kiosks-Shops</a></li>
                                <li style="{ font-size: 10pt; }"><a href='./ViewShopsHierarchy.do'>View/Modify/Delete</a></li>
                        </ul></li>
                         </c:if>-->
                </ul></li>
                <li style="{ font-size: 10pt; }" id="atcs">Associate TL to Shop
                    <ul>
                        <li style="{ font-size: 10pt; }"><a href='./ShopSaleMap.do'>Assign</a></li>
                        <li style="{ font-size: 10pt; }"><a href='./ViewShopToSale.do'>View/Modify/Delete</a></li>
                </ul></li> 
        </ul></li>
        <li style="{ font-size: 10pt; }" id="som">Sales Order Management 
            <ul>
                        <li style="{ font-size: 10pt; }"><a href='./ViewLead.do'>View/Assign un-assigned Jobs</a></li>
                        <c:if test="${role == '3'}">
                            <li style="{ font-size: 10pt; }"><a href='./ReAssignJob.do'>Re-Assign Jobs</a></li>
                        </c:if>
             </ul></li>
      
             <c:if test="${role == '3'}">
                 <li style="{ font-size: 10pt; }" id="sim">Sales&nbsp;Inventory&nbsp;Management
                     <ul>
                         <li style="{ font-size: 10pt; }"><a href='./RaiseRequest.do'>Raise Request</a></li>
                         <li style="{ font-size: 10pt; }"><a href='./VoucherRequest.do'>Voucher Request</a></li>
                         <li style="{ font-size: 10pt; }"><a href='./ViewRequestStatus.do'>View Request Status</a></li>
                           <li style="{ font-size: 10pt; }"><a href='./InventoryLevelSetting.do'>Inventory&nbsp;Level&nbsp;Setting</a></li>
                         
                         <li style="{ font-size: 10pt; }"><a href='./ChangeStatus.do'>Change Status</a></li>
                         <li style="{ font-size: 10pt; }"><a href='./DistributeInventory.do'>Distribute Inventory</a></li>
                         <%-- <li style="{ font-size: 10pt; }"><a href='./CSETransfer.do'>CSE Transfer</a></li> --%>
                         <li style="{ font-size: 10pt; }"><a href='./InvList.do'>Inventory List</a></li>
                         <li style="{ font-size: 10pt; }"><a href='./SearchInv.do'>Search Inventory</a></li>
                         <li style="{ font-size: 10pt; }"><a href='./InvMailSetting.do'>Inventory Mail Setting</a></li>
                 </ul></li> 
             </c:if>
             <c:if test="${role != '0' }">
                 <li style="{ font-size: 10pt; }" id="prof">Profile
                     <ul>
                         <li style="{ font-size: 10pt; }" id="ind"><a href="./ViewSales.do?viewProfile=true" >View/Modify Profile</a></li>
                         <li style="{ font-size: 10pt; }" id="salescom"><a href="./MyCommissionReport.do?salesid=${SALES_ID}">My Commission</a></li> <!---Added by Murali--> 
                    <li><a href="./MyCommissionPayments.do?salesid=${SALES_ID}">My Commission Payments</a></li>  

                 </ul></li>
         </c:if>
         <c:if test="${role == '0' }">
             <li style="{ font-size: 10pt; }" id="salescom">Sales Commission
                 <ul>
                     <li style="{ font-size: 10pt; }" id="salescom"><a href="./AddChannel.do" >Add Channels</a></li>
                     <li style="{ font-size: 10pt; }" id="salescom"><a href="./CommissionPlan.do" >Create Plan</a></li>
                     <li style="{ font-size: 10pt; }" id="salescom"><a href="./commissionPlanEdit.do" >View/Edit Commision Plan</a></li><!--added by murali -->
                     <li style="{ font-size: 10pt; }" id="salescom"><a href="./SetCommissionMode.do" >Set Commission Mode</a></li><!--added by murali -->

             </ul></li>
         </c:if>
         <c:if test="${role != '3' }">
             <li style="{ font-size: 10pt; }" id="traninv"><a href="./TransferInventory.do">Transfer Inventory</a></li>   
         </c:if>
         <li style="{ font-size: 10pt; }"><a href="./Logout.do">Logout</a></li>   
        
    </UL>
    
</div>






