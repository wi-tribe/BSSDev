<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/Logging.php");


//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'cce_track_voucher'))
{
	echo "Permission denied";
	exit;
}

$vouch_obj = new Voucher();
$log_obj = new Logging();
$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying to get voucher status, Serial:".$_POST['voucher_serial']);
$data = $vouch_obj->getVouchStatus($_POST['voucher_serial']);
if(!$data)
    {
        echo $vouch_obj->LastMsg;
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." Unable to get voucher status, Serial:".$_POST['voucher_serial']."Error :".$vouch_obj->LastMsg);
    }
    else
    {
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly got voucher status, Serial:".$_POST['voucher_serial']);
    }
?>

<table width="30%">
    <tr>
     <td class="textboxBur" align="center" ><strong>Voucher Serial:</strong></td>
     <td class="textboxGray"><?=$data[0]['voucher_serial']?></td>
     <TD><PRE> </PRE></TD>
     <td class="textboxBur" align="center" ><strong>Face Value:</strong></td>
     <td class="textboxGray"><?=$data[0]['voucher_denomination']?></td>
      <TD><PRE> </PRE></TD>
    </tr>
    <tr>
     <td class="textboxBur" align="center" ><strong>Voucher Expiration:</strong></td>
     <td class="textboxGray"><?=$data[0]['expiration']?></td>
     <TD><PRE> </PRE></TD>
     <td class="textboxBur" align="center" ><strong>Voucher Status:</strong></td>
     <td class="textboxGray"><?=$data[0]['vouhcer_status']?></td>
    </tr>
    <? if($data[0]['vouhcer_status']=='Used')
    { ?>
     <tr>
        <td class="textboxBur" align="center"><strong>Customer ID</strong></td>
        <td class="textboxGray"><?=$data[0]['customer_id']?></td>
        <TD><PRE> </PRE></TD>
        <td class="textboxBur" align="center"><strong>Customer Name</strong></td>
        <td class="textboxGray"><?=$data[0]['customername']?></td>
     </tr>
     <tr>
        <td class="textboxBur" align="center"><strong>Usage Date</strong></td>
        <td class="textboxGray"><?=$data[0]['usage_date']?></td>
     </tr>
    <? } ?>
</table>

<table width="100%">
  <tr>
    <td colspan="7" align="right" style="font-size:16px"><strong>Voucher Details</strong></td>
    </tr>
  <tr>
        <td class="textboxBur" align="center"><strong>Movement Date</strong></td>
	<td class="textboxBur" align="center"><strong>Movement Time</strong></td>
	<td class="textboxBur" align="center"><strong>From City</strong></td>
        <td class="textboxBur" align="center"><strong>From Channel</strong></td>
	<td class="textboxBur" align="center"><strong>From Channel Type</strong></td>
        <td class="textboxBur" align="center"><strong>Action By</strong></td>
        <td class="textboxBur" align="center"><strong>To City</strong></td>
        <td class="textboxBur" align="center"><strong>To Channel</strong></td>
        <td class="textboxBur" align="center"><strong>To Channel Type</strong></td>
        <td class="textboxBur" align="center"><strong>Assigned To</strong></td>
        <td class="textboxBur" align="center"><strong>Shelf Life in Days</strong></td>       
  </tr>
    <?	
		foreach($data as $arr)
		{ ?>
          <tr>
            <td class="textboxGray"><?=$arr['action_date']?></td>
	    <td class="textboxGray"><?=$arr['action_time']?></td>
            <td class="textboxGray"><?=$arr['from_location']?></td>
	    <td class="textboxGray"><?=$arr['from_channel']?></td>
            <td class="textboxGray"><?=$arr['from_channel_type']?></td>
            <td class="textboxGray"><?=$arr['by_user']?></td>
            <td class="textboxGray"><?=$arr['to_location']?></td>
            <td class="textboxGray"><?=$arr['to_channel']?></td>
            <td class="textboxGray"><?=$arr['to_channel_type']?></td>
            <td class="textboxGray"><?=$arr['assigned_to']?></td>
            <td class="textboxGray"><?=$arr['shelf_life']?></td>
  <? } ?>
            
            
</table>
               