<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Discount.php");


//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountMain'))
{
	echo "Permission denied";
	exit;
}

$dis_obj = new Discount();
$data = $dis_obj->getallDiscounts();

?>
<table width="100%">
  
       <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd'))
        { ?>
      <tr>
      <td>
       <input class="button" type="button" name ="Add_discount" id="Add_discount" value="Add Discount" onclick="javascript:AjaxPopUpWindow( 'Pages/Discounts/discountAdd.php','user_id=<?=$arr['user_id']?>','top=100, left=100,height=500, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" />
      </td>
      <!--
      <td>
          <input class="button" type="button" name="Add_discount_type" id="Add_discount_type" value="Add Discount Type" onclick="javascript:AjaxPopUpWindow( 'Pages/Discounts/discountTypeAdd.php','user_id=<?=$arr['user_id']?>','top=100, left=100,height=200, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" />
      </td>
      !-->
      </tr>
      <? } ?>
  
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Discount Rules</strong></td>
  </tr>
  <tr>
      <td  class="textboxBur" width="10%">S/N</td>
    <td class="textboxBur" align="center" ><strong>Channel Type</strong></td>
    <td class="textboxBur" align="center" ><strong>Discount Type</strong></td>
    <td class="textboxBur" align="center" ><strong>Discount Name</strong></td>
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
            <td class="textboxGray"><?=$arr['discount_type_name']?>
            <td class="textboxGray"><?=$arr['discount_name']?>
            <td class="textboxGray"><?=$arr['region']?></td>
			<td class="textboxGray"><?=$arr['city']?></td>
                        <td class="textboxGray"><?=$arr['zone']?></td>
			 <td class="textboxGray"><?=($arr['channel'])?> </td>
			 <td class="textboxGray"><?=$arr['value']?> </td>
                         <td class="textboxGray"><?=$arr['start_date']?> </td>
                         <td class="textboxGray"><?=$arr['status']?> </td>
            <? if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd')) { ?>
            <td class="textboxGray" align="center">
            <a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/Discounts/discountAdd.php','dis_id=<?=$arr['dis_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit</a></td>
            <? } ?>
          </tr>
  <? } ?>

	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>