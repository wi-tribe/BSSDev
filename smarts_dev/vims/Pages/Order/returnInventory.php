<?
session_start("VIMS");
set_time_limit(700);


	include_once('../../vims_config.php');
	$order = new Order();

	//check permission
	if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'returninventory'))
	{
		echo "Permission denied";
		exit;
	}

	$voucher=new Voucher();
	if(($vData=$voucher->getVoucherDenominations()))
	{	
		$whData_=true;
	}
	
?>	
	<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td>
              <!-----------------------------THIS PAGE------------------------------------->
               <form name="ReturnInventory" id="ReturnInventory" method="post" action="">
					<table width="70%" border="0" cellspacing="1" cellpadding="0">
						<tr bgcolor="#EFEFEF">
						  <td >&nbsp;</td>
						  <td >Return Date : </td>
						  <td ><?=date('Y-d-m')?></td>
						</tr>
						<tr bgcolor="#EFEFEF" >
						  <td>&nbsp;</td>
						  <td >Returned By</td>
						  <td ><?=$_SESSION['username']?></td>
						</tr>
						<tr bgcolor="#EFEFEF" >
						  <td>&nbsp;</td>
						  <td >Return Reason</td>
						  <td ><textarea name="order_desc" id="order_desc"></textarea></td>
						</tr>
						<tr bgcolor="#EFEFEF">
						  <td >&nbsp;</td>
						  <td >Face Value </TD>
						  <td>
							<select class="selectbox" name="voucher_denomination" id="voucher_denomination" 
									onchange="javascript:processForm( 'ReturnInventory','Pages/Order/ajax_get_vouchers.php','listVouchers' );">
							<option value="">Select Face Value</option>
							<?PHP if(!empty($vData)) { for($i=0; $i< sizeof($vData);$i++) { ?>
							<option value="<?=$vData[$i]['voucher_denomination']?>"><?=$vData[$i]['voucher_denomination']?></option>
							<? } } ?>
						  </select> 
						  </td>
						</tr>
                                                <tr><td align="center" bgcolor="#EFEFEF">&nbsp;</td>
                                                    <td bgcolor="#EFEFEF">Serial Start#: 
                                                    <input class="textboxBur" type="text" name="serial_search_start" id="voucher_serial_search" />
                                                    <td bgcolor="#EFEFEF">Serial End#:
                                                    <input class="textboxBur" type="text" name="serial_search_end" id="voucher_serial_search" />
                                                    <input type="button" name="button" id="button" value="Go" onClick="javascript:processForm( 'ReturnInventory','Pages/Order/ajax_get_vouchers.php','listVouchers' );"/></td>
                                                </tr>
					</table>
					<table width='100%' border="0" cellspacing="1" cellpadding="0">
						<tr>
							<td colspan='2'> 
								<div id='listVouchers'></div>	
							</td>
						</tr>
						<tr>
							<td width='50%' align='right'>&nbsp;</td>
							<td>
								<input	name="FormAction" type='button' class="button" value='Return To Warehouse' 
										onclick="javascript:processForm( 'ReturnInventory','FormProcessor/Order/returnInventory.php','MsgDiv' );">
							</td>
							
						</tr>
					</table>
                </form>
				
               <!--------------------------------THE END------------------------------------->
			</td>
  </tr>
	</table>