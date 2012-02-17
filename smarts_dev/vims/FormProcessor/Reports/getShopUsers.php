<?php
	session_start("VIMS");
	set_time_limit(200);

	//ob_start();
	include_once("../../vims_config.php");
	$conf=new config();
	include_once(CLASSDIR."/POS.php");

	$ch_obj = new Channel();
	$vouch_obj = new Voucher();
	$user_obj = new User();
	
	//print_r($_POST);
	
	if($_SESSION['reporttype']=='receipt')
	{
	   $users = $vouch_obj->getUserByShop($_POST['channel']);
		//print_r($users);
		//exit(0);
	}
if($_SESSION['reporttype']=='collection')
    {
        $users = $user_obj->getchannelUsers($_POST['channel']);
    }

?>
    <? if($_SESSION['reporttype']=='receipt')
    { ?>
         <select name='user' id='user' class="selectbox" >
                <option value="ALL"> -- ALL -- </option>
                 <? foreach($users as $arr) { ?>
            <option value="<?=$arr['salespersonnel_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> </option>
            <? } ?>
        </select>
    <? } ?>
 <? if($_SESSION['reporttype']=='collection')
    { ?>
         <select name='user' id='user' style="visibility:visible" class="selectbox" >
                <option value=""> -- ALL -- </option>
                 <? foreach($users as $arr) { ?>
            <option value="<?=$arr['user_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> </option>
            <? } ?>
        </select>
    <? } ?>
