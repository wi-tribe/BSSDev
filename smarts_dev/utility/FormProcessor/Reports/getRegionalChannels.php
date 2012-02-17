<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
//include_once("../../vims_config.php");
include_once("../../util_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

$ch_obj = new Channel();
	if( $_POST['region']!='ALL' )
	{
		$channels = $ch_obj->getChannelByRegionLive($_POST['region'], $_POST['channel_type']);
	}

?>
<select name='channel' id='channel' class="selectbox" onchange="javascript:processForm( 'collectionReport','FormProcessor/Reports/getChannelUsers.php','userdiv' )">
	<option value="ALL"> -- ALL -- </option>
	<?	foreach($channels as $arr)
	{ ?>
		<option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?> </option>
	<? } ?>
 </select>

