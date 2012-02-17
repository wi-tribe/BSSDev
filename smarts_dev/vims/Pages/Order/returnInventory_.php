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
	
?>	
	<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
               <form name="ReturnInventory" id="ReturnInventory" method="post" action="">
                  <table width="320" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td class='orangeText' >Return Date : </td>
                      <td ><?=date('Y-d-m')?></td>
                    </tr>
                    <tr>
                      <td class='orangeText' >Returned By</td>
                      <td ><?=$_SESSION['username']?></td>
                    </tr>
                    <tr>
						  <td class='orangeText' >Voucher Serial Number Start</td>
						  <td ><input type='text' name='vouch_serial_start' id='vouch_serial_start' /></td>  
					</tr>
					<tr >
						<td class='orangeText' >Voucher Serial Number End</td>
						<td ><input  type='text' name='vouch_serial_end' id='vouch_serial_end' /></td>
					</tr>
					<!--tr>
                      <td class='orangeText' >total Items</td>
                      <td ><input type="text" name="total_items" id="total_items"></td>
                    </tr-->
                    <tr >
                      <td class='orangeText' >Return Reason</td>
                      <td><textarea name="order_desc" id="order_desc"></textarea></td>
                    </tr>
                    
                  </table>
				  <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
					<tr>
					  <td align='right'><input name="FormAction" type='button' value='Return To Warehouse' onclick="javascript:processForm( 'ReturnInventory','FormProcessor/Order/returnInventory.php','MsgDiv' );"></td>
					</tr>
				</table>
                </form>
				
               <!--------------------------------THE END------------------------------------->
			</td>
  </tr>
	</table>