<?php
/*************************************************
* @Author: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
* Replacement Status raised to Finance
**************************************************/

session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');
include_once(CLASSDIR."/Replacement.php");

//check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'repoverride'))
//{
//	echo "Permission denied";
//	exit;
//}

$rep_obj = new Replacement();
$ch_obj = new Channel();
$vch_obj = new Voucher();
$user_obj = new User();
$data = $rep_obj->getrepOverrideEntries();
if($data!=null)
    {
    $raised_by = $user_obj->getUserInfo($data[0]['raised_by']);
    $channel_name = $ch_obj->getChannelName($data[0]['by_channel']);
    $repldetails= $rep_obj->getallReplacements();
    if($repldetails!=null && $repldetails[0]['returned_voucher_id']==$data[0]['voucher_id'])
        {
             $issued_voucher = $vch_obj->getVoucherSerial($repldetails[0]['issued_voucher_id']);
             $replaced_by = $user_obj->getUserInfo($repldetails[0]['replaced_by']);
        }

    }

?>

<script>

</script>
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Requested Replacements Status</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
        <td class="textboxBur" align="center" ><strong>Replacement Voucher ID</strong></td>
        <td class="textboxBur" align="center"><strong>Replacement Voucher Serial</strong></td>
	<td class="textboxBur" align="center"><strong>Ticket Generation Date</strong></td>
	<td class="textboxBur" align="center"><strong>Voucher Denomination</strong></td>
        <td class="textboxBur" align="center"><strong>Raised By</strong></td>
        <td class="textboxBur" align="center"><strong>By Channel</strong></td>
        <!--<td class="textboxBur" align="center"><strong>Replacement ID</strong></td>
        <td class="textboxBur" align="center"><strong>Issued Voucher Serial</strong></td>
        <td class="textboxBur" align="center"><strong>Processed By</strong></td>!-->
        <td class="textboxBur" align="center"><strong>Replacement Status</strong></td>
  </tr>
  
  <?	$index=1;
		foreach($data as $arr)
		{
                  if($arr['by_channel']==$_SESSION['channel_id'] && $arr['status']!= 'Processed' )
                   { ?>
          <tr>
            <td><?=$index++?></td>
            <td class="textboxGray"><?=$arr['voucher_id']?></td>
            <td class="textboxGray"><?=$arr['voucher_serial']?></td>
            <td class="textboxGray"><?=$arr['date_added']?></td>
	    <td class="textboxGray"><?=$arr['voucher_denomination']?></td>
	    <td class="textboxGray"><?=$raised_by[0]['username']?></td>
            <td class="textboxGray"><?=$channel_name?></td>
            <!--
            <td class="textboxGray"><?=$repldetails[0]['replacement_id']?></td>
            <td class="textboxGray"><?=$issued_voucher?></td>
            <td class="textboxGray"><?=$replaced_by[0]['username']?></td>
            !-->
            <td class="textboxGray"><?=$arr['status']?></td>
            
          </tr>
          <? } ?>
  <? } ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>



