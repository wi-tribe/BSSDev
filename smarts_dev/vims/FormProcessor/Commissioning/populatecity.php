<?php
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd'))
{
	echo "Permission denied";
	exit;
}
if($_POST['region_id']==null)
    exit();

$channel_obj = new Channel();
$cities = $channel_obj->getRegionCities($_POST['region_id']);

if($cities==null) {echo "No City Found!";
exit();
}
?>

<select name="city_id" id="city_id" class="selectbox" onchange="javascript:processForm( 'commissionAdd','FormProcessor/Commissioning/populatezone.php','zonediv' );" >
				<option value="">--Select City--</option>
				<?php
                                    foreach($cities as $arr) { ?>
                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>
                                    <? } ?>
</select>
