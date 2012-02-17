<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
	include_once('../util_config.php');	
	include_once(UTIL_CLASSDIR."/SmartsUtil.php");

	$account_id= $_REQUEST['acct_id'];
	
	$smarts_obj = new SmartsUtil();
	$result = $smarts_obj->getBRMAccountDetails($account_id);
	$result = $result[0];
	$payments = $smarts_obj->getBRMPaymentDetails($account_id);

?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<?=$_HTML_LIBS?>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1100" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">
		<table width="100%" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="<?IMAGES?>/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;"></td>
			</tr>
		</table>
	</td>
  </tr>
  <tr>
    <td><br>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
        <td><p>&nbsp;</p>
              <!-----------------------------THIS PAGE------------------------------------->
			<table width="100%" border="0" cellpading="0" cellspacing="0">
			<tr>
				<td ><p>
					<div id="customer_detail" class="normalFont">
						<!--////////////////////////////////////////////////////////-->
						<table width='100%'>
							<tr><td colspan='3'><img src='<?IMAGES?>/orderform/customer_details.gif' /></td></tr>
							<tr><td width="250" class='orangeText'>Customer ID</td>				<td>&nbsp;</td><td><strong><?=$result['CUSTOMER_ID']?></strong></td></tr>
							<tr><td width="250" class='orangeText'>Name</td>					<td>&nbsp;</td><td><?=$result['FIRST_NAME']." ".$result['LAST_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Identificaiton Number</td>	<td>&nbsp;</td><td><?=$result['IDENTIFICATION_NUMBER']?></td></tr>
							<tr><td width="250" class='orangeText'>Customer Address</td>		<td>&nbsp;</td><td><?=$result['ADDRESS']?></td></tr>
							<tr><td width="250" class='orangeText'>City</td>					<td>&nbsp;</td><td><?=$result['REGION_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Telephone</td>				<td>&nbsp;</td><td><?=$result['TELEPHONE_NUMBER']?></td></tr>
							<tr><td width="250" class='orangeText'>Mobile</td>					<td>&nbsp;</td><td><?=$result['MOBILE_NUMBER']?></td></tr>
							<tr><td width="250" class='orangeText'>Email Address</td>			<td>&nbsp;</td><td><?=$result['EMAIL_ADDRESS']?></td></tr>

						</table>
				<!--////////////////////////////////////-->
					</div></p>
				</td>
				<td><p>
					<div id="customer_account" class="normalFont">
						<table width='100%' cellpadding='0' cellspacing='0'>
							<tr><td colspan='6'><img src='<?IMAGES?>/orderform/account_details.png' /></td></tr><tr> <BR>
							
							<tr><td width="250" class='orangeText'>Status</td>						<td>&nbsp;</td><td><strong><?=$result['CUSTOMER_STATUS']?></strong></td></tr>
							<tr><td width="250" class='orangeText'>Creation Date</td>				<td>&nbsp;</td><td><?=$result['DATE_CREATED']?></td></tr>
							<tr><td width="250" class='orangeText'>Last Status Change Date</td>		<td>&nbsp;</td><td><?=$result['LAST_STATUS_DATE']?></td></tr>
							<tr><td width="250" class='orangeText'>Last Status Change Reason</td>	<td>&nbsp;</td><td><?=$result['LAST_COMMENT']?></td></tr>
							<tr><td width="250" class='orangeText'>Account Type</td>				<td>&nbsp;</td><td><?=$result['BUSINESS_TYPE']?></td></tr>
							<tr><td width="250" class='orangeText'>MAC Address</td>					<td>&nbsp;</td><td><?=$result['CPE_MAC_ADDRESS']?></td></tr>
							<tr><td width="250" class='orangeText'>Device Model</td>				<td>&nbsp;</td><td><?=$result['CPE_MODEL']?></td></tr>
							<tr><td width="250" class='orangeText'>Device Type</td>					<td>&nbsp;</td><td><?=$result['CPE_TYPE']?></td></tr>
							<tr><td width="250" class='orangeText'>Profile ID as per package</td>	<td>&nbsp;</td><td><?=$result['PROFILE_ID']?></td></tr>
							
						</table>
					</div></p>
				</td>
			</tr>
			<!--tr>
				<td colspan='2' ><p>
					<div id="customer_package" class="normalFont">
						<table width='100%'>
							<tr><td colspan='3'><img src='../images/orderform/package_selection.png' /></td></tr>
							<tr><td width="250" class='orangeText'>Package</td>			<td>&nbsp;</td><td><?=$result['PACKAGE_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Pay Type</td>		<td>&nbsp;</td><td><?=$result['PAY_TYPE']?></td></tr>
							<tr><td width="250" class='orangeText'>Parent Account</td>	<td>&nbsp;</td><td><?=$result['PARENT_ACCT']?></td></tr>
							<tr><td width="250" class='orangeText'>Shop ID</td>			<td>&nbsp;</td><td><?=$result['SHOP_ID']?></td></tr>
							<tr><td width="250" class='orangeText'>Sales Rep</td>		<td>&nbsp;</td><td><?=$result['SALES_REP_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Sales Rep ID</td>	<td>&nbsp;</td><td><?=$result['SALES_REP_ID']?></td></tr>
							<tr><td width="250" class='orangeText'>Total Due Now</td>	<td>&nbsp;</td><td><strong><?=$result['TOTAL_DUE']?></strong></td></tr>
						</table>
					</div></p>
				</td>
			</tr-->
			
			<tr>
				<td colspan='2' ><p>
					<div id="customer_package" class="normalFont">
						<table width='100%'>
							<tr><td colspan='3'><STRONG>PAYMENT DETAILS</STRONG></td></tr>
							<tr>	
								<td width="250" class='orangeText'>PAYMENT_NO</td>
								<td width="250" class='orangeText'>PAYMENT_METHOD</td>
								<td width="250" class='orangeText'>PAYMENT_DATE</td>
								<td width="250" class='orangeText'>INVOICE_REFERNCE</td>
								<td width="250" class='orangeText'>AMOUNT</td>
								<td width="250" class='orangeText'>CURRENCY_IN_WORDS</td>
								<td width="250" class='orangeText'>REGION_ID</td>
								<td width="250" class='orangeText'>SALES_SHOP_ID</td>
								<td width="250" class='orangeText'>AGENT_ID</td>		
							</tr>
							<?	foreach($payments as $payment)
								{ ?>
									<tr>
										<td class="textboxGray"><?=$payment['PAYMENT_NO']?></td>
										<td class="textboxGray"><?=$payment['PAYMENT_METHOD']?></td>
										<td class="textboxGray"><?=$payment['PAYMENT_DATE']?></td>
										<td class="textboxGray"><?=$payment['INVOICE_REFERNCE']?></td>
										<td class="textboxGray"><?=$payment['AMOUNT']?></td>
										<td class="textboxGray"><?=$payment['CURRENCY_IN_WORDS']?></td>
										<td class="textboxGray"><?=$payment['REGION_ID']?></td>
										<td class="textboxGray"><?=$payment['SALES_SHOP_ID']?></td>
										<td class="textboxGray"><?=$payment['AGENT_ID']?></td>
									</tr>	
							<? } ?>
						</table>
					</div></p>
				</td>
			</tr>
			</table >            

			  <!--------------------------------THE END------------------------------------->
		  </td>
      </tr>
    </table>
    <br></td>
  </tr>
  	<tr>
		<td>
		 <table width="1100" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
		  <tr>
			<td align="center" valign="middle" background="<?IMAGES?>/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
		  </tr>
		  </table>
		</td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</body>
</html>	
	
	  
	
