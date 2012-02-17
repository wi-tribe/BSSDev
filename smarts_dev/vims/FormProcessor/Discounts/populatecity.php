<?php
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd'))
{
	echo "Permission denied";
	exit;
}

if($_POST['region_id']==null)
    exit();

$channel_obj = new Channel();
$cities = $channel_obj->getRegionCities($_POST['region_id']);        

if($cities==null)
    {  echo "No City Found!";
        exit();
    }
?>

<select name="city_id" id="city_id" class="selectbox" style="visibility: visible" onchange="javascript:processForm( 'discountAdd','FormProcessor/Discounts/populatezone.php','zonediv' );" onclick="showhidefield('zone_text','zone_id')" >
				<option value="">--Select City--</option>
				<?php
                                    foreach($cities as $arr) { ?>
                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>
                                    <? } ?>
</select>
