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

if($_POST['city_id']==null)
    exit();
    
$channel_obj = new Channel();

$result= $channel_obj->getCityZones($_POST['city_id']);

?>

<select name="zone_id" id="zone_id" class="selectbox" style="visibility: visible" onchange="javascript:processForm( 'discountAdd','FormProcessor/Discounts/populatechannel.php','channeldiv' );" onclick="showhidefield('channel_text','channel_id')" >
				<option value="">--Select Zone--</option>
				<?php
                                    foreach($result as $arr) { ?>
                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>
                                    <? } ?>
                                </select>
