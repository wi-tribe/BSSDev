<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
//include_once("../../vims_config.php");
include_once("../util_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");
?>

<table width="100%">
      <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'checkprovisioning')) { ?>
       <tr>
		<td class="textboxGray"><a id='check_provisioning' name='check_provisioning' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/provisioning.php','FormDiv' );">Check Provisioning</a></td>
       </tr>
    <? } ?>
     <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'customerSearch')) { ?>
       <tr>
		<td class="textboxGray"><a id='check_provisioning' name='check_provisioning' class="options" href="Pages/customer_search.php" onclick="javascript:animatedAjaxCall( 'Pages/customer_search.php','FormDiv' );">Customer Search</a></td>
       </tr>
    <? } ?>
    <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'salesfeedback')) { ?>
       <tr>
		<td class="textboxGray"><a id='sales_shop_feedback' name='sales_shop_feedback' class="options" href="Pages/sales_feedback.php" onclick="javascript:animatedAjaxCall( 'Pages/sales_feedback.php','FormDiv' );">Sales Shop Feedback</a></td>
       </tr>
    <? } ?>
     <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'CNIC_verfication')) { ?>
       <tr>
		<td class="textboxGray"><a id='cnic_verfiy' name='cnic_verfiy' class="options" href="Pages/CNIC_verfication.php" onclick="javascript:animatedAjaxCall( 'Pages/CNIC_verfication.php','FormDiv' );">CNIC Verfication</a></td>
       </tr>
        <? } ?>
         <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'Email_verification')) { ?>
       <tr>
		<td class="textboxGray"><a id='email_verfiy' name='email_verfiy' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/verify_email.php','FormDiv' );">Email Verfication</a></td>
       </tr>
         <? } ?>
           <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'balancecheck')) { ?>
       <tr>
		<td class="textboxGray"><a id='cust_balance_check' name='cust_balance_check' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/brm_bal_check.php','FormDiv' );">BRM Customer Balance</a></td>
       </tr>
        <? } ?>
         <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'Phone_verification')) { ?>
       <tr>
		<td class="textboxGray"><a id='cust_phone_verify' name='cust_phone_verify' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/CustomerVerificationByNumber.php','FormDiv' );">Phone Number Verification</a></td>
	</tr>
         <? } ?>
       <? //if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'cpeStatus')) { ?>
        <tr>
		<td class="textboxGray"><a id='cust_phone_verify' name='provision' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/prov.php','FormDiv' );">CPE Status</a></td>
	</tr>
            <? //} ?>
    <tr>
        <td>
            &nbsp;
        </td>
    </tr>    
	<tr >
		<td class="textboxGray"><a id='my_sales' name='my_sales' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/mySales.php','FormDiv' );">My Sales</a></td>
	</tr>
        <? if($_SESSION['user_role_id']=='62'){ ?>
        <tr >
		<td class="textboxGray"><a id='cust_payables_se' name='cust_payables_se' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/my_nonpaying_customers.php','FormDiv' );">Customer Payables</a></td>
	</tr>
         <?  } ?>

         <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'ShowAllocatedCalls')) { ?>
        <tr >
                <td class="textboxGray"><a id='cust_payables' name='cust_payables' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/my_nonpaying_customers_CC.php','FormDiv' );">Customer Payables</a></td>
        </tr>
        <? } ?>
          <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'AllocateCalls')) { ?>
        <tr >
                <td class="textboxGray"><a id='allocateCalls' name='allocateCalls' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/allocateCalls.php','FormDiv' );">Allocate Calls to CCE's</a></td>
        </tr>
        <? } ?>
        <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'], 'retentionCallSummary')) { ?>
        <tr >
                <td class="textboxGray"><a id='retentionCallSummary' name='retentionCallSummary' class="options" href="Pages/retentionCallsSummary.php" onclick="javascript:animatedAjaxCall( 'Pages/retentionCallsSummary.php','FormDiv' );">Retention Call Summary</a></td>
        </tr>
        <? } ?>
          <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'CustomerPayment') && $_SESSION['user_role_id']!='61' && $_SESSION['user_role_id']!='62') { ?>
        <!--<tr>
                <td class="textboxGray"><a id='retentionCallSummary' name='retentionCallSummary' class="options" href="Pages/Customer_payment.php" onclick="javascript:animatedAjaxCall( 'Pages/Customer_payment.php','FormDiv' );">Customer Payment</a></td>
        </tr>!-->
        <? } ?>
        <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'SalesReturn')) { ?>
        <tr>
                <td class="textboxGray"><a id='sales_report_link' name='sales_report_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/SalesReturnReq_Customers_Dunning.php','FormDiv' );">Sales Return Requests</a></td>
        </tr>
          <? } ?>
       
		 <!--
        <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'collectionreport')) {  ?>
        <tr>
                <td class="textboxGray"><a id='sales_report_link' name='sales_report_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Reports/salesReport.php','FormDiv' );">Sales Report</a></td>
        </tr>
		
        <? } ?>
        <? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptreport')) { ?>
        <tr >
                <td class="textboxGray"><a id='recieptReport_link' name='recieptReport_link' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/Reports/receiptReport.php','FormDiv' );">Receipt Report</a></td>
        </tr>
        <? } ?>
       !-->
         <? if($GLOBALS['_GACL']->checkpermission($_SESSION['username'],'retentiondataUpload')) { ?>
        <tr >
                <td class="textboxGray"><a id='retentiondataUpload' name='retentiondataUpload' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/load.php','FormDiv' );">Retention Data Management</a></td>
        </tr>
        <? } ?>
       
        <tr >
		<td class="textboxGray"><a id='my_sales' name='my_sales' class="options" href="#" onclick="javascript:animatedAjaxCall( 'Pages/changePassword.php','FormDiv' );">Change Password</a></td>
	</tr>
	<? if($GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'DeviceTrackingReport')) { ?>
		 <tr >
		<td class="textboxGray"><a id='my_sales' name='my_sales' class="options" href="Pages/Inventory Status.php" onclick="javascript:animatedAjaxCall( 'Pages/Inventory Status.php','FormDiv' );">Device Tracking System</a></td>
	</tr>
	<? } ?>
	   

</table>