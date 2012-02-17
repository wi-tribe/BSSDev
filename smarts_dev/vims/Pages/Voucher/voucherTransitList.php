<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

session_start("VIMS");
set_time_limit(700);


include_once('../../vims_config.php');
//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'transitvoucherlist'))
{
	echo "Permission denied";
	exit;
}

include_once(CLASSDIR."/Order.php");
$vouch_obj = new Voucher();
$data = $vouch_obj->getTransVouchers($_SESSION['channel_id']);
?>

<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>List of Vouchers in Transit State</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
    <td class="textboxBur" align="center" ><strong>Voucher Serial</strong></td>
    <td class="textboxBur" align="center"><strong>To Channel</strong></td>
	<td class="textboxBur" align="center"><strong>Action By</strong></td>
	<td class="textboxBur" align="center"><strong>Action Date</strong></td>
        <td class="textboxBur" align="center"><strong>Assigned To</strong></td>
	<td class="textboxBur" align="center"><strong>Transfer Type</strong></td>
  </tr>

    <?	$index=1;
		foreach($data as $arr)
		{ ?>
          <tr>
		  <td><?=$index++?></td>
                        <td class="textboxGray"><?=$arr['voucher_serial']?></td>
                        <td class="textboxGray"><?=$arr['to_channel_id']?></td>
			<td class="textboxGray"><?=$arr['action_by']?></td>
			 <td class="textboxGray"><?=$arr['action_date']?> </td>
			 <td class="textboxGray"><?=$arr['assigned_to']?> </td>
                         <td class="textboxGray"><?=$arr['transfer_type_name']?></td>
          </tr>
 
  <?} ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>


