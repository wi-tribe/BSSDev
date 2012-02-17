<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//check permission
//if(!$_POST['permission']=='NWD')
//    {
//        echo "Permission Denied";
//        exit();
//    }

   
if($_POST['channel']==null) {
    print( "No Channel selected!!!");
    exit();
    }
$ch_obj = new Channel();
$user_obj = new User();
if($_SESSION['reporttype']=='receipt')
    {
    $users = $user_obj->getshopSalesid($_POST['channel']);
}
if($_SESSION['reporttype']=='collection')
    {
    $users = $user_obj->getchannelUsers($_POST['channel']);
    }
if($users==null){print("User's not found!"); exit();}

?>
    <? if($_SESSION['reporttype']=='receipt')
    { ?>
    <Label>Users:</Label>
         <select name='user' id='user' style="visibility:visible" class="selectbox" >
                <option value=""> -- Users-- </option>
                 <? foreach($users as $arr) { ?>
            <option value="<?=$arr['salespersonnel_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> </option>
            <? } ?>
        </select>
    <? } ?>
 <? if($_SESSION['reporttype']=='collection')
    { ?>
    <Label>Users:</Label>
         <select name='user' id='user' style="visibility:visible" class="selectbox" >
                <option value=""> -- Users-- </option>
                 <? foreach($users as $arr) { ?>
            <option value="<?=$arr['user_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> </option>
            <? } ?>
        </select>
    <? } ?>
