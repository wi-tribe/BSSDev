<?php
session_start("VIMS");
set_time_limit(200);


//ob_start();
//include_once("../../vims_config.php");
include_once("../../util_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");
$ch_obj = new Channel();

	if($_POST['channel']!='ALL' )
	{
		$users = $ch_obj->getUserByChannel($_POST['channel']);
	}
?>
<select name='user' id='user' class="selectbox" >
	<option value="ALL"> -- ALL -- </option>
	<?	foreach($users as $arr) 
		{ ?>
			 <option value="<?=$arr['user_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> 
	<?	} ?>
</select>

