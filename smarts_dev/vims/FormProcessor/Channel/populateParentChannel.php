<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/User.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailerchannel'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$channel=new Channel();
$user = new User();
$channel_details = $channel->getChannelDetails($_SESSION['channel_id']);
$region_id = $_POST['region_id'];
//print_r($channel_details);
 if($region_id==null || $region_id=="")
     {
        ?>
    <select name="parent_channel_id" id="parent_channel_id">
            <option value="">select</option>
             </select> <?
     }
     else
         {
            $results=$channel->getRegionalChannelsName(1,$region_id);
                

            if($results==null)
                {
                ?>
                <select name="parent_channel_id" id="parent_channel_id">
                        <option value="">NONE</option>
                         </select> <?
                }
                else
               {
            ?>
            <select name="parent_channel_id" id="parent_channel_id" >
            <option value="">None</option>
            <?php
            foreach($results as $arr) { ?>
            <option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?> ---- <?=$arr['channel_type_name']?></option>
            <? } }
}
?>
</select>