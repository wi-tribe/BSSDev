<?php 
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");

//creating new object
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addvoucher'))
{
	echo "Permission denied";
	exit;
}

?>
	<table align="center" width="90%" border=0>
		<tr valign='top' class='textbox' >
			<td>
              <!-----------------------------THIS PAGE------------------------------------->     
				<form name='VoucherAddRange' id='VoucherAddRange' method='post' action=''>
				  <table width='883' border='0' cellspacing='0' cellpadding='0'>
					<tr>
					  <td>Voucher Denomination</td>
					  <td>
                                          <select name="vouch_denomination" id="vouch_denomination" class="selectbox" >
                                          <option value="">Voucher Denomination</option>
                                          <option value="100">100</option>
                                          <option value="250">250</option>
                                          <option value="500">500</option>
                                          <option value="1000">1000</option>
                                          </select></td>
					</tr>
					<tr>
					  <td>Voucher Serial Number Start</td>
					  <td><input type='text' name='vouch_serial_start' id='vouch_serial_start' /></td>
					  <td>Voucher Serial Number End</td>
					  <td><input type='text' name='vouch_serial_end' id='vouch_serial_end' /></td>
					</tr>
					<tr>
					  <td colspan=4 align='right'><input name="FormAction" type='button' value='Add' onclick="javascript:processForm( 'VoucherAddRange','FormProcessor/Voucher/voucherAddRange.php','MsgDiv' );"></td>
					</tr>
				  </table>
				</form
				
			  ><!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>






