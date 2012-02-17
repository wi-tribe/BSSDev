<?php
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Channel.php");

////check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rentaladd'))
//{
//	echo "Permission denied";
//	exit;
//}
if($_POST==null || $_POST['zone_id']==null || $_POST['channel_type_id']==null )
    exit();

$channel_obj = new Channel();
$channels = $channel_obj->getZoneChannels($_POST['zone_id'], $_POST['channel_type_id']);

if($channels==null) {echo "No Channel Found!";
    exit();}

?>

<select name="channel_id" id="channel_id" class="selectbox" >
				<option value="">--Select Channel--</option>
                                <?php
                                    foreach($channels as $arr) { ?>
                                    <option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?></option>
                                    <? } ?>
                                </select>
