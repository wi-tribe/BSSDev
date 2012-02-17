<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

$formName = $_REQUEST['formName'];
//check permission
//if(!$_POST['permission']=='NWD')
//    {
//        echo "Permission Denied";
//        exit();
//    }

$vouch_obj = new Voucher();

      $channel_type = strtolower($_POST['channel_type']);
      $shop = strtolower($_POST['channel']);
      
     // print_r($_POST);
    if($channel_type!=null and $channel_type!='all')
    {
        $users = $vouch_obj->getUsersByChannelType($_POST['region'],$_POS['channel'],$_POST['channel_type']);
        $_SESSION['reporttype']='receipt';
    }
    else if($shop!=null and $shop!='all')
    {
        $users = $vouch_obj->getUserByShop($_POST['channel']);
	$_SESSION['reporttype']='receipt';
    }
?>
 <select name='user' id='user' class="selectbox" >
                <option value="ALL"> -- ALL -- </option>
                 <? foreach($users as $arr) { ?>
            <option value="<?=$arr['salespersonnel_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> </option>
            <? } ?>
 </select>

