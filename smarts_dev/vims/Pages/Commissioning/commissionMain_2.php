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

?>
<table width="100%">
  <tr>  
  <a href="#" type="button" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','user_id=<?=$arr['user_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Add Commission</a> 
  </tr>
  
</table>
<table width="30%">
    <TD><PRE></PRE></TD>
  <tr>
<td class="textboxBur" align="center" width="40%" ><strong>Nation Wide Commission</strong></td>
          <? foreach($data as $arr)
		{ ?>
          <? if($arr['nwd']==1){ ?>
      <td class="textboxGray" align="center" width="20%"><?=($arr['value']*100)."%"?></td>
      <td class="textboxGray" align="center" width="30%">
                <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','comm_id=<?=$arr['comm_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit</a></td>
            <? } }?>
  </tr>
</table>

<table width="100%">
    <TD><PRE></PRE></TD>
   <tr>    
    <td colspan="7" align="center" style="font-size:16px"><strong>Region Wide Commission Rules</strong></td>
  </tr>
</table>
<table width="50%">

         <tr>
          <?  $region=1;
          ?>
                <td  class="textboxBur">S/N</td>
                <td class="textboxBur" align="center"><strong> Region</strong></td>
                 <td class="textboxBur" align="center"><strong>Value</strong></td>
          </tr>
          <? foreach($data as $arr)
		{ if($arr['nwd']==0) {?>
          <tr>
                <td><?=$region++?></td>
                <td class="textboxGray"><?=$arr['region_name']?></td>
                <td class="textboxGray"><?=($arr['value']*100)."%"?></td>
                 <td class="textboxGray" align="center">
                <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','comm_id=<?=$arr['comm_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit</a></td>
            <? } }?>
  </tr>
</table>

<table width="100%">
    <TD><PRE></PRE></TD>
   <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>City Wise Commission Rules</strong></td>
  </tr>
</table>
<table width="50%">
         <tr>
          <?  $city=1;
          ?>
                <td  class="textboxBur">S/N</td>
                <td class="textboxBur" align="center"><strong> City</strong></td>
                 <td class="textboxBur" align="center"><strong>Value</strong></td>
          </tr>
          <? foreach($data as $arr)
		{ if($arr['nwd']==0 && $arr['city_name']!=null) {?>
          <tr>
                <td><?=$city++?></td>
                <td class="textboxGray"><?=$arr['city_name']?></td>
                <td class="textboxGray"><?=($arr['value']*100)."%"?></td>
                 <td class="textboxGray" align="center">
                <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','comm_id=<?=$arr['comm_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit</a></td>
            <? } }?>
  </tr>
</table>

<table width="100%">
    <TD><PRE></PRE></TD>
   <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>City Wise Commission Rules</strong></td>
  </tr>
</table>
<table width="50%">
    <TD><PRE></PRE></TD>
         <tr>
          <?  $city=1;
          ?>
                <td  class="textboxBur">S/N</td>
                <td class="textboxBur" align="center"><strong> Channel</strong></td>
                <td class="textboxBur" align="center"><strong> City</strong></td>
                <td class="textboxBur" align="center"><strong> Region</strong></td>
                 <td class="textboxBur" align="center"><strong>Value</strong></td>
          </tr>
          <? foreach($data as $arr)
		{ if($arr['nwd']==0 && $arr['channel_name']!=null ) {?>
          <tr>
                <td><?=$city++?></td>
                <td class="textboxGray"><?=$arr['channel_name']?></td>
                <td class="textboxGray"><?=$arr['city_name']?></td>
                <td class="textboxGray"><?=$arr['region_name']?></td>
                <td class="textboxGray"><?=($arr['value']*100)."%"?></td>
                 <td class="textboxGray" align="center">
                <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Commissioning/commissionAdd.php','comm_id=<?=$arr['comm_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit</a></td>
            <? } }?>
  </tr>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>