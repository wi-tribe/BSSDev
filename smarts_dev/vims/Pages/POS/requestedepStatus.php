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
$data = $rep_obj->getrepOverrideEntries();

?>

<script>

</script>
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Requested Replacements Status</strong></td>
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
	    <td class="textboxGray"><?=$arr['raised_by']?></td>
            <td class="textboxGray"><?=$arr['by_channel']?></td>
            <td class="textboxGray" align="center">

            <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/POS/ticketedVouchers.php','rep_id=<?=$arr['rep_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Details</a></td>
          </tr>
  <? } ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>



