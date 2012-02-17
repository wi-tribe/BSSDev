<?
session_start("VIMS");
set_time_limit(700);
include_once("../../vims_config.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/Channel.php");

$ch_obj = new Channel();

$data = $ch_obj->getAllChannelTypes();
for($index = 0; $index < sizeof($data); $index++)
   {
       if($data[$index]['channel_type_name']=='Retailer')
       {
           $retailer []= $data[$index];
       }
   }
$user_obj =  new User();
$logged_in_user_info = $user_obj->getUserDetailInfo($_SESSION['user_id']);
$channels = $ch_obj->getRetailersChannel($retailer[0]['channel_type_id'], $logged_in_user_info[0]['region']);
?>
<select name="user_channel_id" id="user_channel_id">
                                <option value="">Select Channel</option>
                                 <?php
                                    foreach($channels as $arr) { ?>
                                    <option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?></option>
                                    <? } ?>
</select>
