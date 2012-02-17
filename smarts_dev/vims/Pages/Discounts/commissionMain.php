<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Commission.php");

//check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionMain'))
//{
//	echo "Permission denied";
//	exit;
//}

$com_obj = new Commission();
$data = $com_obj->getallCommissions();
//print_r($data);

?>
<table width="100%">
  <tr>
   <?// if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd')) { ?>
  <input  class="button" type="button" name="add_commision_rule" id="add_commission_rule" value="Add Commission" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','user_id=<?=$arr['user_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" />
  <?// } ?>
  </tr>
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Commission Rules</strong></td>
  </tr>
  <tr>
      <td  class="textboxBur" width="5%">S/N</td>
    <td class="textboxBur" align="center" ><strong>Channel Type</strong></td>
    <td class="textboxBur" align="center"><strong>Region</strong></td>
    <td class="textboxBur" align="center"><strong>City</strong></td>
	<td class="textboxBur" align="center"><strong>Zone</strong></td>
	<td class="textboxBur" align="center"><strong>Channel</strong></td>
        <td class="textboxBur" align="center"><strong>Value</strong></td>
        <td class="textboxBur" align="center"><strong>Start Date</strong></td>
	<td class="textboxBur" align="center"><strong>Status</strong></td>


  </tr>
  <?	$index=1;
		foreach($data as $arr)
		{ ?>
          <tr>
              <td><?=$index++?></td>
            <td class="textboxGray"><?=$arr['channel_type_name']?></td>
            <td class="textboxGray"><?=$arr['region']?>
            <td class="textboxGray"><?=$arr['city']?></td>
			<td class="textboxGray"><?=$arr['zone']?></td>
                        <td class="textboxGray"><?=$arr['channel']?></td>
			 <td class="textboxGray"><?=($arr['value'])."%"?> </td>
                         <td class="textboxGray"><?=($arr['start_date'])?> </td>
			 <td class="textboxGray"><?=$arr['status']?> </td>
            <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd')) { ?>
            <td class="textboxGray" align="center">
            <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','commission_id=<?=$arr['commission_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit</a></td>
            <? } ?>
          </tr>
  <? } ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>