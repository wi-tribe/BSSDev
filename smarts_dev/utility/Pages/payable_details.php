<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/style.css" rel="stylesheet" type="text/css"></link>
<style type="text/css">
<!--
body {
	background-color:#CCCCCC ;/*#5F5D60*/
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script src="../js/jquery/jquery.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.core.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.datepicker.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/jquery.validate.js" type="text/javascript" language="javascript"></script>
<script language="javascript" type="text/javascript" src="../js/validations.js"></script>
<script type="text/javascript" src="../js/thickbox.js"></script>
 
<link href="../styles/style.css" rel="stylesheet" type="text/css"></link>
<link href="../js/jquery/ui.datepicker.css" rel="stylesheet" type="text/css"></link>
<!--link rel="stylesheet" href="css/thickbox.css" type="text/css" media="screen" /-->
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
</head>
<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
	session_start();
	ob_start();

        include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	$sales_obj = new SalesPersonnel();
        
	$account['rec_id']= $_REQUEST['rec_id'];
	$result = $sales_obj->getAccount($account);
	$sales_rep = $sales_obj->getCurrentCSR();
	$calls = $sales_obj->getCalls($account);
?>
<script>
	function call_status_update()
	{
                document.getElementById('updateCall').disabled=true;
		var rec_id_value = document.getElementById('rec_id').value;
		var cust_id_value = document.getElementById('customer_id').value;
		var user_id_value = document.getElementById('sales_personnel_id').value;

		var rc_obj = document.getElementById('call_reason_code');
		var reason_code_value = rc_obj.options[rc_obj.selectedIndex].value;
                var remarks = document.getElementById('remarks').value;
                var region = document.getElementById('region').value;
                var shop_id = document.getElementById('shop_id').value;

		var post_func = function()
					{
						var effect = function(data){ $("#call_result").html(data).show("normal");
                                                        document.getElementById('updateCall').disabled=false;
							window.opener.document.getElementById('cust_payables').onclick();
                                                        //window.close();
						};
						$.post("update_call_record.php",{customer_id: cust_id_value, salespersonnel_id: user_id_value, reason_code: reason_code_value, rec_id: rec_id_value,remarks:remarks,region:region,shop_id:shop_id},effect);
					}
                $("#call_result").html("<img src='../../images/loading.gif' /> Loading...");
		$("#call_result").fadeOut("slow",post_func);
	}

function MailtoCustomer()
{
    var template = document.getElementById('Emailtemplate').value;
    var first_name = document.getElementById('FirstName').value;
    var last_name = document.getElementById('LastName').value;
    var due_amount = document.getElementById('due_amount').value;
     var customer_id = document.getElementById('customer_id').value;
    var customer_email = document.getElementById('customer_email').value;
    var package_info = document.getElementById('package_info').value;
     var post_func = function()
				{
					var effect = function(data){ $("#result").html(data).show("normal"); };
					$.post("../FormProcessor/ajax_email.php",{Emailtemplate:template,first_name:first_name,last_name:last_name,due_amount:due_amount,customer_email:customer_email,package_info:package_info,customer_id:customer_id} ,effect);
				}
	$("#result").html("<img src='../../images/loading.gif' /> Loading...");
	$("#result").show("normal",post_func);
}

function textCounter(field, countfield, maxlimit) {
if (field.value.length > maxlimit) // if too long...trim it!
field.value = field.value.substring(0, maxlimit);
// otherwise, update 'characters left' counter
else 
countfield.value = maxlimit - field.value.length;
}
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1100" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">
		<table width="100%" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="../../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;"></td>
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
			<tr><td>
				<form name="call_info" target="_blank" method="post" action="dashboard/test.php">
					<input name ="customer_id" id ="customer_id" type="hidden" value="<?=$result['CUSTOMER_ID']?>" />
					<input name ="rec_id" id ="rec_id" type="hidden" value="<?=$result['REC_ID']?>" />
					<input name ="sales_personnel_id" id ="sales_personnel_id" type="hidden" value="<?=$sales_rep['SALESPERSONNEL_ID']?>" />
                                        <input name="region" id="region" type="hidden" value="<?=$sales_rep['ADDR_CITY']?>" />
                                        <input name="shop_id" id="shop_id" type="hidden" value="<?=$sales_rep['SHOP_ID']?>" />
					<table >
						<tr>
                                                    <td class='orangeText'>Call Status &nbsp;
								<select name="call_reason_code" id="call_reason_code" class="textbox">
									<option value="Call not contacted/busy/switched off">Call not contacted/busy/switched off</option>
									<option value="Customer not available">Customer not available</option>
									<option value="Device already returned">Device already returned</option>
									<option value="Did not use - Waiver Request">Did not use - Waiver Request</option>
									<option value="Fake Sales">Fake Sales</option>
									<option value="Incorrect customer details">Incorrect customer details</option>
									<option value="Out of Station">Out of Station</option>
									<option value="Safe Custody / Freeze Account">Safe Custody / Freeze Account</option>
									<option value="Troubleshooting request">Troubleshooting request</option>
									<option value="Troubleshooting request - Waiver">Troubleshooting request - Waiver</option>
									<option value="Will not pay - Sales Return Request">Will not pay - Sales Return Request</option>
									<option value="Will Pay - Home Collection ">Will Pay - Home Collection </option>
									<option value="Will Pay - Themselves">Will Pay - Themselves</option>
								</select>
							</td>
                                                        <td class='orangeText'>Remarks</td>
                                                         <td><textarea name="remarks" id="remarks" onKeyDown="textCounter(this.form.remarks,this.form.remLen,200);" onKeyUp="textCounter(this.form.remarks,this.form.remLen,200);"></textarea>
                                                                <input readonly type=text name=remLen size=3 maxlength=3 value="200" /> characters left
                                                            </td>
							<td><input type="button" id="updateCall" name="updateCall" onclick="javascript:call_status_update();" value="Save & Close" /></td>
						</tr>
						<tr>
							<td class="orangeText" colspan="4"><strong>Call History</strong></td>
						</tr>
						<tr>
							<td class="textboxBur">Call Status</td>
							<td class="textboxBur">Called At</td>
							<td class="textboxBur">Called By</td>
                                                        <td class="textboxBur">Remarks</td>
						</tr>
							<?	foreach($calls as $sale)
								{ ?>
								  <tr>
									<td class="textboxGray"><?=$sale['REASON_CODE']?> </td>
									<td class="textboxGray"><?=$sale['DATETIME']?></td>
									<td class="textboxGray"><?=$sale['SALESPERSONNEL_ID']?></td>
                                                                        <td class="textboxGray"><?=$sale['REMARKS']?></td>
								  </tr>
						  <? } ?>
						<tr>
							 <td width="77%"> 
								<div  id ="call_result" ></div>
							</td> 
						</tr>
					</table>
				</form>
				</td>
			</tr>
			<tr>
				<td ><p>
					<div id="customer_detail" class="normalFont">
						<!--////////////////////////////////////////////////////////-->
						<table width='100%'>
							<tr><td colspan='3'><img src='../../images/orderform/customer_details.gif' /></td></tr>
							<tr><td width="250" class='orangeText'>Customer ID</td>			<td>&nbsp;</td><td><strong><?=$result['CUSTOMER_ID']?></strong></td></tr>
							<tr><td width="250" class='orangeText'>Name</td>				<td>&nbsp;</td><td><?=$result['FIRST_NAME']." ".$result['LAST_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Customer Address</td>	<td>&nbsp;</td><td><?=$result['ADDRESS']?></td></tr>
							<tr><td width="250" class='orangeText'>City</td>				<td>&nbsp;</td><td><?=$result['REGION_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Telephone</td>			<td>&nbsp;</td><td><?=$result['TELEPHONE_NUMBER']?></td></tr>
							<tr><td width="250" class='orangeText'>Mobile</td>				<td>&nbsp;</td><td><?=$result['MOBILE_NUMBER']?></td></tr>
							<tr><td width="250" class='orangeText'>Email Address</td>		<td>&nbsp;</td><td><?=$result['EMAIL_ADDRESS']?></td></tr>
                                                        <? if($result['METERING_PROMO']!=NULL) {?>
                                                        <tr><td width="250" class='orangeText'>Promo 1</td>		<td>&nbsp;</td><td><?=$result['PROMO1']?></td></tr>
                                                        <tr><td width="250" class='orangeText'>Promo 2 </td>		<td>&nbsp;</td><td><?=$result['PROMO2']?></td></tr>
                                                        <? } ?>
						</table>
				<!--////////////////////////////////////-->
					</div></p>
                                  <table>
                                        <tr >
                                        <input name ="FirstName" id ="FirstName" type="hidden" value="<?=$result['FIRST_NAME']?>" />
                                        <input name ="LastName" id ="LastName" type="hidden" value="<?=$result['LAST_NAME']?>" />
                                        <input name ="customer_email" id ="customer_email" type="hidden" value="<?=$result['EMAIL_ADDRESS']?>" />
                                        <input name ="due_amount" id ="due_amount" type="hidden" value="<?=$result['TOTAL_DUE']?>" />
                                        <input name ="package_info" id ="package_info" type="hidden" value="<?=$result['PACKAGE_NAME']?>" />
                                            <td class="orangeText"><strong>Mail Subject: </strong></td>
                                            <td>
                                                <select class="selectbox" name="Emailtemplate" id="Emailtemplate" >
                                                    <option value="">--- Mail Subjects ---</option>
                                                    <option value="billingupdate/index">Billing Update</option>
                                                </select>
                                            </td>
                                            <td>
                                                <input type="button" class="button" name="EmailCustomer" id="EmailCustomer" value="Send Mail to Customer" onclick="MailtoCustomer();"/>
                                            </td>
                                            <td>
                                                <div id="result"></div>
                                            </td>
                                        </tr>

                                    </table>
				</td>
				<td><p>
					<div id="customer_account" class="normalFont">
						<table width='100%' cellpadding='0' cellspacing='0'>
							<tr><td colspan='6'><img src='../../images/orderform/account_details.png' /></td></tr> <BR>
							<tr><td width="250" class='orangeText'>Status</td>						<td>&nbsp;</td><td><strong><?=$result['CUSTOMER_STATUS']?></strong></td></tr>
							<tr><td width="250" class='orangeText'>Creation Date</td>				<td>&nbsp;</td><td><?=$result['DATE_CREATED']?></td></tr>
							<tr><td width="250" class='orangeText'>Last Status Change Date</td>		<td>&nbsp;</td><td><?=$result['LAST_STATUS_DATE']?></td></tr>
							<tr><td width="250" class='orangeText'>Last Status Change Reason</td>	<td>&nbsp;</td><td><?=$result['LAST_COMMENT']?></td></tr>
							<tr><td width="250" class='orangeText'>Account Type</td>				<td>&nbsp;</td><td><?=$result['BUSINESS_TYPE']?></td></tr>
							<tr><td width="250" class='orangeText'>MAC Address</td>					<td>&nbsp;</td><td><?=$result['CPE_MAC_ADDRESS']?></td></tr>
							<tr><td width="250" class='orangeText'>Usage Month 1</td>				<td>&nbsp;</td><td><?=$result['USAGEMONTH1']?></td></tr>
							<tr><td width="250" class='orangeText'>Usage Month 2</td>					<td>&nbsp;</td><td><?=$result['USAGEMONTH2']?></td></tr>
							<tr><td width="250" class='orangeText'>Profile ID as per package</td>	<td>&nbsp;</td><td><?=$result['PROFILE_ID']?></td></tr>
						</table>
					</div></p>
                                   
				</td>
			</tr>
			<tr>
				<td><p>
					<div id="customer_package" class="normalFont">
						<table width='100%'>
                                                    <tr><td colspan='3'><img src='../../images/orderform/package_selection.png' /></td></tr>
							<tr><td width="250" class='orangeText'>Package</td>			<td>&nbsp;</td><td><?=$result['PACKAGE_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Pay Type</td>		<td>&nbsp;</td><td><?=$result['PAY_TYPE']?></td></tr>
							<tr><td width="250" class='orangeText'>Parent Account</td>	<td>&nbsp;</td><td><?=$result['PARENT_ACCT']?></td></tr>
							<tr><td width="250" class='orangeText'>Shop ID</td>			<td>&nbsp;</td><td><?=$result['SHOP_ID']?></td></tr>
							<tr><td width="250" class='orangeText'>Sales Rep</td>		<td>&nbsp;</td><td><?=$result['SALES_REP_NAME']?></td></tr>
							<tr><td width="250" class='orangeText'>Sales Rep ID</td>	<td>&nbsp;</td><td><?=$result['SALES_REP_ID']?></td></tr>
							<tr><td width="250" class='orangeText'>Total Due Now</td>	<td>&nbsp;</td><td><strong><?=$result['TOTAL_DUE']?></strong></td></tr>
						</table>
					</div>
                                        </p>
                                    
				</td>
			</tr>
                            <tr>
                                <td>
                                    <table width='100%'>
                                        <tr><td colspan='3'><strong><font color="#C12267" size="2">BILL PAYMENT</font></strong></td></tr>
							<tr><td width="250" class='orangeText'>Nearest Retail Shop1 Address:</td>			<td><strong><?=$result['NEARESTRETAILSHOP1']?></strong></td></tr>
							<tr><td width="250" class='orangeText'>Nearest Retail Shop2 Address:</td>				<td><?=$result['NEARESTRETAILSHOP2']?></td></tr>
							<tr><td width="250" class='orangeText'>Nearest Retail Shop3 Address:</td>	<td><?=$result['NEARESTRETAILSHOP3']?></td></tr>
					
						</table>
                                </td>
                            </tr>

			</table >            

			  <!--------------------------------THE END------------------------------------->
		  </td>
      </tr>
    </table>
    </br></td>
  </tr>
  	<tr>
		<td>
		 <table width="1100" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
		  <tr>
			<td align="center" valign="middle" background="../../images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
		  </tr>
		  </table>
		</td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</body>
</html>	
	
	
	
