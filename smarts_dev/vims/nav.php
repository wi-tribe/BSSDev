<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
?>
<table width="100%" >
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addvoucher')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Voucher/voucherSeriesAdd.php','FormDiv' );">Add Vouchers</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'voucherstats')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Voucher/voucherStats.php','FormDiv' );">Voucher Stats</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'transitvoucherlist')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Voucher/voucherTransitList.php','FormDiv' );">List Transit Vouchers</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'vouchersalestats')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Voucher/voucherSales.php','FormDiv' );">Voucher Sales</a></td>
    </tr>
        <? } ?>

    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'poslist')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/POS/pos.php','FormDiv' );">Point of Sale</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'blockvoucher')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/POS/blockvoucher.php','FormDiv' );">Block Voucher</a></td>
    </tr>
        <? } ?>

    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addchannel')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Channel/channelAdd.php','FormDiv' );">Add Channel</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listchannel')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Channel/channelList.php','FormDiv' );">List Channels</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'reqinventory')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/requestInventory.php','FormDiv' );">Request Inventory</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processorders')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/processOrderList.php','FormDiv' );">Process Orders</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listorders')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/orderStatusListings.php','FormDiv' );">Cancel Order</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'recieveinventory')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/recieveOrderList.php','FormDiv' );">Recieve Orders</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listorders')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/orderStatusListings.php','FormDiv' );">List Orders</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'returninventory')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/returnInventory.php','FormDiv' );">Return Inventory</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'assigninventory')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Inventory/assignInventory.php','FormDiv' );">Assign Inventory</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'adduser')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/User/UserAdd.php','FormDiv' );">Add User</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listuser')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/User/userList.php','FormDiv' );">List Users</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addrole')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/User/userRole.php','FormDiv' );">User Roles</a></td>
    </tr>
        <? } ?>

    <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'collectionreport')) { ?>
    <tr>
        <td class="textboxGray"><a id='sales_report_link' name='sales_report_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Reports/salesReport.php','FormDiv' );">Sales Report</a></td>
    </tr>
    <? } ?>
    <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'SAVDSCREPORT')) { ?>
    <tr>
        <td class="textboxGray"><a id='sales_report_link' name='sales_report_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Reports/SAVDSCReport.php','FormDiv' );">Sales Report</a></td>
    </tr>
    <? } ?>

    <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptreport')) { ?>
    <tr >
        <td class="textboxGray"><a id='recieptReport_link' name='recieptReport_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Reports/receiptReport.php','FormDiv' );">Receipt Report</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptreport')) { ?>
    <tr >
        <td class="textboxGray"><a id='recieptSummaryReport_link' name='recieptSummaryReport_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Reports/receiptSummary.php','FormDiv' );">Receipt Summary Report</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionMain')) { ?>
    <tr >
        <td class="textboxGray"><a id='commission_add_link' name='commission_add_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Commissioning/commissionMain.php','FormDiv' );">Commission Rules</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountMain')) { ?>
    <tr>
        <td class="textboxGray"><a id='discount_add_link' name='discount_add_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Discounts/discountMain.php','FormDiv' );">Discount Rules</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'repoverride')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/POS/ticketedVouchersList.php','FormDiv' );">Voucher Replacement Queries</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailer')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='AddTL' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/User/CreateRetailer.php','FormDiv' );">Add Retailer</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listretailers')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/User/listRetailers.php','FormDiv' );">List Retailers</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processretailerorder')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='AddTL' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Order/ProcessRetailerOrder.php','FormDiv' );">Process Retailer Order</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'changepassword')) { ?>
    <tr >
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/User/changePassword.php','FormDiv' );">Change Password</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'replacementStatus')) { ?>
    <tr>
        <td class="textboxGray"><a id='replacement_status' name='replacement_status' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/POS/requestedrepStatus.php','FormDiv' );">Channel Replacement Status</a></td>
    </tr>
        <? } ?>

    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rentalMain')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Rental/RentalMain.php','FormDiv' );">List Rental Rules</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rentalpayables')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Rental/Rental_Payables.php','FormDiv' );">Rental Payables</a></td>
    </tr>
        <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'viewLostInventory')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Voucher/ViewLostVouchers.php','FormDiv' );">View Lost Inventory</a></td>
    </tr>
        <? } ?>
    <? if ($GLOBALS['_GACL']->checkPermission($_SESSION['username'], 'cce_track_voucher')) { ?>
    <tr>
        <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/POS/getVoucherStatus.php','FormDiv' );">Check Voucher Current Status</a></td>
    </tr>
        <? } ?>
    <!--
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'crownscript')) { ?>
     <tr>
            <td class="textboxGray"><a id='cust_payable' name='cust_payable' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Rental/CrownScript.php','FormDiv' );">Script</a></td>
						</tr>
        <? } ?>
						!-->
	<? if($GLOBALS['_GACL']->checkpermission($_SESSION['username'],'retentiondataUpload')) { ?>
        <tr >
                <td class="textboxGray"><a id='retentiondataUpload' name='retentiondataUpload' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/load.php','FormDiv' );">Retention Data Management</a></td>
        </tr>
        <? } ?>

</table>