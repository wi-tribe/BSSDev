<?php
/*************************************************
* @Author: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
* Lists the used vouchers came for replacement
**************************************************/

session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');
include_once(CLASSDIR."/Replacement.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'repoverride'))
{
	echo "Permission denied";
	exit;
}

$rep_obj = new Replacement();
$ch_obj = new Channel();
$user_obj = new User();
$data = $rep_obj->getrepoveridePendings();
if($data!=null){$channel_name = $ch_obj->getChannelName($data[0]['by_channel']);
$user = $user_obj->getUserInfo($data[0]['raised_by']);}

?>

<script>

</script>
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Requested Used Vouchers for Replacement</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
        <td class="textboxBur" align="center" ><strong>Voucher ID</strong></td>
        <td class="textboxBur" align="center"><strong>Voucher Serial</strong></td>
	<td class="textboxBur" align="center"><strong>Ticket Generation Date</strong></td>
	<td class="textboxBur" align="center"><strong>Voucher Denomination</strong></td>
        <td class="textboxBur" align="center"><strong>Raised By</strong></td>
        <td class="textboxBur" align="center"><strong>By Channel</strong></td>


  </tr>
  <?	$index=1;
		foreach($data as $arr)
		{ ?>
          <tr>
            <td><?=$index++?></td>
            <td class="textboxGray"><?=$arr['voucher_id']?></td>
            <td class="textboxGray"><?=$arr['voucher_serial']?></td>
            <td class="textboxGray"><?=$arr['date_added']?></td>
	    <td class="textboxGray"><?=$arr['voucher_denomination']?></td>
	    <td class="textboxGray"><?=$user[0]['username']?></td>
            <td class="textboxGray"><?=$channel_name?></td>
            <td class="textboxGray" align="center">
                
            <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/POS/ticketedVouchers.php','rep_id=<?=$arr['rep_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Details</a></td>
          </tr>
  <? } ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>



