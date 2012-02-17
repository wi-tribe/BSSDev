<?


session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Channel.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailerchannel'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$channel=new Channel();


$region_id = $_POST['region_id'];

 if($region_id==null || $region_id=="")
     {
        ?>
    <select name="city_id" id="city_id">
            <option value="">Select a City</option>
             </select> <?
     }
     else
         {

            $cities_names= $channel->getRegionCities($region_id);
        //    $data = $ROLE->getHeads($region_id);

            if($cities_names==null)
                {
                ?>
                <select name="city_id" id="city_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateZone.php','zone_Div' );">
                        <option value="">NONE</option>
                         </select> <?
                }
                else
               {
            ?>
            <select name="city_id" id="city_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateZone.php','zone_Div' );">
            <option value="">Select a City</option>
            <?php
            foreach($cities_names as $arr) { ?>
            <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>
            <? } }
}

?>
</select>






